/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection.cameraselector;

import de.dhbw.vicon.tracker.cameraselection.cameraselector.CameraNamePathManager;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;

/**
 *
 * @author rahm-
 */
public class CameraFinder {

    private CameraNamePathManager pathManager;

    // Constructor that receives the amount of camera by parameter
    public CameraFinder(int amountCameras) {
        this.pathManager = new CameraNamePathManager(amountCameras);
    }

    public void findCamera(int index) {
        // Initialize the SikuliX screen object
        Screen screen = new Screen();
        
        // Define the image path
        String imagePath = pathManager.getPath(index);
        
        while (true) {

            try {
                // Wait for the image to appear on the screen with a timeout of 3 seconds
                screen.setAutoWaitTimeout(3);
                // Wait for the image to appear on the screen
                screen.wait(imagePath);
                // Image is found, continue with your code logic here
                System.out.println("Image found!");
                break;
                
            } catch (FindFailed e) {
                // Image did not appear within the timeout period
                System.out.println("Image not found within the specified timeout.");
                //screen.type(KEY.BRACKET_RIGHT);  // Goes to the next image
            }
        }
    }
    
    public static void main(String[] args) {
        // Initialize the SikuliX screen object
        Screen screen = new Screen();

        // Type the ']' key
        screen.type("]");
    }
}
