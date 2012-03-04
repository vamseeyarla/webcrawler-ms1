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
	
	public XPathEngine()
	{
		xpaths=null;
		statuses=null;
	}
	
	public XPathEngine(String [] s)
	{
		xpaths=s;
		statuses=new boolean[xpaths.length];
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
	
	
	public boolean[] evaluate(Document d)
	{
		
		
		return statuses;
	}
	
	
	
	public boolean isValid (int i)
	{
		if(statuses==null || statuses.length>=i)
		{
			return statuses[i-1];
		}
		else
		{
			return false;
		}
	}
}
