package nephilim.junit.ch06._fundamental;

import java.io.File;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.jetty.servlet.Context;

public class JettySample {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);
		
		System.out.println("current root path = " + (new File(".")).getAbsolutePath());
		
		Context root = new Context( server, "/");
		root.setResourceBase(".");
		root.setHandler(new ResourceHandler());
		
		server.start();
	}
}
