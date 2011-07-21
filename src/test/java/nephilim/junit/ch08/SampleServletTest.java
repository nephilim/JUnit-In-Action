package nephilim.junit.ch08;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

public class SampleServletTest {
	
	HttpServletRequest mockedRequest;
	HttpSession mockedSession;
	
	@Before
	public void prepareMock() {
		mockedRequest = mock(HttpServletRequest.class);
		mockedSession = mock(HttpSession.class);
		
		
		// request에서 getSession을 하면 무조건 mockedSession 반환됨
		when(mockedRequest.getSession(anyBoolean())).thenReturn(mockedSession);
	}
	
	@Test
	public void testIsAuthenticated_세션정보가true일경우() {
		when(mockedSession.getAttribute("authenticated")).thenReturn("true");
		
		SampleServlet sampleServlet = new SampleServlet();
		assertThat(
				"session에 authenticated이 true일경우 인증됐다고 반환해야함",
				sampleServlet.isAuthenticated(mockedRequest),
				is(true));
	}

	@Test
	public void testIsAuthenticated_세션이null일경우() {
		when(mockedRequest.getSession(anyBoolean())).thenReturn(null);
				
		SampleServlet sampleServlet = new SampleServlet();
		assertThat(
				"session이 null일 경우 인증되지 않았다고 반환해야함",
				sampleServlet.isAuthenticated(mockedRequest),
				is(false));
	}
	
	@Test
	public void testIsAuthenticated_세션정보가false일() {
		when(mockedSession.getAttribute("authenticated")).thenReturn("false");
		
		SampleServlet sampleServlet = new SampleServlet();
		assertThat(
				"session에 authenticated이 false일경우 인증되지 않았다고 반환해야함",
				sampleServlet.isAuthenticated(mockedRequest),
				is(false));
	}
}
