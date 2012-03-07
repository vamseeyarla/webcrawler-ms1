/**
 * 
 */
package test.edu.upenn.cis.cis555.hw2;





import java.util.ArrayList;



import edu.upenn.cis.cis555.webserver.HandleReq;
import edu.upenn.cis.cis555.webserver.ServletsInit;
import junit.framework.TestCase;

/**
 * @author VamseeKYarlagadda
 *
 */
public class ResponseTest extends TestCase {

	
	ArrayList<String> headers=new ArrayList<String>();
	String met;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}


	/*
	 * Test to check Response object proper execution; Content-Type
	 */
	
	public void testIsResponseHandled1()
	{
		
		headers.clear();
		met="GET";
		headers.add("GET /init/vamsee?userid=vamsee HTTP/1.1");
		headers.add("Host: localhost:1234");
		headers.add("User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");
		headers.add("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		headers.add("Accept-Language: en-us,en;q=0.5");
		headers.add("Accept-Encoding: gzip, deflate");
		headers.add("DNT: 1");
		headers.add("Connection: keep-alive");
		headers.add("Referer: http://localhost:1234/man.html");
		headers.add("Cookie: SessionID=05fb884d-2ef3-4fb7-997b-9e7ade5e0c62");
			    
		new ServletsInit().startServelts("/home/cis555/hw1/src/edu/upenn/cis/cis555/Sample/Servlets/web/WEB-INF/web.xml");
		HandleReq obj=new HandleReq();
		obj.isNotTest=false;
		obj.headers=headers;
		obj.met=met;
		obj.isServlet();
		assertEquals("text/html",obj.response.getContentType());
		
	}
	
	/*
	 * Test to check Response object proper execution; IsCommitted Fn
	 */
	
	public void testIsResponseHandled2()
	{
		
		headers.clear();
		met="GET";
		headers.add("GET /init/vamsee?userid=vamsee HTTP/1.1");
		headers.add("Host: localhost:1234");
		headers.add("User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");
		headers.add("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		headers.add("Accept-Language: en-us,en;q=0.5");
		headers.add("Accept-Encoding: gzip, deflate");
		headers.add("DNT: 1");
		headers.add("Connection: keep-alive");
		headers.add("Referer: http://localhost:1234/man.html");
		headers.add("Cookie: SessionID=05fb884d-2ef3-4fb7-997b-9e7ade5e0c62");
			    
		new ServletsInit().startServelts("/home/cis555/hw1/src/edu/upenn/cis/cis555/Sample/Servlets/web/WEB-INF/web.xml");
		HandleReq obj=new HandleReq();
		obj.isNotTest=false;
		obj.headers=headers;
		obj.met=met;
		obj.isServlet();
		assertEquals(false,obj.response.isCommitted());
		
	}
	
	
}
