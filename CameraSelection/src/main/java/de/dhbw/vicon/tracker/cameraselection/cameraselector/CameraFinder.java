/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection.cameraselector;

import de.dhbw.vicon.tracker.cameraselection.cameraselector.CameraNamePathManager;
import de.dhbw.vicon.tracker.cameraselection.SikuliXManager;

import java.util.Random;

/**
 *
 * @author rahm-
 */
public class CameraFinder extends SikuliXManager {

    private int amountCameras;
    private CameraNamePathManager pathManager;  // To get the path of the camera name images
    private float similarity;  // For SikuliX calibration

    // Constructor that receives the amount of camera by parameter
    public CameraFinder(int amountCameras) {
        this.amountCameras = amountCameras;
        this.pathManager = new CameraNamePathManager(amountCameras);
        this.similarity = 0.9f;  //0.9 avoids getting trash, more than that moy not even recognize the camera
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
        // To generate random integers
        Random random = new Random();

        // We compare the candidate n times
        int amountComparisons = 2;
        for (int i = 0; i < amountComparisons; i++){
            
            // Generate a random integer between 0 and amountCameras-1
            int randomCameraIndex = random.nextInt(amountCameras);
            
            // Checks if it is repeated
            if (randomCameraIndex == candidateIndex){
                // It makes no sense of using the same number
                i--;  // This iteration did not count
                continue;  //  tries again
                
            } else {
                // Numbers are different
                
                // Makes comparisons
                if(isCameraOnScreen(randomCameraIndex)){
                    // It found another coincidence, this is a problem
                    return false;  // There is confusion, the original match is not correct
                } else {
                    // This comparison was ok
                    continue;  // Goes to the next comparisons
                }
            }
        }  // Finishes comparisons
        
        // If it reaches this poit, it passed all the validations
        return true;  // it is correct
    }
    
    
    
     /*
    @dev: Returns the most repeated number in an array of integers
    @param: Array of integers, to get its most common element
            Lenght of the array, integer. To improve performance
    @returns: The most repeated number, integer
    @author: Andres Masis
    */
    private int mostRepeatedNumber(int[] arr, int arrLenght) {
        int maxCount = 0;  // Counts how many times a number appears in the array
        int mostRepeatedNumber = 0;

        // Gets a number to count it
        for (int i = 0; i < arrLenght; i++) {
            int count = 0;
            
            // Compares each single number with the current to count
            for (int j = 0; j < arrLenght; j++) {
                if (arr[i] == arr[j]) {
                    count++;
                }
            }

            // Checks if has to update the max
            if (count > maxCount) {
                maxCount = count;
                mostRepeatedNumber = arr[i];
            }
        }
        
        return mostRepeatedNumber;
    }
    

    /*
    @dev: Gets the index of the camera that is active in Vicon Screen in that moment
    @param: None
    @returns: The index of the camera corresponding of what is screen. Integer index
    @author: Andres Masis
     */
    private int getCurrentCamera() {
        // Sometimes the image recognition fails, we give it several trails
        int amountOfTrials = 3;  // An odd number over 3 is recommended
        int[] candidates = new int[amountOfTrials];  // In case of SikuliX confusions
        for (int trial = 0; trial < amountOfTrials; trial++) {
            
            // Goes name by name comparing to see if it coincides with what is in Vicon Screen right now
            for (int cameraIndex = 0; cameraIndex < amountCameras; cameraIndex++) {
                
                // Checks if the current sample coincides
                if (isCameraOnScreen(cameraIndex)){
                    // It coincides
                    
                    // Checks if it is correct or just a miscoincidence
                    if(isCorrect(cameraIndex)) {
                        // There were no confusions, it is a valid match
                        return cameraIndex;  // Returns the correct camera index
                    }
                
                } else {
                    // It did not coincide
                    continue; // Goes to the next camera
                }
            }
            
            // A trial ended, goes to the next trial
        }

        // If it reaches this point, it says no sample matched without confusin the current GUI.
        // That should not happen. After this point is uncertain
        
        // Returns the number that was selected the most times, however with confusion, not so reliable
        return mostRepeatedNumber(candidates, amountOfTrials);
    }

    public static void main(String[] args) {
        CameraFinder c = new CameraFinder(13);
        System.out.println("Current Camera: " + c.getCurrentCamera());
    }
}
