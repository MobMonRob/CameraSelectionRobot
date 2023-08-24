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
        DesiredCapabilities appCapabilities = new DesiredCapabilities();
        appCapabilities.setCapability("app", "Root");
        
        WindowsDriver desktopSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), appCapabilities);
        

        // Use the session to control the desktop
        desktopSession.getKeyboard().sendKeys(Keys.COMMAND + "a" + Keys.COMMAND);
        desktopSession.findElementByName("Calculator - 1 running window").click();
    }
}