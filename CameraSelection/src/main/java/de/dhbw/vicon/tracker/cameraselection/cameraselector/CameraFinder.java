/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection.cameraselector;

import de.dhbw.vicon.tracker.cameraselection.SikuliXManager;
import java.awt.Toolkit;
import java.util.HashSet;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author rahm-
 */
public class CameraFinder extends SikuliXManager {

    private final int amountCameras;
    private final CameraNamePathManager pathManager;  // To get the path of the camera name images
    private final float similarity;  // For SikuliX calibration

    // Constructor that receives the amount of camera by parameter
    public CameraFinder(int amountCameras) {
        this.amountCameras = amountCameras;
        this.pathManager = new CameraNamePathManager(amountCameras);
        this.similarity = 0.95f;  //0.95 avoids getting trash, more than that moy not even recognize the camera
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
        return isImageOnScreen(imagePath, this.similarity);  // Function from SikuliXManager                    
    }

    /*
    @dev: SikuliX is based on image recognition, therefore is prone to confusions and error
          When it detects a camera it is not 100% sure that is the real one
          This function selects randomly other samples to see if the coincide or not
          If only the given candidate coincides, it means it is correct
          But, if anothere image coincides, there is confusion and it may be problematic
    @param: The candidate. Int
    @returns: True in case the candidate is unique
              False in case another image also coincides
    @author: Andres Masis
     */
    private boolean isCorrect(int candidateIndex) {
        Random random = new Random();  // To generate random integers

        // We use a set to store the already checked indexes
        HashSet<Integer> checkedIndexes = new HashSet<>(); // We add the candidate index to the set
        checkedIndexes.add(candidateIndex);

        int amountComparisons = 2;  // We compare the candidate n times
        for (int i = 0; i < amountComparisons; i++) {

            // Generate a random integer between 0 and amountCameras-1
            int randomCameraIndex;
            do {
                randomCameraIndex = random.nextInt(amountCameras);
            } while (checkedIndexes.contains(randomCameraIndex));  // To make sure the random number is not used yet

            // Makes comparisons
            if (isCameraOnScreen(randomCameraIndex)) {
                // It found another coincidence, this is a confusion problem
                return false;  // The original match is not correct
            }

            // We add the random index to the set
            checkedIndexes.add(randomCameraIndex);
            
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
        // Using Streams is slightly more efficient in time than a for loop
        int match = IntStream.range(0, amountCameras) // Creates streams from 0 to amountCameras-1
                .filter(cameraIndex -> isCameraOnScreen(cameraIndex) && isCorrect(cameraIndex)) // Conditon to meet
                .findFirst() // Stops at the first match
                .orElse(-1);  // Sets -1 in case of no matches at all

        return match;
    }

    public static void main(String[] args) {
        CameraFinder c = new CameraFinder(13);
        System.out.println("Current Camera: " + c.getCurrentCamera());
        Toolkit.getDefaultToolkit().beep();
    }
}
