/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appCopy;


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
public class TrackerCopy {

    // Class the contains the methods to controll automatically the Vicon Tracker
    WinAppCopy winAppController;

    // Constructor
    private TrackerCopy () throws AWTException, IOException, MalformedURLException, InterruptedException {
        winAppController = new WinAppCopy();
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
    private String disableViconCamera(int index) throws IllegalArgumentException, InterruptedException {
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
        TrackerCopy t = new TrackerCopy();  // Instance to run the constructor and access the non-static methods
        t.disableViconCamera(11);
        Thread.sleep(5000);
        t.disableViconCamera(11);
    }
}
