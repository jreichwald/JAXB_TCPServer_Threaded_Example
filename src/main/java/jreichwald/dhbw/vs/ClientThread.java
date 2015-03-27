package jreichwald.dhbw.vs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Client Thread for handling client-threads on the server-side.
 * @author Prof. Dr. Julian Reichwald <julian.reichwald@dhbw-mannheim.de>
 *
 */
public class ClientThread implements Runnable {

	/**
	 * Log Instance
	 */
	private Logger _log = LogManager.getLogger(ClientThread.class);
	
	/**
	 * Client Connection
	 */
	private Socket _clientSocket ;
	
	/**
	 * Default Constructor
	 * @param socket
	 */
	public ClientThread(Socket socket) {
		this._clientSocket = socket;
	}
	
	
	@Override
	public void run() {
		
		try {

			// Bind input- and output streams
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(
							_clientSocket.getInputStream()));

			//wait for stream
			while (!reader.ready())
				Thread.sleep(20);

			// Create JAXB Unmarshaller
			JAXBContext ctx = JAXBContext.newInstance(Message.class);
			Unmarshaller unmarshaller = ctx.createUnmarshaller();
			
			// Unmarshal the message
			Message msg = (Message)unmarshaller.unmarshal(reader);
			
			_log.debug("Object unmarshalled: " + msg);
			
			// wait for a random time to showcase multiple client threads.
			Thread.sleep(10000);
			
			// Log output when thread finishes.
			_log.debug("Finished");
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
