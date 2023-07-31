package de.dhbw.vicon.tracker.testspackage;

import org.sikuli.script.*;

public class AutomationTest {
    
    private final int amountCameras;
    private final String[] pathsCameraName;
    
    public AutomationTest(){
        this.amountCameras = 13;
        this.pathsCameraName = new String[amountCameras];
        fillPathsCameraName();
    }
    
    private void fillPathsCameraName(){
        String basePath = "src/main/resources/Actions/CameraNames/";
        for (int i = 0; i < amountCameras; i++){
            pathsCameraName[i] = basePath+"cameraName"+(i+1)+".PNG";      
        }
    }

    public static void main(String args[]) {

    }
}
