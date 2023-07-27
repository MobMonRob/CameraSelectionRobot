/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.testspackage;

import org.zeromq.ZMQ;

public class Server {
    public static void main(String[] args) {
        try (ZMQ.Context context = ZMQ.context(1);
             ZMQ.Socket socket = context.socket(ZMQ.REP)) {

            // Bind the socket to a specific address and port
            socket.bind("tcp://10.172.2.129:5555");

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
