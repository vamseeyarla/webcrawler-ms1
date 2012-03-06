/**
 * 
 */
package edu.upenn.cis.cis555;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author VamseeKYarlagadda
 *
 */
public class HttpClient {
	String URL;
	ByteArrayOutputStream outBytes;
public HttpClient(String url)
{
	URL=url;
}

public ByteArrayOutputStream fetchData()
{
	if(URL=="" || URL==null)
	{
		outBytes=null;
	}
	else if(URL.indexOf("http://")==0 || URL.indexOf("HTTP://")==0)
	{
		String address;
		String request;
		String port="80";
		
			//Considering it as Local Server File
			//COsidering it as Global file
		
			if(URL.indexOf("/",7)==-1)
			{
				return null;
			}
			else
			{
				address=URL.substring(0,URL.indexOf("/",7));
				if(address.indexOf(":",7)!=-1)
				{
					port=address.substring(address.indexOf(":",7)+1,address.length());
					address=address.substring(0,address.indexOf(":",7));
				}
				request=URL.substring(URL.indexOf("/",7),URL.length());
			}
			address=address.trim();
			request=request.trim();
			if(address =="" || request=="")
			{
				return null;
			}
			else
			{
				//TIME TO REQUEST THE SERVER FOR DATA
				//
				// Create a connection to the server socket on the server application
				//
				try{
					address=address.substring(7,address.length());
				InetAddress host = InetAddress.getByName(address);
				Socket socket = new Socket (host.getHostAddress(), Integer.parseInt(port));
				
				//
				// Send a message to the client application
				//
				OutputStream out=(socket.getOutputStream());
				out.write(("GET "+request+" HTTP/1.0\n").getBytes());
				out.write(("Host: "+address+"\n").getBytes());
				out.write(("Accept: text/html\n").getBytes());
				out.write(("Accept-Language: en-us\n\n").getBytes());
				
				
				
				
				
				int Length=0;
				BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				String firstHead=br.readLine();
				if(firstHead.indexOf("404")!=-1 || firstHead.indexOf("500")!=-1)
				{
					System.out.println("SERVER ERROR AT REMOTE LOCATION");
					return null;
				}
				else
				{
					String contentLength=null;
					while((contentLength=br.readLine()).indexOf("Content-Length")==-1);
				
					Length=Integer.parseInt((contentLength.substring(contentLength.indexOf(":")+1,contentLength.length()).trim()));
					int x;
					while(!((x=br.read())==13 && (x=br.read())==10 && (x=br.read())==13 && (x=br.read())==10));
				
					 char[] tempc=new char[Length];
					 br.read(tempc, 0, Length);
					 br.close();
					 out.close();
       			  	 socket.close();
       			  	 StringBuilder sb=new StringBuilder();
       			  	 sb.append(tempc);
       			  	 outBytes=new ByteArrayOutputStream();
       			  	 
       		      	 outBytes.write(sb.toString().getBytes());
					
					
				}
				
				
				
				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					return null;		
				}
				
			}
		
	}
	else
	{
		//Considering it as Local FileSystem File
		try{
			FileReader fr=new FileReader(URL);
			outBytes=new ByteArrayOutputStream();
			char []buf=new char[2048];
			int ret;
			while((ret=fr.read(buf, 0, 2048))!=-1)
			{
				outBytes.write(String.valueOf(buf).getBytes(), 0, ret);
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in reading file");
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	return outBytes;
}
}
