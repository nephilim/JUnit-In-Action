package nephilim.junit.ch06;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.net.URL;

import nephilim.junit.ch06.handler.ContentNotFoundHandler;
import nephilim.junit.ch06.handler.StaticMessageReturnHandler;
import nephilim.junit.ch6.WebClient;

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
		
		// 정상 반환 테스트용 context 설정 
		Context okContext = new Context(server, "/test-okay");
		StaticMessageReturnHandler msgReturnHandler = new StaticMessageReturnHandler();
		msgReturnHandler.setMessage(MESSAGE);
		okContext.setHandler(msgReturnHandler);
		
		// 비정상 반환 테스트용 context 설정 
		Context errorContext = new Context(server, "/test-error");
		errorContext.setHandler(new ContentNotFoundHandler());
		
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
				"ok url의 응답 내용은 지정된 메세지와 같아야 한다",
				result, is(equalTo(MESSAGE)));
	}

	@Test
	public void testGetContent_Error() throws Exception{
		WebClient client = new WebClient();
		URL errorUrl = new URL("http://localhost:8080/test-error");
		String result = client.getContent(errorUrl);
		
		assertThat(
				"error url의 응답 내용은 null이어야 한다",
				result, is(nullValue()));
	}
}
