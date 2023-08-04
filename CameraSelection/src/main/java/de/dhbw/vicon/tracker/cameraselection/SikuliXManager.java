/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection;

import org.sikuli.script.Pattern;
import org.sikuli.script.Region;

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
    public boolean isImageOnScreen(String imagePath, float similarity, Region region) {
        // Calibrates similarity
        Pattern imagePattern = new Pattern(imagePath).similar(similarity);

        // Tries to find the image on the screen and return wether it is there or not
        return region.exists(imagePattern) != null;
    }

}
