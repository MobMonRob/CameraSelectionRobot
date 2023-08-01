/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection.cameraselector;

import de.dhbw.vicon.tracker.cameraselection.ImagePathManager;  // Local library

/**
 *
 * @author rahm-
 */


/*
This module gets the paths of the camera names images
Extends from ImagePathManager for absolute path "calculating" services
*/
public class CameraNamePathManager extends ImagePathManager{

    private final int amountCameras;
    private final String[] pathsCameraName;

    // Constructor that receives the amount of camera by parameter
    protected CameraNamePathManager(int amountCameras) {
        this.amountCameras = amountCameras;
        this.pathsCameraName = new String[amountCameras];
        fillPathsCameraName();  // Loads all the paths into the pathsCameraName array
    }

    /*
    @dev: fills the path of all the cameras into the array
          called in the constructor
    @param: None
    @returns: void
    @author: Andres Masis
     */
    private void fillPathsCameraName() {
        // Goes one by one to fill the whole array
        for (int i = 0; i < amountCameras; i++) {
            // The fisrt parameter of the function is the folder and the second the name of the file we want
            pathsCameraName[i] =  getSpecificImagePath("CameraNames", "cameraName"+(i + 1));
        }
    }

    /*
    @dev: gives the file path of the image of the name of a given camera, service to other modules
    @param: index of the camera to searh
    @returns: the String file path of the image of that camera name
    @author: Andres Masis
     */
    protected String getPath(int index) {
        return pathsCameraName[index];
    }

}
