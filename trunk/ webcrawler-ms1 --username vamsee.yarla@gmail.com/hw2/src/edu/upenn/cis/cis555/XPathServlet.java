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
		out.println("</tr><tr>");
		out.println("<td><b>Enter URL for HTML/XML</b></td>");
		out.println("<td><input type=\"text\" name=\"url\" size=\"30\"></td>");
		out.println("</tr><tr>");
		out.println("<td><input type=\"Submit\" name=\"Submit\" ></td>");
		out.println("</td></form>");
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
		System.out.println("LENGTH INPUT: "+outStream.size());
		
		/*
		Document root=engine.createDOM(request.getParameterValues("url"));
	
	
		
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
			//TODO
		}
		else
		{
		//SOMETHING WRONG WITH SUPPLIED URL
		//TODO
		}
		*/
		
	}
		
}
	
