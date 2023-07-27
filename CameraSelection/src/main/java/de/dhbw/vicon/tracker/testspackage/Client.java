/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.testspackage;

/**
 *
 * @author andres
 */
import org.zeromq.ZMQ;

public class Client {
    public static void main(String[] args) {
        try (ZMQ.Context context = ZMQ.context(1);
             ZMQ.Socket socket = context.socket(ZMQ.REQ)) {

            // Connect the socket to the server's address and port
            socket.connect("tcp://server_ip:5555");

            // Send a request to the server
            String requestMsg = "Hello from Client";
            socket.send(requestMsg.getBytes(), 0);

            // Wait for the response from the server
            byte[] response = socket.recv(0);
            System.out.println("Received response: " + new String(response));
        }
    }
}
