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

public class Publisher {
    public static void main(String[] args) {
        try (ZMQ.Context context = ZMQ.context(1);
             ZMQ.Socket publisher = context.socket(ZMQ.PUB)) {

            // Bind the publisher to an address (e.g., tcp://localhost:5555)
            publisher.bind("tcp://localhost:5555");

            // Publish messages
            for (int i = 0; i < 10; i++) {
                String message = "Message " + i;
                publisher.send(message.getBytes(), 0);
                System.out.println("Published: " + message);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
