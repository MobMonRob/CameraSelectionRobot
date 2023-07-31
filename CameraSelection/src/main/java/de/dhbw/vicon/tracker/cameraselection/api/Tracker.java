package de.dhbw.vicon.tracker.cameraselection.api;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Tracker {
    
    public int getCameraCount(){
        // test
        return 13;
    }
    
    public void enableViconCamera(int index) throws IllegalArgumentException{}
    public void disableViconCamera(int index) throws IllegalArgumentException{}
}
