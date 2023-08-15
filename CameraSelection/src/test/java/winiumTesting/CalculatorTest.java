/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package winiumTesting;

import io.appium.java_client.windows.WindowsDriver;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author rahm-
 */
public class CalculatorTest {

    public static void main(String[] args) throws MalformedURLException {
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
    }

}
