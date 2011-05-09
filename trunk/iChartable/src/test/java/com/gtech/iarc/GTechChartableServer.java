package com.gtech.iarc;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.jetty.webapp.WebAppContext;

public class GTechChartableServer {
	private static final int PORT = 1686;
	private static final int MAX_IDLE_TIME = 1000 * 60 * 60;
	private static final int SO_LINGER_TIME = -1;
	private static final String WAR_PATH = "src/main/webapp";
	private static final String CONTEXT_PATH = "/gtech";

	public static void main(String[] args) throws Exception {
		Server server = new Server();
		SocketConnector connector = new SocketConnector();

		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(MAX_IDLE_TIME);
		connector.setSoLingerTime(SO_LINGER_TIME);
		connector.setPort(PORT);
		server.setConnectors(new Connector[] { connector });

		WebAppContext ctx = new WebAppContext();
		ctx.setServer(server);
		ctx.setContextPath(CONTEXT_PATH);
		ctx.setWar(WAR_PATH);
		ctx.addServlet(new ServletHolder(new org.apache.jasper.servlet.JspServlet()), "*.jsp");

		boolean enableReload = true;
		server.addHandler(ctx);

		try {
			System.out.println(">>> STARTING EMBEDDED JETTY SERVER, PRESS ANY KEY TO STOP");
			server.start();
			System.in.read();
			System.out.println(">>> STOPPING EMBEDDED JETTY SERVER");
			if (enableReload) {
				System.out.println("reloading ..");
				while (System.in.available() == 0) {
					Thread.sleep(2000);
				}
			}
			server.stop();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(100);
		}
	}
}