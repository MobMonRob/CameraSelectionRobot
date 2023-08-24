/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestJeroMQ;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class JeroMQClientServerExample {

    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            Socket server = context.createSocket(SocketType.REP);
            server.bind("tcp://localhost:5555");
            System.out.println("Server started");
            while (!Thread.currentThread().isInterrupted()) {
                byte[] request1 = server.recv();
                int i = java.nio.ByteBuffer.wrap(request1).getInt();
                System.out.println("Received request: " + i);
                byte[] reply = java.nio.ByteBuffer.allocate(4).putInt(i).array();
                server.send(reply, 0);
                
                byte[] request2 = server.recv();
                int i2 = java.nio.ByteBuffer.wrap(request2).getInt();
                System.out.println("Received request: " + i2); 
                byte[] reply2 = java.nio.ByteBuffer.allocate(4).putInt(i2).array();
                server.send(reply2, 0);
                
                byte[] request = server.recv(0);
                System.out.println("Received: " + new String(request));
                String response = "World";
                server.send(response.getBytes(ZMQ.CHARSET), 0);
            }
            
            
            context.destroy();
            System.exit(0);
        }
    }
}

