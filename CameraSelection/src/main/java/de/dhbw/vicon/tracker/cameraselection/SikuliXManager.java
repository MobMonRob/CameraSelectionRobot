/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection;

import org.sikuli.script.Pattern;
import org.sikuli.script.Screen;

/**
 *
 * @author rahm-
 */
public class SikuliXManager {

    /*
    @dev: With SikuliX, gets if an image is on the screen or not
    @param: String with the absolute path of the image. So SikuliX can load it
            Float of similarity. Parameter to adjust. Must be between 0 and 1
    @returns: True if SikuliX found the given image on the screen
              False if SikuliX could not found the given image in the screen
    @author: Andres Masis
     */
    public boolean isImageOnScreen(String imagePath, float similarity) {
        // Creates the SikuliX screen
        Screen screen = new Screen();
        
        // Calibrates similarity
        Pattern imagePattern = new Pattern(imagePath);  // Converts the string to a pattern to adjust similarity
        imagePattern.similar(similarity);  // Adjusts similarity

        // Tries to find the image on the screen
        if (screen.exists(imagePattern) != null) {
            // If exists() it means SikuliX found a coincidence
            return true;  // The camera is on the screen
            
        } else {
            // If exists() returns null, it means that it did not coincide
            return false; // The camera was not on the screen
        }
    }

}
