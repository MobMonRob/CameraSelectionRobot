/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection.cameraselector;

/**
 *
 * @author rahm-
 */
public class CameraNamePathManager {

    private final int amountCameras;
    private final String[] pathsCameraName;

    // Constructor that receives the amount of camera by parameter
    protected CameraNamePathManager(int amountCameras) {
        this.amountCameras = amountCameras;
        this.pathsCameraName = new String[amountCameras];
        fillPathsCameraName();
    }

    /*
    @dev: fills the path of all the cameras into the array, used in the constructor
    @param: None
    @returns: void
    @author: Andres Masis
     */
    private void fillPathsCameraName() {
        String basePath = "src/main/resources/Actions/CameraNames/";
        for (int i = 0; i < amountCameras; i++) {
            pathsCameraName[i] = basePath + "cameraName" + (i + 1) + ".PNG";
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
