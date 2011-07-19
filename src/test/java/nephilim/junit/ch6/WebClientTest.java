package nephilim.junit.ch6;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.net.URL;

import nephilim.junit.ch6.handler.StaticMessageReturnHandler;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;

public class WebClientTest {

	static Server server;
	public static final String MESSAGE = "it works"; 
	
	@BeforeClass
	public static void startUpServer() throws Exception {
		server = new Server(8080);
		Context context = new Context(server, "/test-okay");
		StaticMessageReturnHandler handler = new StaticMessageReturnHandler();
		handler.setMessage(MESSAGE);
		context.setHandler(handler);
		server.start();
	}
	
	@AfterClass
	public static void shutdownServer() throws Exception {
		if ( server == null ) return;
		server.stop();
	}
	
	@Test
	public void testGetContent_Ok() throws Exception{
		WebClient client = new WebClient();
		URL okUrl = new URL("http://localhost:8080/test-okay");
		String result = client.getContent(okUrl);
		
		assertThat(
				"okUrl의 응답 내용은 지정된 메세지와 같아야 한다",
				result, is(equalTo(MESSAGE)));
	}

}
