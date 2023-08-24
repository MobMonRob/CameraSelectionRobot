package de.dhbw.vicon.tracker.cameraselection.api;

import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 *
 * This class acts as the server that's running on the same computer of the
 * Vicon Tracker. It allows the user to enable and disable the cameras remotely.
 * This class only deals with the sockets, for the logic it relies on another
 * class (WinAppController)
 */
public class Tracker {

    // Class the contains the methods to controll automatically the Vicon Tracker
    WinAppController winAppController;

    // Constructor
    private Tracker() throws AWTException, IOException, MalformedURLException, InterruptedException {
        winAppController = new WinAppController();
    }

    /*
    @dev: This method clicks the enables a given camera
          Note that it throws an excpetion, so it also must be added when calling this method
    @param: index of the desired camera (integer between 0 and 12)
    @return: Message telling either the camera was enabled successfully or it was already enabled from before
    @author: Anddres Masis
     */
    private String enableViconCamera(int index) throws IllegalArgumentException {
        winAppController.clickOnCamera(index);  // Selects the camera

        // We look for false. Enable is from false to true. To enable it first must be disabled (false)
        if (winAppController.clickCheckbox("false")) {
            return "The camera was enabled succesfully";
        } else {
            return "The camera is already enabled";
        }
    }

    /*
    @dev: This method clicks the disables a given camera
          Note that it throws an excpetion, so it also must be added when calling this method
    @param: index of the desired camera (integer between 0 and 12)
    @return: Message telling either the camera was disabled successfully or it was already disabled from before
    @author: Anddres Masis
     */
    private String disableViconCamera(int index) throws IllegalArgumentException {
        winAppController.clickOnCamera(index);  // Selects the camera

        // We look for true. Disable is from true to false. To disable it first must be enabled (true)
        if (winAppController.clickCheckbox("true")) {
            return  "The camera was disabled succesfully";
        } else {
            return "The camera is already disabled";
        }
    }

    /*
    @dev: This method clicks the disables a given camera
          Note that it throws an excpetion, so it also must be added when calling this method
    @param: index of the desired camera (integer between 0 and 12)
    @return: None
    @author: Anddres Masis
     */
    private void stopProgram() throws IOException {
        winAppController.endSession();
    }

    public static void main(String[] args) throws AWTException, IOException, MalformedURLException, InterruptedException {
        Tracker t = new Tracker();  // Instance to run the constructor and access the non-static methods

        // Creates the sockets
        try (ZContext context = new ZContext()) {
            ZMQ.Socket server = context.createSocket(SocketType.REP);
            server.bind("tcp://localhost:5555");  // Localhost because this is the server

            // While true to keep the server listening
            while (true) {
                // Gets the camera selection value from the client (this is the counterpart of lines 30 and 40 of CameraSelection.java)
                byte[] cameraIndexData = server.recv();  // Receives the bytes
                int cameraIndex = java.nio.ByteBuffer.wrap(cameraIndexData).getInt();  // Parse to int

                if (cameraIndex == 0) {
                    // 0 is to stop the app (this is the counterpart of lines 40-50 in CameraSelection.java)
                    
                    t.stopProgram(); // Stops the WinAppDriver session
                    server.send("Server stopped".getBytes(ZMQ.CHARSET), 0);  // Informs the client
                    break;  // Exits the loop
                    
                } else {
                    // Must enable/disable a camera
                    
                    // Response to inform everything is good with the camera indexc (counterpart of line 57 of CameraSelection.java)
                    byte[] reply1 = java.nio.ByteBuffer.allocate(4).putInt(1).array();  // 1 as reply to inform everything is good with the camera index
                    server.send(reply1, 0);  // Sends the reply to the client
                    
                    // Gets the option value from the client (counterpart of lines 70 and 71 of CameraSelection.java)
                    byte[] optionData = server.recv();  // Receives the bytes
                    int option = java.nio.ByteBuffer.wrap(optionData).getInt();
                    // Send a reply to the client (counterpart of lines 73 and 74 of CameraSelection.java)
                    byte[] reply2 = java.nio.ByteBuffer.allocate(4).putInt(1).array(); // 1 as reply to inform everything is good
                    server.send(reply2, 0); // Sends the reply to the client
                    
                    // Perform the enable/disable action
                    String response = "";  // To store the response of the enable/disable action
                    if(option == 0) {
                        // 0 is to disable
                        response = t.disableViconCamera(cameraIndex);  // Stores the response in the variable
                    } else if(option == 1) {
                        // 1 is to disable
                        response = t.enableViconCamera(cameraIndex);  // Stores the response in the variable
                    } else {
                        // Any othef number from the client (invalid option)
                        response = "Action not performed by invalid user input";
                    }

                    // Sends the response to the client
                    server.send(response.getBytes(ZMQ.CHARSET), 0);

                }

                // Closing operations after exiting the server listening (while)
                context.destroy();  // Closes the JeroMQ sockets context
                System.exit(0);  // Stops the execution
            }
        }
    }
}
