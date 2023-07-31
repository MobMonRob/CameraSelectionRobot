/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.testspackage;

import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class Server {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Create a socket of type REP (reply)
            ZMQ.Socket socket = context.createSocket(SocketType.REP);

            // Bind the socket to a specific address and port
            // socket.bind("tcp://10.172.2.129:5555");  // Andres IP
            socket.bind("tcp://localhost:5555");  // Lab IP

            while (!Thread.currentThread().isInterrupted()) {
                byte[] request = socket.recv(0); // Wait for a request from the client
                // Process the request
                System.out.println("Received request: " + new String(request));
                // Send a response back to the client
                socket.send("Hello from Server".getBytes(), 0);
            }
        }
    }
}
