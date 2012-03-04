/**
 * 
 */
package edu.upenn.cis.cis555.webserver;

import java.io.File;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;

import org.xml.sax.helpers.DefaultHandler;


/**
 * @author VamseeKYarlagadda
 *
 */
public class ServletsInit {
	 private static Logger logger = Logger.getLogger(ServletsInit.class);
	public static HashMap<String,HttpServlet> servlets; 
	public static HashMap<String,String> mappings=new HashMap<String,String>();
	public static  String webxml=null;
	public static String sessionTimeOut="-1";
	//HANDLER TO PARSE XML USING FNITE STATE MODEL AND SLPIT ELEMNTS
	static class Handler extends DefaultHandler {
		public void startElement(String uri, String localName, String qName, Attributes attributes) {
			if (qName.compareTo("servlet-name") == 0) {
				m_state = 1;
			} else if (qName.compareTo("servlet-class") == 0) {
				m_state = 2;
			} else if (qName.compareTo("context-param") == 0) {
				m_state = 3;
			} else if (qName.compareTo("init-param") == 0) {
				m_state = 4;
			} else if (qName.compareTo("param-name") == 0) {
				m_state = (m_state == 3) ? 10 : 20;
			} else if (qName.compareTo("param-value") == 0) {
				m_state = (m_state == 10) ? 11 : 21;
			} 
					
			else if(qName.compareTo("url-pattern")==0 )
			{
				m_state=50;
			}
			
			else if (qName.compareTo("session-timeout") == 0) {
				m_state = 45;
			}
		}
		public void characters(char[] ch, int start, int length) {
			String value = new String(ch, start, length);
			if (m_state == 1) {
				m_servletName = value;
				sname=value;
				m_state = 0;
			} else if (m_state == 2) {
				m_servlets.put(m_servletName, value);
				m_state = 0;
			} else if (m_state == 10 || m_state == 20) {
				m_paramName = value;
			} else if (m_state == 11) {
				if (m_paramName == null) {
					System.err.println("Context parameter value '" + value + "' without name");
					System.exit(-1);
				}
				m_contextParams.put(m_paramName, value);
				m_paramName = null;
				m_state = 0;
			} else if (m_state == 21) {
				if (m_paramName == null) {
					System.err.println("Servlet parameter value '" + value + "' without name");
					System.exit(-1);
				}
				HashMap<String,String> p = m_servletParams.get(m_servletName);
				if (p == null) {
					p = new HashMap<String,String>();
					m_servletParams.put(m_servletName, p);
				}
				p.put(m_paramName, value);
				m_paramName = null;
				m_state = 0;
			}
			
			else if(m_state == 50)
			{
				
				urlMappings.put(sname, value);
			}
			
			else if(m_state == 45)
			{
				
				sessionTimeOut=value;
			}
		}
		private int m_state = 0;
		private String m_servletName;
		private String sname;
		private String m_paramName;
		HashMap<String,String> m_servlets = new HashMap<String,String>();
		HashMap<String,String> urlMappings = new HashMap<String,String>();
		HashMap<String,String> m_contextParams = new HashMap<String,String>();
		HashMap<String,HashMap<String,String>> m_servletParams = new HashMap<String,HashMap<String,String>>();
	}
		
	//FUCTION TO HANDLE web.xml FILES BY PARSING THEM
	private static Handler parseWebdotxml(String webdotxml) throws Exception {
		Handler h = new Handler();
		File file = new File(webdotxml);
		if (file.exists() == false) {
			System.err.println("error: cannot find " + file.getPath());
			System.exit(-1);
		}
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		parser.parse(file, h);
		
		return h;
	}
	
	//FUNCTION TO CREATE CONTEXT FOR THIS WEB APP
	private static ServletsContext createContext(Handler h) {
		ServletsContext fc = new ServletsContext();
		for (String param : h.m_contextParams.keySet()) {
			fc.setInitParam(param, h.m_contextParams.get(param));
		}
		return fc;
	}
	
	//FUNCTION TO STORE ALL THE SERVLETS IN HASH MAP BASED ON THEIR NAME
	private static HashMap<String,HttpServlet> createServlets(Handler h, ServletsContext fc) throws Exception {
		HashMap<String,HttpServlet> servlets = new HashMap<String,HttpServlet>();
		for (String servletName : h.m_servlets.keySet()) {
			ServletsConfig config = new ServletsConfig(servletName, fc);
			String className = h.m_servlets.get(servletName);
			Class servletClass = Class.forName(className);
			HttpServlet servlet = (HttpServlet) servletClass.newInstance();
			HashMap<String,String> servletParams = h.m_servletParams.get(servletName);
			if (servletParams != null) {
				for (String param : servletParams.keySet()) {
					config.setInitParam(param, servletParams.get(param));
				}
			}
			servlet.init(config);
			servlets.put(servletName, servlet);
		}
		
		mappings=h.urlMappings;
			
		fc.StoreServlets(servlets);
		return servlets;
	}
	
	
	//FUNCTION TO ACTIVATE SERVLETS
	public boolean startServelts(String args)
	{
	//	System.out.println("DONE AOfghhDING SERVLETS");
		boolean status=false;
try{
//	System.out.println("TRACK0");
		Handler h = parseWebdotxml(args);
	//	System.out.println("TRACK1");
		webxml=args;
		ServletsContext context = createContext(h);
		
	//	System.out.println("TRACK2");
		
		
		servlets = createServlets(h, context);
	//	System.out.println("TRACK3");
		status=true;
	//	System.out.println("DONE LAODING SERVLETS");
	}
	
	catch(Exception e)
	{
		e.printStackTrace();
		logger.error(e.toString());
		status=false;
	//	System.out.println(e.toString()+" ERROR IN SERVLET INIT");
	}
	
		return status;
	
}
}
