/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.testspackage;

/**
 *
 * @author andres
 */
import org.zeromq.SocketType;
import org.zeromq.ZMQ;
import org.zeromq.ZContext;

public class Client {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            // Create a socket of type REQ (request)
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);

            // Connect the socket to the server
            // socket.connect("tcp://10.172.2.129:5555");  // Andres IP
            socket.connect("tcp://localhost:5555");  // Lab IP

            for (int i = 0; i < 5; i++) {
                String message = "Request " + i;
                // Send a request to the server
                socket.send(message.getBytes(), 0);
                System.out.println("Sent: " + message);

                // Wait for the response from the server
                byte[] reply = socket.recv(0);
                System.out.println("Received: " + new String(reply));
            }
        }
    }
}
