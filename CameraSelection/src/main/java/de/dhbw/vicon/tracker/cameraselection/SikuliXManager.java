/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

/**
 *
 * @author rahm-
 */
public class SikuliXManager {

    private Screen screen;  // SikuliX screen

    public SikuliXManager() {
        this.screen = ScreenSingleton.getScreen();  // Gets the Singleton instance of the SikuliX screen
    }

    
    /*
    @dev: With SikuliX, gets if an image is on the screen or not
    @param: String with the absolute path of the image. So SikuliX can load it
            Float of similarity. Parameter to adjust. Must be between 0 and 1
    @returns: True if SikuliX found the given image on the screen
              False if SikuliX could not found the given image in the screen
    @author: Andres Masis
    */
    public boolean isImageOnScreen(String imagePath, float similarity) {
        // Calibrates similarity
        Pattern imagePattern = new Pattern(imagePath);  // Converts the string to a pattern to adjust similarity
        imagePattern.similar(similarity);  // Adjusts similarity

        // Tries to find the image on the screen
        try {
            // This makes the comparison
            screen.wait(imagePattern);

            // If it stays in the try block, it means SikuliX found a coincidence
            return true;  // The camera is in the screen

        } catch (FindFailed e) {
            // If it falls in the catch blocks it means that it did not coincide
            return false; // The camera was not on the screen
        }
    }

}
