/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package de.dhbw.vicon.tracker.cameraselection;

import java.util.Scanner;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 *
 * This class acts as the remote client.
 * It allows the user to interact with the server remotely.
 * This class only to get user input and send it to the server
 * and get responses from the server and show it to the user.
 */
public class CameraSelection {

    public static void main(String[] args) {
        // To read user´s input
        Scanner scanner = new Scanner(System.in);
        
        // Connect to the server
        String remoteMachineAddress = "Dr. Oliver´s laptop address";
        try (ZContext context = new ZContext()) {
            ZMQ.Socket client = context.createSocket(SocketType.REQ);
            client.connect(remoteMachineAddress);

            // Menu to interact with the user
            int cameraIndex = 0;
            do {
                // Ask the user for an option
                System.out.println("What camera do you want to interact with"
                        + "\nSend 0 to close the program");
                cameraIndex = scanner.nextInt();  // Get the input
                
                // Sends the camera index to the server (this is the counterpart of lines 85 and 86 of Tracker.java)
                 byte[] cameraIndexData = java.nio.ByteBuffer.allocate(4).putInt(cameraIndex).array();
                 client.send(cameraIndexData, 0);
                
                // Checks the input
                if (cameraIndex == 0) {
                    // Closes the app
                    
                    // Waits for the server response (cpunterpart of line  92 of Tracker.java)
                    byte[] reply = client.recv();  // Gets the bytes from the server
                    System.out.println(new String(reply));  // Print that message
                    
                    // Final message from client
                    System.out.println("Program closed succesfully");
                    
                } else if (cameraIndex < 1 || cameraIndex > 13) {
                    // Valid camera
                    
                    // Waits for the server response to ensure everything is fine with the camera index (counterpart of line 98 of Tracker.java)
                    byte[] reply = client.recv();
                    int result = java.nio.ByteBuffer.wrap(reply).getInt();
                    
                    // Ask the user if it wants to enable or disable
                    System.out.println("Press 0 to disable the camera"
                            + "\nPress 1 to enable the camera");
                    int option = scanner.nextInt();  // Gets user input
                    
                    // Checks that input
                    if (option == 0 || option == 1) {
                        // Will enable/disable the camera, that is responsability of the remote server    
                        
                        // Sends the option to the server (counterpart of line 72 of Tracker.java)
                        byte[] optionData = java.nio.ByteBuffer.allocate(4).putInt(option).array();
                        client.send(optionData, 0);
                        // Waits fot the server response (counterpart of lines 106 and 107 of Tracker.java)
                        byte[] reply2 = client.recv();
                        int result2 = java.nio.ByteBuffer.wrap(reply2).getInt();

                        // Checks if the connection was established properly
                        if (result == 1 && result2 == 1) {
                            System.out.println("Message successfully sended to the server");
                        } else {
                            System.out.println("The connection with the server produced an error.");
                        }

                    } else {
                        // Note this else is for an invalid option, not invalid camera index for enable or disable
                        System.out.println("Invalid option");
                    }

                } else {
                    // Note this else is for an invalid camera index, not invalid option for enable or disable
                    System.out.println("Invalid camera index");
                }

            } while (cameraIndex != 0);
            
            // Closing operations after exiting the menu (while)
            context.destroy();  // Closes the JeroMQ sockets context
            System.exit(0);  // Stops the execution
        }

    }
}
