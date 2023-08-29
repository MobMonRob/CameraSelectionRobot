/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TestJeroMQ;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class JeroMQClientExample {
    public static void main(String[] args) {
        try (ZContext context = new ZContext()) {
            Socket client = context.createSocket(SocketType.REQ);
            client.connect("tcp://182.52.247.41:5555");
            System.out.println("Client started");
            for (int i = 0; i < 10; i++) {
                int request = i;
                int request2 = i+20;
                
                byte[] data = java.nio.ByteBuffer.allocate(4).putInt(request).array();
                byte[] data2 = java.nio.ByteBuffer.allocate(4).putInt(request2).array();
                
                client.send(data, 0);
                byte[] reply = client.recv();
                int result = java.nio.ByteBuffer.wrap(reply).getInt();
                System.out.println("Received reply " + i + ": " + result);
                
                client.send(data2, 0);
                byte[] reply2 = client.recv();
                int result2 = java.nio.ByteBuffer.wrap(reply2).getInt();
                System.out.println("Received reply " + i + ": " + result2);

            }
            
            String request = "Hello";
            client.send(request.getBytes(ZMQ.CHARSET), 0);
            byte[] reply = client.recv(0);
            System.out.println("Received: " + new String(reply));
        
            context.destroy();
            System.exit(0);
        }
    }
}
