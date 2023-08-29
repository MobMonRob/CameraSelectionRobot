/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package winiumTesting;

import io.appium.java_client.windows.WindowsDriver;
import io.appium.java_client.windows.WindowsElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.Keys;

import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author rahm-
 */
public class NotepadTest {

    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
        new ProcessBuilder("taskkill", "/F", "/IM", "WinAppDriver.exe").start(); // Shuts down the WinApp server
    }
}