/**
 * 
 */
package edu.upenn.cis.cis555;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;



/**
 * @author VamseeKYarlagadda
 *
 */
public class XPathEngine {
	public String [] xpaths;
	boolean[] statuses;
	public boolean[] xpathIsCorrect;
	xpath[] xpathExp;
	
	public XPathEngine()
	{
		xpaths=null;
		statuses=null;
	}
	
	public XPathEngine(String [] s)
	{
		xpaths=s;
		statuses=new boolean[xpaths.length];
		xpath[] xpathExp=new xpath[xpaths.length];
		xpathIsCorrect=new boolean[xpaths.length];
		
		for(int i=0;i<s.length;i++)
		{
			xpathIsCorrect[i]=isValid(i+1);
			
			
			if(xpathIsCorrect[i])
			{
					xpathExp[i]=visualizeXPath(s[i]);
					
					xpath links=xpathExp[i];
					
					while(links.link!=null)
					{
						System.out.println(links.nodeName);
						links=links.link;
					}
				//	links.link=temp;
					System.out.println(links.nodeName);
					
			}	
			
			
		}
		
	}
	
	
	public Document createDOM(String [] s)
	{
		String url;
		DocumentBuilderFactory doc=null;
		DocumentBuilder docBuilder=null;
		Document docHead=null;
		
		if(s.length==0)
		{
			return null;
		}
		else
		{
			try
			{
			url=s[0];
					url="http://localhost:1234/web.xml";         
			 doc=DocumentBuilderFactory.newInstance();
	         docBuilder = doc.newDocumentBuilder();
       /*
           BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            doc=DocumentBuilderFactory.newInstance();
            docBuilder = doc.newDocumentBuilder();
            
             ByteArrayOutputStream out=new ByteArrayOutputStream();		           
		                byte data[] = new byte[1024];
		                int count;
		                while ((count = in.read(data, 0, 1024)) != -1)
		                {
                                    out.write(data, 0, count);
		                }
		                  if (in != null)
		                        in.close();
		*/           
	
        docHead = docBuilder.parse(new URL(url).openStream());
	
			
			
			
			//Code to initialize XML/HTML as DOM
			}
			catch(Exception e)
			{
				e.printStackTrace();
				docHead=null;
			}
			
			
			return docHead;
			
			
		}
	}
	
	
	public boolean[] evaluate(Document root)
	{
		
		
		return statuses;
	}
	
	
	
	public boolean isValid (int i)
	{
		if(statuses==null || statuses.length>=i)
		{
			if(xpaths[i-1].charAt(0)!='/' || xpaths[i-1].indexOf("//")!=-1 || xpaths[i-1].indexOf("[[")!=-1 || xpaths[i-1].indexOf("::")!=-1|| xpaths[i-1].indexOf("==")!=-1 || xpaths[i-1].indexOf("@@")!=-1 || xpaths[i-1].indexOf("((")!=-1 || xpaths[i-1].indexOf("/[")!=-1)
			{
				System.out.println("XPath Verfication Failed!");
				xpathIsCorrect[i-1]=false;
				return false;
			}
	
			else
			{
				xpathIsCorrect[i-1]=true;
				return true;
			}
			
			
		}
		else
		{
			return false;
		}
	}
	
	
	class xpath
	{
		String nodeName;
		String attributes;
		xpath link;
	}
	
	public xpath visualizeXPath(String path) 
	{
		
		int pos=1;
		int actualPointer=1;
		xpath start=new xpath();
		int match;
		int objStatus=0;
		
		while((match=path.indexOf("/",pos))!=-1)
		{
			
			int bracketLStatus=0,bracketRStatus=0;
			String temps=path.substring(actualPointer, match);	
			for(int z=0;z<temps.length();z++)
			{
				if(temps.charAt(z)=='[')
				{
					bracketLStatus++;
				}
				else if(temps.charAt(z)==']')
				{
					bracketRStatus++;
				}
			}
			
			if(bracketLStatus!=bracketRStatus)
			{
				pos=match+1;
				continue;
			}
				
			xpath temp=new xpath();
			temp.nodeName=path.substring(actualPointer,match).trim();
			temp.link=null;
			pos=match+1;
			actualPointer=match+1;
			if(objStatus==0)
			{
				start=temp;
				start.link=null;
				objStatus++;
			}
			else
			{
				xpath links=start;
				while(links.link!=null)
				{
					links=links.link;
				}
				links.link=temp;
				objStatus++;
			}
			
			
		}
		
		
		/*LAST '/' that doesn't execute in while loop
		 * 
		 */
		
		xpath temp=new xpath();
		temp.nodeName=path.substring(actualPointer,path.length()).trim();
		temp.link=null;
		pos=match+1;
		actualPointer=match+1;
		if(objStatus==0)
		{
			start=temp;
			start.link=null;
			objStatus++;
		}
		else
		{
			xpath links=start;
			while(links.link!=null)
			{
				links=links.link;
			}
			links.link=temp;
			objStatus++;
		}
		
		
				
		return start;
	}
}
