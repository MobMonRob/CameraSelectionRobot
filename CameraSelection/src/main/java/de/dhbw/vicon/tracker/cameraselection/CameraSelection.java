/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package de.dhbw.vicon.tracker.cameraselection;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 *
 * This class acts as the remote client. It allows the user to interact with the
 * server remotely. This class only to get user input and send it to the server
 * and get responses from the server and show it to the user.
 */
public class CameraSelection {

    /*
    This main method starts the sockets to allow this client to connect the server
    After the connection is established, it runs a menu for the user to interact with a camera
    It is a while(true) to keep it running
    In the menu it first asks the user to enter a camera index
    If the user enters 0 it means to close the app
    This client tells the server to shut down and the menu stops, the execution ends
    If the user enters a camera index between 1 and 13 it is a valid index
    If the camera index is valid and no 0, it asks the user if it wants to enable or disable
    0 is to disable, 1 is to enable, any other option is invalid of enable/disable
    If the camera index is valid and the enable/disable option too, performs the action
    The server is in charged of that, so this client only sends those 2 details to the server
    And then it waits the reply from the server to print it to the user
    If the user enters a negative camera index or over 13 it is invalid
     */
    public static void main(String[] args) {
        // To read user´s input
        Scanner scanner = new Scanner(System.in);

        // Connect to the server
        try (ZContext context = new ZContext()) {
            ZMQ.Socket client = context.createSocket(SocketType.REQ);
            String remoteMachineAddress = "tcp://localhost:5555";  // Dr. Olver´s laptop in the lab address 
            client.connect(remoteMachineAddress);

            // Menu to interact with the user
            int cameraIndex = 0;
            while (true) {
                // Ask the user for an option
                System.out.println("What camera do you want to interact with"
                        + "\nSend 0 to close the program");

                // Try-catch in case the user enters a non integer value
                try {
                    cameraIndex = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter an integer between 0 to 13.");
                    scanner.nextLine();
                    continue;
                }

                // Checks the input
                if (cameraIndex == 0) {
                    // Has to close the app

                    // Sends the camera index 0 to the server to shut it down (counterpart of lines 98-99 of Tracker.java)
                    byte[] cameraIndexData = java.nio.ByteBuffer.allocate(4).putInt(cameraIndex).array();
                    client.send(cameraIndexData, 0);

                    // Waits for the server response (counterpart of line 107-108 of Tracker.java)
                    byte[] reply = client.recv();
                    System.out.println(new String(reply));

                    break;

                } else if (cameraIndex >= 1 && cameraIndex <= 13) {
                    // Valid camera

                    // Ask the user if it wants to enable or disable
                    System.out.println("Press 0 to disable the camera"
                            + "\nPress 1 to enable the camera");
                    int option;

                    // Try-catch in case the user enters a non integer value
                    try {
                        option = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter integer 0 or 1.");
                        scanner.nextLine();
                        continue;
                    }

                    // Checks that input
                    if (option == 0 || option == 1) {
                        // Valid camera index and enable/disable input   

                        // Sends the camera index to the server (counterpart of lines 98-99 of Tracker.java)
                        byte[] cameraIndexData = java.nio.ByteBuffer.allocate(4).putInt(cameraIndex).array();
                        client.send(cameraIndexData, 0);

                        // Waits for the response of the server the cameraIndex was received (counterpart of lines 117-118 of Tracker.java)
                        client.recv(0);

                        // Sends the option to the server (counterpart of lines 121-122 of Tracker.java)
                        byte[] optionData = java.nio.ByteBuffer.allocate(4).putInt(option).array();
                        client.send(optionData, 0);

                        // Waits for the response of the server the option was received (counterpart of lines 125-126 of Tracker.java)
                        client.recv(0);

                        // Waits for the server response of the enable/disable action (counterpart of line 138 of Tracker.java)
                        byte[] reply = client.recv();
                        System.out.println(new String(reply));

                    } else {
                        System.out.println("Invalid enable/disable option");
                    }

                } else {
                    System.out.println("Invalid camera index");
                }

            }

            // Out of the loop (menu is not running anymore)
            context.destroy();  // Closes the JeroMQ sockets context
            System.out.println("Program closed succesfully");
        }

    }
}
