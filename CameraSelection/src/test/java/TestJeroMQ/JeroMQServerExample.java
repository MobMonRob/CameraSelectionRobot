/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestJeroMQ;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class JeroMQServerExample {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            Socket server = context.createSocket(SocketType.REP);
            server.bind("tcp://*:5555");
            System.out.println("Server started");
            while (!Thread.currentThread().isInterrupted()) {
                byte[] request = server.recv(0);
                System.out.println("Received: " + new String(request));
                String response = "World";
                server.send(response.getBytes(ZMQ.CHARSET), 0);
            }
        }
    }
}
