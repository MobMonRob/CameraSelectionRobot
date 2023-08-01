/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection;

/**
 *
 * @author rahm-
 */

import org.sikuli.script.Screen;

/*
Many different classes and functions use the SikuliX Screen
However, it is important to always use the same one
To make sure there is just one unique instance in all the code, the Singleton pattern is ideal
*/
public class ScreenSingleton {

    private static Screen screen;

    private ScreenSingleton() {
    }

    public static Screen getScreen() {
        if (screen == null) {
            screen = new Screen();
        }
        return screen;
    }
}
