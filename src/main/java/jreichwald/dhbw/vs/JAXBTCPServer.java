package jreichwald.dhbw.vs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Implementation of a simple TCP server receiving a request and sending a
 * response back to the client. The server is single-threaded for demonstration
 * purposes.
 * 
 * @author Prof. Dr. Julian Reichwald <julian.reichwald@dhbw-mannheim.de>
 *
 */
public class JAXBTCPServer {

	/**
	 * Log instance
	 */
	private static Logger _log = LogManager.getLogger(JAXBTCPServer.class);

	/**
	 * The server port
	 */
	private static final int _serverPort = 11111;

	/**
	 * Time (in milliseconds) the server spends for processing a message
	 * (simulated processing time). 0 = no processing time needed.
	 */
	private static final long _processingDuration = 2000;

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		_log.debug("Server starting.");
		ServerSocket server = null;

		Socket clientConnectionSocket = null;

		int connectionCount = 0 ; 
		
		try {
			server = new ServerSocket(_serverPort);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//loop endlessly 
		while (true) {

			_log.debug("Accepting connections.");

			// accept() is a blocking call!
			try {
				clientConnectionSocket = server.accept();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			connectionCount++; 
			_log.debug("Client connected. (Connection count: " + connectionCount + ")");
			
			ClientThread ct = new ClientThread(clientConnectionSocket);
			Thread t1 = new Thread(ct);
			t1.start();
		}
	}
}
