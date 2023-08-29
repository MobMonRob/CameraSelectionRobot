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
            return "The camera was disabled succesfully";
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

    /*
    In this main method
    It creates an instance of this class
    This to run the constructor, there the WinAppController is automatically started also
    Also allows to call the non static methods of this class
    Then it creates the socket connection, this class acts as the server
    After that it start a while true to keep this server listening for requests
    If the server receives 0 as camera index it shuts down everything
    If it receives another index it means it is the index of an existing camera
    In that case it also needs to receive another value to know if it must enable or disable
    When it has the valid index and the enable or disable value, perfroms the action
     */
    public static void main(String[] args) throws AWTException, IOException, MalformedURLException, InterruptedException {
        Tracker t = new Tracker();  // Instance to run the constructor and access the non-static methods

        // Creates the sockets
        try (ZContext context = new ZContext()) {
            ZMQ.Socket server = context.createSocket(SocketType.REP);
            server.bind("tcp://localhost:5555");  // Localhost because this is the server

            // While(true) to keep the server listening
            while (true) {
                // Gets the camera index from the client
                // (counterpart to lines 66-67 or lines 97-98 of CameraSelection.java)
                byte[] cameraIndexData = server.recv();
                int cameraIndex = java.nio.ByteBuffer.wrap(cameraIndexData).getInt();

                // Checks if it is either the close app value or an index to interact with a camera
                if (cameraIndex == 0) {
                    // Has to close the app
                    t.stopProgram();

                    // Informs the client of the closing status (counterpart of lines 70-71 of CameraSelection.java)
                    String response = "Server succesfully stopped";
                    server.send(response.getBytes(ZMQ.CHARSET), 0);

                    // Leaves the loop to end the execution
                    break;

                } else {
                    // Got an index of a camera to interact with

                    // Tells the client the camera index was received (counterpart to line 101 of CameraSelection.java)
                    boolean value1 = true;
                    server.send(new byte[]{(byte) (value1 ? 1 : 0)}, 0);

                    // Gets the option from the client (counterpart to lines 104-105 of CameraSelection.java)
                    byte[] optionData = server.recv();
                    int option = java.nio.ByteBuffer.wrap(optionData).getInt();

                    // Tells the client the option was received (counterpart to line 108 of CameraSelection.java)
                    boolean value2 = true;
                    server.send(new byte[]{(byte) (value2 ? 1 : 0)}, 0);

                    // Executes the enable/disable action
                    cameraIndex--;  // Arrays are from 0 to n-1, human input comes from 1 to n
                    String response;
                    if (option == 0) {
                        response = t.disableViconCamera(cameraIndex);
                    } else {
                        response = t.enableViconCamera(cameraIndex);
                    }

                    // Tells the client the output of enable/disable (counterpart of lines 111-112 of CameraSelection.java)
                    server.send(response.getBytes(ZMQ.CHARSET), 0);
                }

            }

            // Out of the loop (server is not listening anymore)
            context.destroy();  // Closes the JeroMQ sockets context
        }
    }
}
