/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.cameraselection.api;

import io.appium.java_client.windows.WindowsDriver;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * PREREQUISITES TO USE WINAPP DRIVER
 *
 * Install WinApp Driver from:
 * https://github.com/Microsoft/WinAppDriver/releases More information of Driver
 * installation in the following video, at minute 8:30.
 * https://www.youtube.com/watch?v=jVjg2WOO6-8&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=1
 *
 * Add the Selenium and Appium dependencies in the Maven project (in this
 * project they should already be there) More information of Dependencies
 * installation in the following video, at minute 2:00.
 * https://www.youtube.com/watch?v=165imUZ0098&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=3
 *
 * Before running any WinAppDriver code, make sure that the driver "server" is
 * running A code to start it automatically is added, based on:
 * https://www.youtube.com/watch?v=HH_qKhqorU4&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=5
 *
 * The developer mode must be enabled in the computer To see how to enable
 * developer mode, check minute 9:55 of:
 * https://www.youtube.com/watch?v=jVjg2WOO6-8&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=1
 *
 * Sometimes, the WinApp Server fails to connect, so it may need to be started
 * again. It is extremely rare but the possibility cannot be discarded. There is
 * not a code in this project to manage that. You may need to start it manually,
 * take a look at minute 8:00.
 * https://www.youtube.com/watch?v=165imUZ0098&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=3
 *
 * An inspection tool can be very useful to get the names and automation id of
 * the elements of the GUI.
 *
 * Some tools are inspect.exe or UI Recorder. To install them, check minutes
 * 6:10(for Inspect.exe) and 11:00(for UI Recorder) of:
 * https://www.youtube.com/watch?v=IlkPsJiGUYA&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=2
 * Note that names change based on the language of the Windows in that computer
 *
 * For the capabilities you can send the absolute path of the .exe file Or the
 * id of the app. You can find the id with PowerShell. You can check it on
 * minute 3:00 of:
 * https://www.youtube.com/watch?v=IlkPsJiGUYA&list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe&index=2
 *
 * To learn more about these tools, check this playlist:
 * https://www.youtube.com/playlist?list=PLnxpMuIcxn1TG2Eupfj_16mDRVtYipKYe
 */
public class WinAppController {

    private String[] cameraNames;  // Hold the automation names of the cameras
    private Robot robot;  // To controll the mouse
    private WindowsDriver winDriver;  // To interact with the GUI

    // Constructor
    public WinAppController() throws AWTException, MalformedURLException, IOException, InterruptedException {
        cameraNames = new String[]{"#1 5 (Vero v2.2)",
            "#2 6 (Vero v2.2)",
            "#3 7 (Vero v2.2)",
            "#4 8 (Vero v2.2)",
            "#5 1 (Vero v2.2)",
            "#6 2 (Vero v2.2)",
            "#7 11 (Vero v2.2)",
            "#8 3 (Vero v2.2)",
            "#9 9 (Vero v2.2)",
            "#10 10 (Vero v2.2)",
            "#11 4 (Vero v2.2)",
            "#12 13 (Vero v2.2)",
            "#13 12 (Vero v2.2)"};
        robot = new Robot();
        initializeWinAppDriver();
        openViconTracker();
    }

    /*
    @dev: This method starts the server, and puts the capabilities into the driver and starts the driver 
          Note that it throws an excpetion, so it also must be added when calling this method
    @param: none
    @return: void
    @author: Anddres Masis
     */
    private void initializeWinAppDriver() throws MalformedURLException, IOException, InterruptedException {
        // Creates a desktop instance to open automatically some programs
        Desktop desktop = Desktop.getDesktop();

        // Starts the driver server
        desktop.open(new File("C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe"));
        Thread.sleep(1000);  // Gives some time for the server to start

        // Sets the capabilities needed for the driver
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Root");

        // Starts the WinApp driver instance
        // URL is deprecated but must be used because it is the one supported by WindowsDriver
        winDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
    }

    /*
    @dev: This method opens the Vicon Tracker app 
    @param: none
    @return: void
    @author: Anddres Masis
     */
    private void openViconTracker() {
        // We go to the Desktop with shortcut Win+D
        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyPress(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_D);
        robot.keyRelease(KeyEvent.VK_WINDOWS);

        // Clicks on the Vicon Shortcut
        WebElement element = winDriver.findElementByName("Vicon Tracker 3.10.0 x64");
        Actions action = new Actions(winDriver);
        action.doubleClick(element).perform();
    }

    /*
    @dev: This method tells if the Vicon Tracker is connected or not
          It looks for the CONNECTED label on screen
    @param: none
    @return: bool
             True if CONNECTED
             False if CONNECTED not founded
    @author: Anddres Masis
     */
    protected boolean isConnected() {
        try {
            // Looks for the CONNECTED label on the screen
            winDriver.findElementByName("CONNECETED");
            return true;
        } catch (NoSuchElementException e) {
            // The element was not found
            return false;
        }
    }

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

    public void closePreviousInstances(String processName) {
        if (isAppRunning(processName)) {
            closeApp(processName);
        }
    }

    /*
    @dev: This method closes all the app, making sure the running environments shuts down. 
          It shuts down the Vicon tracker, the WinApp driver and its server 
          Note that it throws an excpetion, so it also must be added when calling this method
    @param: none
    @return: void
    @author: Anddres Masis
     */
    protected void endSession() throws IOException {
        winDriver.findElementByName("Schließen").click();  // Closes the Vicon Tracker
        winDriver.quit(); // Stops the WinApp driver     
        new ProcessBuilder("taskkill", "/F", "/IM", "WinAppDriver.exe").start(); // Shuts down the WinApp server
    }

    /*
    @dev: This method makes sure that we are on the correct camera. It clicks on the screen the camera with the given index.
    @param: it receives by parameter the Index of the camera integral it should be between zero and 12 
    @return: it returns true in case the element was found successfully
             it returns false in case the element was not found
    @author: Anddres Masis
     */
    protected void clickOnCamera(int cameraIndex) {
        winDriver.findElementByName(cameraNames[cameraIndex]).click();
    }

    /*
    @dev: This method clicks the enable/disable check box on the screen
    @param: it receives by parameter the specific name of the checkbox
            it should be a string of true or false (note there is no uppercase)
    @return: it returns true in case the element was found successfully
             it returns false in case the element was not found
    @author: Anddres Masis
     */
    protected boolean clickCheckbox(String value) {
        // Get the path of the true/false checkbox to find it on screen
        String xPath = "/Pane[@ClassName=\"#32769\"][@Name=\"Desktop 1\"]/Window[@Name=\"VICON TRACKER 3.10\"][@AutomationId=\"MainWindow\"]/Window[@ClassName=\"QDockWidget\"][@Name=\"RESOURCES\"]/Group[@ClassName=\"QWidget\"]/Group[@ClassName=\"VDataBrowser\"]/Custom[@ClassName=\"QSplitter\"]/Group[@ClassName=\"QTabWidget\"]/Custom[@ClassName=\"QStackedWidget\"]/Custom[@ClassName=\"QSplitter\"]/Group[@ClassName=\"QWidget\"]/Table[@ClassName=\"VParamListView\"]/DataItem[@Name=\"" + value + "\"]";

        // It looks for the element on the screen
        try {
            winDriver.findElementByXPath(xPath).click();  // Goes to the element
            robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x - 85, MouseInfo.getPointerInfo().getLocation().y - 4);  // Adjust the mouse location to the actual checkbox
            // Clicks on the checkbox
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            return true;
        } catch (NoSuchElementException e) {
            // The element was not found
            return false;
        }
    }

}
