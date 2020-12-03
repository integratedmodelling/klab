package org.integratedmodelling.klab.engine.services;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.integratedmodelling.klab.Logging;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceCheck {

	//to be set from config
	int port = 6666;
	int load = 100;
	public void start() {
       try (ServerSocket serverSocket = new ServerSocket(port)) {
    	   
    	   Logging.INSTANCE.info("Agent Check Service is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                Logging.INSTANCE.info("HAProxy Connected");
                System.out.println("New client connected");
 
                new AgentCheckThread(socket, load).start();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }	
	}
	public void setLoad(int load) {
		this.load = load;
	}
}
