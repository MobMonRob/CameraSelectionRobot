/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection.cameraselector;

import de.dhbw.vicon.tracker.cameraselection.SikuliXManager;

import java.awt.Toolkit;
import java.util.BitSet;
import java.util.concurrent.ThreadLocalRandom;
import org.sikuli.script.Region;

/**
 *
 * @author rahm-
 */
public class CameraFinder extends SikuliXManager {

    private final int amountCameras;
    private final CameraNamePathManager pathManager;  // To get the path of the camera name images
    private final float similarity;  // For SikuliX calibration
    private final Region region;  // SikuliX region
    private int lastCameraIndex;  // To check directly the last camera used

    // Constructor that receives the amount of camera by parameter
    public CameraFinder(int amountCameras) {
        this.amountCameras = amountCameras;
        this.pathManager = new CameraNamePathManager(amountCameras);
        this.similarity = 0.95f;  //0.95 avoids getting trash, more than that may not even recognize the camera
        this.region = new Region(295, 20, 190, 235);  // This is the region where the camera names are located
        this.lastCameraIndex = 0;
    }

    /*
    @dev: With SikuliX, gets if an image of a camera name is on the screen or not
          Relies on function of de.dhbw.vicon.tracker.cameraselection.SikuliXManager;
    @param: Integer of the camera index
    @returns: True if SikuliX found the given image on the screen
              False if SikuliX could not found the given image in the screen
    @author: Andres Masis
     */
    private boolean isCameraOnScreen(int cameraIndex) {
        // Gets the absolute path in a string of the given camera
        String imagePath = pathManager.getPath(cameraIndex);

        // Returns either the camera image is on the screen or not
        return isImageOnScreen(imagePath, this.similarity, this.region);  // Function from SikuliXManager                    
    }

    /*
    @dev: SikuliX is based on image recognition, therefore is prone to confusions and error
          When it detects a camera it is not 100% sure that is the real one
          This function selects randomly other samples to see if they coincide or not
          If only the given candidate coincides, it means it is correct
          But, if another image coincides, there is confusion and it may be problematic
    @param: The candidate. Int
    @returns: True in case the candidate is unique
              False in case another image also coincides
    @author: Andres Masis
     */
    private boolean isCorrect(int candidateIndex) {
        // We set the bit of each random number index to true when used
        BitSet checkedIndexes = new BitSet(amountCameras);
        checkedIndexes.set(candidateIndex);  // We set the candidate as used

        // To generate random numbers, more thread safe and faster
        // Compare the candidate n times
        int amountComparisons = 2;  // We compare the candidate n times
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < amountComparisons; i++) {
            // Generate a random integer between 0 and amountCameras-1
            int randomCameraIndex;
            do {
                randomCameraIndex = random.nextInt(amountCameras);
            } while (checkedIndexes.get(randomCameraIndex));  // To make sure the random number is not used yet

            // Makes comparisons
            if (isCameraOnScreen(randomCameraIndex)) {
                // It found another coincidence, this is a confusion problem
                return false;  // The original match is not correct
            }

            // We set the bit of the random index to true
            checkedIndexes.set(randomCameraIndex);

            // This comparison was ok, goes to the next comparisons
        }  // Finishes comparisons

        // If it reaches this point, it passed all the validations
        return true;  // it is correct
    }


    /*
    @dev: Gets the index of the camera that is active in Vicon Screen in that moment
    @param: None
    @returns: The index of the camera corresponding of what is screen. Integer index
    @author: Andres Masis
    */
    public int getCurrentCamera() {
        // It is very likely that the last camera used is still the same on screen, we start checking for that one
        if (isCameraOnScreen(this.lastCameraIndex) && isCorrect(this.lastCameraIndex)) {
            return lastCameraIndex;
        }

        // Goes camera sample 1 by 1 checking if it is the one in the current Vicon GUI
        for (int cameraIndex = 0; cameraIndex < amountCameras; cameraIndex++) {
            // Validations
            if (cameraIndex != lastCameraIndex &&  // Already checked the last camera, on its turn, we skip it
            isCameraOnScreen(cameraIndex) &&  // Checks if the current sample coincies with the Vicon GUI
            isCorrect(cameraIndex))  // Validates it is not a false positive
            {
                
                // Passed all the validations, it is the correct one
                this.lastCameraIndex = cameraIndex; // Update the last found camera
                return cameraIndex; // Return the camera index
            }
        }

        return -1; // Return -1 if no match found
    }
    

    public static void main(String[] args) {
        CameraFinder c = new CameraFinder(13);
        System.out.println("Current Camera: " + c.getCurrentCamera());
        Toolkit.getDefaultToolkit().beep();
    }

}
