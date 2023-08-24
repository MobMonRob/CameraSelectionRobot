/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package winiumTesting;

import io.appium.java_client.windows.WindowsDriver;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author rahm-
 */
public class CalculatorTest {

    public static void main(String[] args) throws MalformedURLException, IOException, InterruptedException {
        String[] cameraNames = new String[]{"5", "6", "7", "8", "1", "2", "11", "3", "9", "10", "4", "13", "12"};
        
        for (int i = 0; i < 13; i++) {
            cameraNames[i] = "#" + (i + 1) + " " + cameraNames[i] + "(Vero v2.2)";
            System.out.println("\"" + cameraNames[i] + "\",");
        }
        
       /*Desktop desktop = Desktop.getDesktop();
        desktop.open(new File("C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe"));
        Thread.sleep(1000);
        
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Microsoft.WindowsCalculator_8wekyb3d8bbwe!App");
        WindowsDriver calSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
        
        calSession.findElementByName("One").click();
        calSession.findElementByName("Plus").click();
        calSession.findElementByName("Nine").click();
        calSession.findElementByName("Equals").click();
        
        String result = calSession.findElementByAccessibilityId("CalculatorResults").getText();
        System.out.println(result);
        calSession.quit();
        Runtime.getRuntime().exec("taskkill /F /IM WinAppDriver.exe");*/
    }

}
