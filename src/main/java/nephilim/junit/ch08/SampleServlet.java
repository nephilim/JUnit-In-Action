package nephilim.junit.ch08;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SampleServlet extends HttpServlet {
	public boolean isAuthenticated(HttpServletRequest request) {
		boolean result = false;
		HttpSession session = request.getSession(false);
		if (session != null ) {
			String authAttribute = (String)session.getAttribute("authenticated");
			result = Boolean.valueOf(authAttribute);
		}
		return result;
	}
}
