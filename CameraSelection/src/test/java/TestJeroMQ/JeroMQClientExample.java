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
            client.connect("tcp://192.168.10.1:5555");
            System.out.println("Client started");
            for (int i = 0; i < 10; i++) {
                String request = "Hello " + i;
                client.send(request.getBytes(ZMQ.CHARSET), 0);
                byte[] reply = client.recv(0);
                System.out.println("Received: " + new String(reply));
            }
        }
    }
}
