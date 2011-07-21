package nephilim.junit.ch06.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.handler.AbstractHandler;

public class ContentNotFoundHandler extends AbstractHandler {
	public void handle(String target, HttpServletRequest request,
			HttpServletResponse response, int dispatch) throws IOException,
			ServletException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}

}
