/**
 * 
 */
package edu.upenn.cis.cis555;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Node;




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
		xpathExp=new xpath[xpaths.length];
		xpathIsCorrect=new boolean[xpaths.length];
		
		for(int i=0;i<s.length;i++)
		{
			xpathIsCorrect[i]=isValid(i+1);
			if(xpathIsCorrect[i])
			{
				xpaths[i]=removeWhiteSpaces(xpaths[i]);
			}
		}
		
	}
	
	public String removeWhiteSpaces(String xpath)
	{
		System.out.println("BEFORE XPATH:  "+xpath);
		String temp="";
		
		for(int i=0;i<xpath.length();i++)
		{
			int counter=0;
			if(xpath.charAt(i)==' ')
			{
				
				for(int j=0;j<xpath.substring(0,i).length();j++)
				{
				if(xpath.substring(0,i).charAt(j)=='"')
				{
					counter++;
				}
				}
				if(counter%2!=0)
				{
					temp=temp.concat(" ");
				}
			}
			else
			{
				temp=temp.concat(String.valueOf(xpath.charAt(i)));
			}
		}
			
		System.out.println("AFTER XPATH:   "+temp);
		return temp;
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
		
		for(int i=0;i<xpathIsCorrect.length;i++)
		{
					
		if(xpathIsCorrect[i])
		{
				
				
		}
			}
		
		
		for(int i=0;i<xpathIsCorrect.length;i++)
		{
			if(!xpathIsCorrect[i])
			{
				statuses[i]=false;
				continue;
			}
			else
			{
				/*
				 * Code to traverse the XPath and create necessary obj for parsing
				 */
				
				xpathExp[i]=visualizeXPath(xpaths[i]);
				
				xpath links=xpathExp[i];
				
				/*
				 * Printig data that has been parsed with xPath
				 */
				while(links.link!=null)
				{
				System.out.print(links.nodeName+":::");
				System.out.println(links.attributes);
					links=links.link;
				}
			    System.out.print(links.nodeName+":::");
				System.out.println(links.attributes);
				
				/*
				 * TODO Start writign the evauation fn
				 */
				statuses[i]=isMatch(root.cloneNode(true),xpathExp[i]);
				System.out.println("STATUS:  "+statuses[i]);
			}
		}
		
		return statuses;
	}
	
	
	public boolean isMatch(Node root, xpath node)
	{
		System.out.println("ROOT:  "+root.getNodeName());
		System.out.println("NODE:  "+node.nodeName);
		System.out.println("LENGTH:  "+root.getChildNodes().getLength());
		int i=0;
		for(i=0;i<root.getChildNodes().getLength();i++)
		{
			System.out.println("ELEMENT:  "+root.getChildNodes().item(i).getNodeName()+"  at: "+i);
			if(root.getChildNodes().item(i).getNodeName().equalsIgnoreCase(node.nodeName))
			{
				if(node.attributes!=null)
				{
				if(!checkForAttributes(root.getChildNodes().item(i).cloneNode(true),node))
				{
					continue;
				}
				}
				
				//TODO PUT ATTRIBUTE CHECKING CODE
				if(node.link!=null)
				{
			     boolean status=isMatch(root.getChildNodes().item(i).cloneNode(true), node.link);
			     if(status)
			     {
			    	 return true;
			     }
				}
				else
					return true;
			}
		}
		if(i==root.getChildNodes().getLength())
		{
		return false;
		}
		else
		{
			return false;
		}
	}	
	
	
	public boolean checkForAttributes(Node root, xpath node)
	{
		String attribute=node.attributes;
		ArrayList<String> attributes=new ArrayList<String>();
		
		int position=0;
		int cursorPos=0;
	    int match=attribute.length()-1;
	    boolean firstOrNot=true;
		while((match=attribute.indexOf("]",cursorPos))!=-1)
		{
			System.out.println("ATTRIBUTE SPLIT:  "+attribute.substring(position,match+1));
			int counter=0;
			for(int i=0;i<attribute.substring(position,match+1).length();i++)
			{
				if(attribute.substring(position,match+1).charAt(i)=='[')
				{
					counter++;
				}
				else if(attribute.substring(position,match+1).charAt(i)==']')
				{
					counter--;
				}
			}
			if(counter==0)
			{
				attributes.add(attribute.substring(position,match+1));
				position=match+1;
				cursorPos=match+1;
			}
			else
			{
				cursorPos=match+1;
			}
		}
		
		boolean result=false;
		int beg=0;
		for(int i=0;i<attributes.size();i++)
		{
			System.out.println("ARRAYLIST ELEM:  "+attributes.get(i));
			String tempAtt=attributes.get(i);
			tempAtt=tempAtt.substring(1);
			tempAtt=tempAtt.substring(0,tempAtt.length()-1);
			
			if(tempAtt.length()>6 && tempAtt.substring(0,6).equalsIgnoreCase("text()"))
			{
				boolean internal=false;
				System.out.println("ENTERED text()");
				//TODO ; For text() data
				String []pairs=tempAtt.split("=");
				pairs[0]=pairs[0].trim();
				pairs[1]=pairs[1].trim();
				pairs[1]=pairs[1].substring(1);
				pairs[1]=pairs[1].substring(0,pairs[1].length()-1);
				
				for(int z=0;z<root.getChildNodes().getLength();z++)
				{
					System.out.println("TEXT() ELEMS:  "+root.getChildNodes().item(z).getNodeName());
					if(root.getChildNodes().item(z).getNodeName().equalsIgnoreCase("#text"))
					{
						System.out.println("TEXT() VALUE:  "+root.getChildNodes().item(z).getNodeValue().trim());
						if(root.getChildNodes().item(z).getNodeValue().trim().equalsIgnoreCase(pairs[1]))
						{
						internal=true;
						break;
						}
					}
				}
				if(!result && beg==0)
				{
					result=internal;
					beg++;
				}
				else
				{
				result=internal && result;
				beg++;
				}
				System.out.println("RESULT:  "+internal);
				continue;
			}
			else if(tempAtt.length()>1 && tempAtt.substring(0,1).equalsIgnoreCase("@"))
			{
				/*
				 * Attribute Handling
				 */
				boolean internal=false;
				System.out.println("ENTERED @ATT()");
				//TODO ; For text() data
				String []pairs=tempAtt.split("=");
				pairs[0]=pairs[0].trim();
				pairs[0]=pairs[0].substring(1);
				pairs[1]=pairs[1].trim();
				pairs[1]=pairs[1].substring(1);
				pairs[1]=pairs[1].substring(0,pairs[1].length()-1);
				
				for(int z=0;z<root.getAttributes().getLength();z++)
				{
					System.out.println("@ATT() ELEMS:  "+root.getAttributes().item(z).getNodeName());
					if(root.getAttributes().item(z).getNodeName().equalsIgnoreCase(pairs[0]))
					{
						System.out.println("@ATT() VALUE:  "+root.getAttributes().item(z).getNodeValue().trim());
						if(root.getAttributes().item(z).getNodeValue().trim().equalsIgnoreCase(pairs[1]))
						{
						internal=true;
						break;
						}
					}
				}
				if(!result && beg==0)
				{
					result=internal;
					beg++;
				}
				else
				{
				result=internal && result;
				beg++;
				}
				System.out.println("RESULT:  "+internal);
				continue;
				
			}
			
			else if(tempAtt.length()>8 && tempAtt.substring(0,8).equalsIgnoreCase("contains"))
			{
				//TODO ; For contains
				/*
				 * Contains Handling
				 */
				boolean internal=false;
				System.out.println("ENTERED CONTAINS()");
				//TODO ; For text() data
				String[] temp=tempAtt.split("contains");
				temp[1]=temp[1].trim();
				temp[1]=temp[1].substring(1);
				temp[1]=temp[1].substring(0,temp[1].length()-1);
				
				String []pairs=temp[1].split(",");
				pairs[0]=pairs[0].trim();
				pairs[1]=pairs[1].trim();
				pairs[1]=pairs[1].substring(1);
				pairs[1]=pairs[1].substring(0,pairs[1].length()-1);
				
				for(int z=0;z<root.getChildNodes().getLength();z++)
				{
					System.out.println("CONTAINS() ELEMS:  "+root.getChildNodes().item(z).getNodeName());
					if(root.getChildNodes().item(z).getNodeName().equalsIgnoreCase("#text"))
					{
						System.out.println("CONTAINS() VALUE:  "+root.getChildNodes().item(z).getNodeValue().trim());
						if(root.getChildNodes().item(z).getNodeValue().trim().equalsIgnoreCase(pairs[1]))
						{
						internal=true;
						break;
						}
					}
				}
				if(!result && beg==0)
				{
					result=internal;
					beg++;
				}
				else
				{
				result=internal && result;
				beg++;
				}
				System.out.println("RESULT:  "+internal);
				continue;
				
			}
			else
			{
				System.out.println("ENTERED ELEMENT PARSING!");
				boolean internal=false;
			xpath temp=visualizeXPath(" "+tempAtt);
			internal= isMatch(root, temp);
			
			if(!result && beg==0)
			{
				result=internal;
				beg++;
			}
			else
			{
			result=internal && result;
			beg++;
			}
			System.out.println("RESULT:  "+internal);
			continue;
			
			
			}
			
		}
		
		System.out.println("ULTI RESULT:"+result);
		return result;
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
		start.attributes=null;
		start.link=null;
		start.nodeName=null;
		
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
			temp.attributes=null;
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
		temp.attributes=null;
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
		
		//FINISHED PARSING
		
		
		
		
		/*
		 * SEPRATE NODENAME AND ATTRIBUTES FOR FURTHER REFERENCE
		 */
		
		xpath links=start;
		
		while(links.link!=null)
		{
			if(links.nodeName.indexOf("[")!=-1)
			{
				links.attributes=links.nodeName.substring(links.nodeName.indexOf("["),links.nodeName.length());
				links.nodeName=links.nodeName.substring(0,links.nodeName.indexOf("["));
			}
			links=links.link;
		}
		if(links.nodeName.indexOf("[")!=-1)
		{
		links.attributes=links.nodeName.substring(links.nodeName.indexOf("["),links.nodeName.length());
		links.nodeName=links.nodeName.substring(0,links.nodeName.indexOf("["));
		}
		
				
		return start;
	}
}
