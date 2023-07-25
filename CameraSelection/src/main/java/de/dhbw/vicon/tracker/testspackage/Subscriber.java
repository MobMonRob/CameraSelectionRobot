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

public class Subscriber {
    public static void main(String[] args) {
        try (ZMQ.Context context = ZMQ.context(1);
             ZMQ.Socket subscriber = context.socket(ZMQ.SUB)) {

            // Connect the subscriber to the publisher's address (e.g., tcp://localhost:5555)
            subscriber.connect("tcp://localhost:5555");

            // Subscribe to all messages (empty byte array matches all)
            subscriber.subscribe("".getBytes());

            while (true) {
                byte[] message = subscriber.recv(0);
                System.out.println("Received: " + new String(message));
            }
        }
    }
}
