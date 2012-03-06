/**
 * 
 */
package edu.upenn.cis.cis555;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

/**
 * @author cis555
 *
 */
public class XPathServlet extends HttpServlet{

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out=response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>");
		out.println("XPathServlet");
		out.println("</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out.println("<form action=\"http://localhost:1234/demo\" method=\"POST\" >");
		out.println("<table>" +
				     "<tr><td>");
		out.println("<b>Enter XPath</b></td>");
		out.println("<td><input type=\"text\" name=\"xpath\" size=\"30\"></td>");
		out.println("<td><input type=\"text\" name=\"xpath\" size=\"30\"></td>");
		out.println("<td><input type=\"text\" name=\"xpath\" size=\"30\"></td>");
		out.println("</tr><tr>");
		out.println("<td><b>Enter URL for HTML/XML</b></td>");
		out.println("<td><input type=\"text\" name=\"url\" size=\"30\"></td>");
		out.println("</tr><tr>");
		out.println("<td><input type=\"Submit\" name=\"Submit\" ></td>");
		out.println("</td>");
		
		out.println("The URL can be File System Address or any Remote Address; All Remote Addresses are supposed to start with http://");
		
		out.println("</form>");
		out.println("</BODY>");
		out.println("</HTML>");
}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("XPATH is:   "+request.getParameterValues("xpath"));
		XPathEngine engine=new XPathEngine(request.getParameterValues("xpath"));
		
		
		
		System.out.println("URL:  "+request.getParameterValues("url"));
		HttpClient client=new HttpClient(request.getParameterValues("url")[0]);
		
		ByteArrayOutputStream outStream=client.fetchData();
		if(outStream==null)
		{
			//TODO: Problem with URL Specified..
			PrintWriter out=response.getWriter();
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>");
			out.println("URL Problem");
			out.println("</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY>");
			out.println("There has been an error assocaited while parsing the URL.</br> Program Terminated");
			out.println("</BODY>");
			out.println("</HTML>");
			
		}
		else
		{
		System.out.println("LENGTH INPUT: "+outStream.size());
		
		
		Document root=engine.createDOM(outStream);
	
	
		
		if(root!=null)
		{
			for(int i=1;i<=request.getParameterValues("xpath").length;i++)
			{
			if(!engine.xpathIsCorrect[i-1])
			{
				//TODO XPath not correct
			}
			else
			{
				//TODO CALL EVALAUTE FN
				boolean[] status=engine.evaluate(root);
			}
			}
			//TODO PRINTING DATA TO USER
			PrintWriter out=response.getWriter();
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>");
			out.println("XPathEngine Results");
			out.println("</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY>");
			
			out.println("Supplied URL is: "+request.getParameterValues("url")[0]+"</br>");
			out.println("Status..</br></br>");
			
			out.println("<table>");
			
			for(int i=0;i<request.getParameterValues("xpath").length;i++)
			{
				out.println("<tr>");
				out.println("<td><b>"+engine.xpaths[i]+"</b></td>");
				out.println("<td></td><td></td>");
				out.println("<td><b>"+String.valueOf(engine.statuses[i]).toUpperCase()+"</b></td>");
				out.println("</tr>");
			}
			
			out.println("</table>");
				
			
			out.println("</BODY>");
			out.println("</HTML>");
			
		}
		else
		{
		//SOMETHING WRONG WITH SUPPLIED URL
		//TODO
			PrintWriter out=response.getWriter();
			out.println("<HTML>");
			out.println("<HEAD>");
			out.println("<TITLE>");
			out.println("URL LINK DATA Problem");
			out.println("</TITLE>");
			out.println("</HEAD>");
			out.println("<BODY>");
			out.println("There has been an error assocaited while parsing the data of the URL.</br> Program Terminated");
			out.println("</BODY>");
			out.println("</HTML>");
		}
		
		
	}
	}
		
}
	
