package de.dhbw.vicon.tracker.cameraselection.api;

import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

/**
 * Make sure to build the .jar with the maven-assembly-plugin This should be
 * added in the pom file of the project This plugin is found in the first answer
 * of:
 * https://stackoverflow.com/questions/1729054/including-dependencies-in-a-jar-with-maven
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
    private String enableViconCamera(int index) throws IllegalArgumentException, InterruptedException {
        winAppController.clickOnCamera(index);  // Selects the camera

        // We look for false. Enable is from false to true. To enable it first must be disabled (false)
        if (winAppController.clickCheckbox("false")) {
            return "\nThe camera was enabled succesfully";
        } else {
            return "\nAn error occured. Check that the Vicon Tracker GUI is running on full screen and no other programs on the screnn on the server machine";
        }
    }

    /*
    @dev: This method clicks the disables a given camera
          Note that it throws an excpetion, so it also must be added when calling this method
    @param: index of the desired camera (integer between 0 and 12)
    @return: Message telling either the camera was disabled successfully or it was already disabled from before
    @author: Anddres Masis
     */
    private String disableViconCamera(int index) throws IllegalArgumentException, InterruptedException {
        winAppController.clickOnCamera(index);  // Selects the camera

        // We look for true. Disable is from true to false. To disable it first must be enabled (true)
        if (winAppController.clickCheckbox("true")) {
            return "\nThe camera was disabled succesfully";
        } else {
            return "\nAn error occured. Check that the Vicon Tracker GUI is running on full screen and no other programs on the screnn on the server machine";
        }
    }

    /*
    @dev: This method gets which cameras are enabled or disabled
    @param: None
    @return: Message telling for all cameras if it is enabled or disabled
    @author: Andres Masis
     */
    private String getDisabledCameras() throws InterruptedException {
        // Element to store the result to return
        StringBuilder response = new StringBuilder();

        // Gets the states of the cameras
        boolean[] disabledArray = winAppController.getDisabledCameras();
        // Forms a string to make it more readable
        for (int i = 0; i < 13; i++) {
            response.append("Camera ").append(Integer.toString(i + 1)).append(" is ");
            if (disabledArray[i]) {
                response.append("disabled\n");
            } else {
                response.append("enabled\n");
            }
        }
        return response.toString();
    }
    
    private void closeFirewallPopup() {
        winAppController.closeFirewall();
    }


    /*
    @dev: This method clicks the disables a given camera
          Note that it throws an excpetion, so it also must be added when calling this method
    @param: index of the desired camera (integer between 0 and 12)
    @return: None
    @author: Andres Masis
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
        Thread.sleep(30000);  // Gives 30 seconds for the Vicon Tracker to load everything on GUI.
        // Creates the sockets
        try (ZContext context = new ZContext()) {
            ZMQ.Socket server = context.createSocket(SocketType.REP);
            server.bind("tcp://*:5555");  // address * to listen 

            // Closes the firewall pop up window
            t.closeFirewallPopup();

            // While(true) to keep the server listening
            while (true) {
                // Gets the camera index from the client
                byte[] cameraIndexData = server.recv();
                int cameraIndex = java.nio.ByteBuffer.wrap(cameraIndexData).getInt();

                // Checks if it is either the close app value or an index to interact with a camera
                if (cameraIndex == 0) {
                    // Has to close the app
                    t.stopProgram();

                    // Informs the client of the closing status
                    String response = "\nServer succesfully stopped";
                    server.send(response.getBytes(ZMQ.CHARSET), 0);

                    // Leaves the loop to end the execution
                    break;

                } else if (cameraIndex == 14) {
                    // Gets if the cameras are enabled or disabled

                    // Tells the client the enable/disable states of all cameras
                    String response = t.getDisabledCameras();
                    server.send(response.getBytes(ZMQ.CHARSET), 0);

                } else {
                    // Got an index of a camera to interact with

                    // Tells the client the camera index was received
                    boolean syncronizationFlag1 = true;
                    server.send(new byte[]{(byte) (syncronizationFlag1 ? 1 : 0)}, 0);

                    // Gets the enable7disable option from the client
                    byte[] optionData = server.recv();
                    int option = java.nio.ByteBuffer.wrap(optionData).getInt();

                    // Tells the client the option was received
                    boolean syncronizstionFlag2 = true;
                    server.send(new byte[]{(byte) (syncronizstionFlag2 ? 1 : 0)}, 0);

                    // Executes the enable/disable action
                    cameraIndex--;  // Arrays are from 0 to n-1, human input comes from 1 to n
                    String response;
                    if (option == 0) {
                        response = t.disableViconCamera(cameraIndex);
                    } else {
                        response = t.enableViconCamera(cameraIndex);
                    }

                    // Gets the signal from the client to send the final response
                    server.recv(0);

                    // Tells the client the output of enable/disable 
                    server.send(response.getBytes(ZMQ.CHARSET), 0);
                }

            }

            // Out of the loop (server is not listening anymore)
            context.destroy();  // Closes the JeroMQ sockets context
        }
    }
}
