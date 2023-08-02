/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection.cameraselector;

import de.dhbw.vicon.tracker.cameraselection.cameraselector.CameraNamePathManager;
import de.dhbw.vicon.tracker.cameraselection.ScreenSingleton;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.sikuli.script.Pattern;

/**
 *
 * @author rahm-
 */
public class CameraFinder {

    private CameraNamePathManager pathManager;  // To get the path of the camera name images
    private Screen screen;  // SikuliX screen
    private int amountCameras;

    // Constructor that receives the amount of camera by parameter
    public CameraFinder(int amountCameras) {
        this.amountCameras = amountCameras;
        this.pathManager = new CameraNamePathManager(amountCameras);
        this.screen = ScreenSingleton.getScreen();  // Gets the Singleton instance of the SikuliX screen
    }

    /*
    @dev: Gets the index of the camera that is active in Vicon Screen in that moment
    @param: None
    @returns: The index of the camera corresponding of what is screen. Integer index
    @author: Andres Masis
     */
    private int getCurrentCamera() {
        // Goes name by name comparing to see if it coincides with what is in Vicon Screen right now
        for (int cameraIndex = 0; cameraIndex < amountCameras; cameraIndex++) {
            // Gets a sample image to compare if it is the same of what is on screen
            String imagePath = pathManager.getPath(cameraIndex);

            try {
                // Wait for the image to appear on the screen, this makes the comparison
                screen.wait(imagePath);

                // If it stays in the try block, it means the image was found.
                return cameraIndex;  // The search stops

            } catch (FindFailed e) {
                // If it falls in the catch blocks it means that it did not coincide
                continue;  // Goes to the next sample image to the next sample to compare it
            }
        }

        // If it reaches this point, it says no sample matches the GUI. That should not happen.
        return -1;  // Error number
    }

    public static void main(String[] args) {
        CameraFinder c = new CameraFinder(13);
        String path = c.pathManager.getPath(12);
        System.out.println("Found Path: " + path);
        Pattern button = new Pattern(path);
        button.similar(0.5);
        try {
            c.screen.click(button);
        } catch (FindFailed e) {
            e.printStackTrace();
        }
    }
}
