/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package winiumTesting;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

/**
 *
 * @author rahm-
 */
public class MyFirstTestCase {



    public static void main(String[] args) throws AWTException, IOException, MalformedURLException, InterruptedException {
        NotepadTest n = new NotepadTest();
        System.out.println(n.isAppRunning("netbeans64"));
        // Tracker.exe
        // WinAppDriver.exe
    }

}
