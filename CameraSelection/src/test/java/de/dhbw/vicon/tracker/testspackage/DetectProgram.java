/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.testspackage;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author andre
 */
public class DetectProgram {

    public boolean isAppRunning(String processName) {
        boolean isRunning = false;
        try {
            Process process = Runtime.getRuntime().exec("tasklist");
            Scanner scanner = new Scanner(process.getInputStream());

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains(processName)) {
                    isRunning = true;
                    break;
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isRunning;
    }

    public void closeApp(String processName) {
        try {
            ProcessBuilder pb = new ProcessBuilder("taskkill", "/f", "/im", processName);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void closePreviousInstances(String processName){
        if(isAppRunning(processName)) {
            closeApp(processName);
        }
    }
}

