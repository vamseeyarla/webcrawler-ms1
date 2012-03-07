/**
 * 
 */
package test.edu.upenn.cis.cis555.hw1;



import java.util.ArrayList;



import edu.upenn.cis.cis555.webserver.HandleReq;
import edu.upenn.cis.cis555.webserver.ServletsInit;
import junit.framework.TestCase;

/**
 * @author VamseeKYarlagadda
 *
 */
public class ServletTest extends TestCase {

	
	ArrayList<String> headers=new ArrayList<String>();
	String met;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}


	/*
	 * Test to check if the program is Servlet or not;: Passed something which is not Servlet
	 */
	
	public void testIsNotServlet()
	{
		headers.clear();
		met="POST";
		headers.add("POST /demox HTTP/1.1");
		headers.add("Host: localhost:1234");
		headers.add("User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");
		headers.add("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		headers.add("Accept-Language: en-us,en;q=0.5");
		headers.add("Accept-Encoding: gzip, deflate");
		headers.add("DNT: 1");
		headers.add("Connection: keep-alive");
		headers.add("Referer: http://localhost:1234/man.html");
		headers.add("Cookie: SessionID=05fb884d-2ef3-4fb7-997b-9e7ade5e0c62");
		headers.add(" ");
	    headers.add("textline=dsf&textlin=dsf");
	    
	    new ServletsInit().startServelts("/home/cis555/hw1/src/edu/upenn/cis/cis555/Sample/Servlets/web/WEB-INF/web.xml");
		HandleReq obj=new HandleReq();
		obj.isNotTest=false;
		obj.headers=headers;
		obj.met=met;
		assertEquals(true, obj.isServlet());
	}
	
	/*
	 * Test to check if the program is Servlet or not;: Passed something which is a Servlet
	 */
	
	public void testIsServlet()
	{
		headers.clear();
		met="POST";
		headers.add("POST /demo HTTP/1.1");
		headers.add("Host: localhost:1234");
		headers.add("User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:10.0.2) Gecko/20100101 Firefox/10.0.2");
		headers.add("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		headers.add("Accept-Language: en-us,en;q=0.5");
		headers.add("Accept-Encoding: gzip, deflate");
		headers.add("DNT: 1");
		headers.add("Connection: keep-alive");
		headers.add("Referer: http://localhost:1234/man.html");
		headers.add("Cookie: SessionID=05fb884d-2ef3-4fb7-997b-9e7ade5e0c62");
		headers.add(" ");
	    headers.add("textline=dsf&textlin=dsf");
	    
	    new ServletsInit().startServelts("/home/cis555/hw1/src/edu/upenn/cis/cis555/Sample/Servlets/web/WEB-INF/web.xml");
		HandleReq obj=new HandleReq();
		obj.isNotTest=false;
		obj.headers=headers;
		obj.met=met;
		assertEquals(false, obj.isServlet());
	}
	
	
}
