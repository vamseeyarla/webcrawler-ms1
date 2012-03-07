/**
 * 
 */
package edu.upenn.cis.cis555;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
	String ConType=null;
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
					outBytes=new ByteArrayOutputStream();
					outBytes.write("404".getBytes());
					return outBytes;
				}
				else
				{
					//String contentLength=null;
					String contentType=null;
					
					while((contentType=br.readLine()).indexOf("Content-Type")==-1);
					String type=contentType.substring(contentType.indexOf(":")+1,contentType.length()).trim();
					if(type.indexOf("xml")!=-1 || type.indexOf("XML")!=-1)
					{
						ConType="XML";
					}
					else if(type.indexOf("html")!=-1 || type.indexOf("HTML")!=-1)
					{
						ConType="HTML";
					}
					/*
					while((contentLength=br.readLine()).indexOf("Content-Length")==-1);
				
					Length=Integer.parseInt((contentLength.substring(contentLength.indexOf(":")+1,contentLength.length()).trim()));
						*/
					
					/*
					 char[] tempc=new char[Length];
					 br.read(tempc, 0, Length);
					 StringBuilder sb=new StringBuilder();
       			  	 sb.append(tempc);
					  outBytes.write(sb.toString().getBytes());
					 */
					int x;
					while(!((x=br.read())==13 && (x=br.read())==10 && (x=br.read())==13 && (x=br.read())==10));
			
					outBytes=new ByteArrayOutputStream();
					while((x=br.read())!=-1)
					{
						outBytes.write(x);
					}
					 br.close();
					 out.close();
       			  	 socket.close();
       			  	
       			  	
       			  	 
       		      	
					
					
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
			outBytes=new ByteArrayOutputStream();
			try {
				outBytes.write("404".getBytes());
			} catch (IOException e1) {
				//  Auto-generated catch block
				e1.printStackTrace();
			}
			return outBytes;
			//return null;
		}
	}
	
	
	
	return outBytes;
}
}
