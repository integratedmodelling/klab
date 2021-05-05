package org.integratedmodelling.klab.engine.services;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class AgentCheckThread extends Thread {

	private Socket socket;
	private int load;
	
	public AgentCheckThread(Socket socket, int load) {
		this.socket = socket;
		this.load = load;
		this.setName("agent-check-thread");
	}
	
    public void run() {
        try {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println( Integer.toString(load)+"%\n"); 
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
