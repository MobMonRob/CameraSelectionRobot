/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package winiumTesting;

import io.appium.java_client.windows.WindowsDriver;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author rahm-
 */
public class MyFirstTestCase {
    static WindowsDriver driver;

    public static void main(String[] args) throws MalformedURLException, InterruptedException{
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Microsoft.WindowsMaps_8wekyb3d8bbwe!App");
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("platfromVersion", "1.0");
        
        driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
        System.out.println(driver.getSessionId());
        search("India");
        search("England");
        driver.findElementByAccessibilityId("Close").click();
    }
    
    public static void search(String name) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElementByAccessibilityId("TextBox").sendKeys(name);
        Thread.sleep(1000);
        driver.findElementByName("Search").sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        driver.findElementByAccessibilityId("MinimizeButton").click();
        Thread.sleep(1000);
        driver.findElementByAccessibilityId("CloseButton").click();
        Thread.sleep(1000);
    }
}
