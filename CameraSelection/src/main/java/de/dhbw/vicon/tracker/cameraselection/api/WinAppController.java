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
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.openqa.selenium.NoSuchElementException;
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
        openApps();
        robot = new Robot();
        cameraNames = new String[]{"#1 (Vero v2.2)",
            "#2 (Vero v2.2)",
            "#3 (Vero v2.2)",
            "#4 (Vero v2.2)",
            "#5 (Vero v2.2)",
            "#6 (Vero v2.2)",
            "#7 (Vero v2.2)",
            "#8 (Vero v2.2)",
            "#9 (Vero v2.2)",
            "#10 (Vero v2.2)",
            "#11 (Vero v2.2)",
            "#12 (Vero v2.2)",
            "#13 (Vero v2.2)"};
    }

    /*
    @dev: This method opens the apps (WinAppDriver server and Vicon Tracker so far)
          It is recommended to closePrevious instances of the same app, before starting it to avoid interferences 
          Always make sure to start Vicon at the last
          To avoid any other third-party GUI to be in front and interfere 
    @param: none
    @return: void
    @author: Anddres Masis
     */
    private void openApps() throws IOException, MalformedURLException, InterruptedException {
        // WinAppDriver
        closePreviousInstances("WinAppDriver.exe");
        initializeWinAppDriver();

        // Vicon Tracker
        // from the external programs, Vicon Tracker should always be started last to make sure its GUI will be in front with no interferences
        closePreviousInstances("Tracker.exe");
        openViconTracker();
    }

    /*
    @dev: This method opens the Vicon Tracker app 
    @param: none
    @return: void
    @author: Anddres Masis
     */
    private void openViconTracker() throws IOException {
        // Creates a desktop instance to open automatically some programs
        Desktop desktop = Desktop.getDesktop();

        // Starts the Vicon Tracker
        desktop.open(new File("C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\Vicon\\Tracker 3.10.0\\Vicon Tracker 3.10.0 x64.lnk"));
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
        Thread.sleep(800);  // Gives some time for the server to start

        // Sets the capabilities needed for the driver
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "Root");

        // Starts the WinApp driver instance
        // URL is deprecated but must be used because it is the one supported by WindowsDriver
        winDriver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
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

    /*
    @dev: This method tells if a given app is running in this machine
    @param: processName (String)
    @return: true if the process is running
             false if the process is not running (was not found)
    @author: Andres
     */
    private boolean isAppRunning(String processName) {
        boolean isRunning = false;  // Flag variable that will be returned
        try {
            String line;
            Process p = new ProcessBuilder("tasklist.exe").start();  // Gets the list of processes
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // Checks process by process to see if it coincides with the processName parameter
            while ((line = input.readLine()) != null) {
                if (line.contains(processName)) {
                    // Found the process
                    isRunning = true;
                    break;
                }
            }
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isRunning;
    }

    /*
    @dev: This method closes a given app
    @param: processName (String)
    @return: void
    @author: Andres
     */
    private void closeApp(String processName) {
        try {
            ProcessBuilder pb = new ProcessBuilder("taskkill", "/f", "/im", processName);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    @dev: This method closes a given app in case it is running
    @param: processName (String)
    @return: void
    @author: Andres
     */
    private void closePreviousInstances(String processName) {
        if (isAppRunning(processName)) {
            closeApp(processName);
        }
    }

    /*
    @dev: it puts the subarea that contains the a given element in the correct position
          to make sure the element is visible
          It achieves that by going to the given area and scrolling all the way to the top
          Because the elements we are looking for, are usually at the top
    @param: xPath (String), necessary to find the element on screen
    @returns: void
    @author: Andres Masis
     */
    private void arrangeAreaByXPath(String xPath) throws InterruptedException {
        // Clicks on the Properties label (closest element)
        winDriver.findElementByXPath(xPath).click();

        // Moves the mouse based on the given offset so it is on the correct subarea
        robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x - 175, MouseInfo.getPointerInfo().getLocation().y + 20);

        // Scrolls all the way up (negative to scroll up)
        for (int i = 0; i < 4; i++) {
            robot.mouseWheel(-4);  // No more than 4, if you put more, it may get stuck
            Thread.sleep(25);  // Necessary delay to give chance for Vicon Tracker to react
        }
    }

    /*
    @dev: it puts the subarea that contains the checkbox in the correct position
          to make sure the checkbox is visible
          Relies on the method arrangeAreaByXPath()
    @param: None
    @returns: void
    @author: Andres Masis
     */
    private void arrangeCameraArea() throws InterruptedException {
        // Clicks on the Properties label (closest element)
        String xPath = "/Pane[@ClassName=\"#32769\"][@Name=\"Desktop 1\"]/Window[@Name=\"VICON TRACKER 3.10\"][@AutomationId=\"MainWindow\"]/Window[@ClassName=\"QDockWidget\"][@Name=\"RESOURCES\"]/Group[@ClassName=\"QWidget\"]/Group[@ClassName=\"VDataBrowser\"]/Custom[@ClassName=\"QSplitter\"]/Group[@ClassName=\"QTabWidget\"]/Custom[@ClassName=\"QStackedWidget\"]/Custom[@ClassName=\"QSplitter\"]/Custom[@ClassName=\"QStackedWidget\"]/Group[@ClassName=\"VDataBrowserSystem\"]/Group[@ClassName=\"VConfigurationWidget\"]/Button[@ClassName=\"QToolButton\"]";
        arrangeAreaByXPath(xPath);
    }

    /*
    @dev: This method makes sure that we are on the correct camera. It clicks on the screen the camera with the given index.
    @param: it receives by parameter the Index of the camera integral it should be between zero and 12 
    @return: void
    @author: Anddres Masis
     */
    protected void clickOnCamera(int cameraIndex) throws InterruptedException {
        // Makes sure everything is in the correct position on the screen
        arrangeCameraArea();

        // Looks for the camera on screen
        try {
            winDriver.findElementByName(cameraNames[cameraIndex]).click();
        } catch (NoSuchElementException e) {
            // The camera was not visible, must scroll down a little bit
            robot.mouseWheel(2);

            // Searches the camera again
            winDriver.findElementByName(cameraNames[cameraIndex]).click();
        }
    }

    /*
    @dev: This method clicks the enable/disable check box on the screen
    @param: it receives by parameter the specific name of the checkbox
            it should be a string of true or false (note there is no uppercase)
    @return: it returns true in case the element was found successfully
             it returns false in case the element was not found
    @author: Anddres Masis
     */
    protected boolean clickCheckbox(String value) throws InterruptedException {
        // Get the path of the true/false checkbox to find it on screen
        String xPath = "/Pane[@ClassName=\"#32769\"][@Name=\"Desktop 1\"]/Window[@Name=\"VICON TRACKER 3.10\"][@AutomationId=\"MainWindow\"]/Window[@ClassName=\"QDockWidget\"][@Name=\"RESOURCES\"]/Group[@ClassName=\"QWidget\"]/Group[@ClassName=\"VDataBrowser\"]/Custom[@ClassName=\"QSplitter\"]/Group[@ClassName=\"QTabWidget\"]/Custom[@ClassName=\"QStackedWidget\"]/Custom[@ClassName=\"QSplitter\"]/Group[@ClassName=\"QWidget\"]/Table[@ClassName=\"VParamListView\"]/DataItem[@Name=\"" + value + "\"]";

        // It looks for the element on the screen
        boolean found = false;
        try {
            winDriver.findElementByXPath(xPath).click();  // Goes to the element
            // Adjust the mouse location to the actual checkbox
            robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x - 85, MouseInfo.getPointerInfo().getLocation().y - 4);
            // Clicks on the checkbox
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

            // Informs the action was completed successfully
            found = true;
        } catch (NoSuchElementException e) {
            // The element was not found
            found = false;
        } finally {
            // Moves the mouse a to the video screen just that it is not anymore over the checkbox to avoid enable/disable it by an accidental click
            robot.mouseMove(MouseInfo.getPointerInfo().getLocation().x + 500, MouseInfo.getPointerInfo().getLocation().y);
            return found;
        }
    }
    
    /*
    @dev: This method get for each one of all the cameras if it is enabled or disabled
         It gets it by the value of the enabled checkbox in the Properties panel
         It works with the disabled XPath, the enabled XPath does not work properly
    @param: None
    @returns: Array of booleans, for each postion
              True if the camera of that index was enabled
              False if the camera of that index was disabled
    */
    protected boolean[] getDisabledCameras() throws InterruptedException {
        // Array that will be returned
        boolean[] statesArray = new boolean[13];
        // Get the path of the checkbox, ONLY APPLIES FOR DISABLED
        String disabledPath = "/Pane[@ClassName=\"#32769\"][@Name=\"Desktop 1\"]/Window[@Name=\"VICON TRACKER 3.10\"][@AutomationId=\"MainWindow\"]/Window[@ClassName=\"QDockWidget\"][@Name=\"RESOURCES\"]/Group[@ClassName=\"QWidget\"]/Group[@ClassName=\"VDataBrowser\"]/Custom[@ClassName=\"QSplitter\"]/Group[@ClassName=\"QTabWidget\"]/Custom[@ClassName=\"QStackedWidget\"]/Custom[@ClassName=\"QSplitter\"]/Group[@ClassName=\"QWidget\"]/Table[@ClassName=\"VParamListView\"]/DataItem[@Name=\"false\"]";

        // Arranges the screen correctly to make sure the cameras will be found
        arrangeCameraArea();

        // Goes camera by camera checking its enabled/disabled state
        for (int i = 0; i < 7; i++) {
            // Selects the camera on the Vicon Tracker GUI
             winDriver.findElementByName(cameraNames[i]).click();

            try {
                // Goes to the element
                winDriver.findElementByXPath(disabledPath).click();
                // The enabled element was found successfully
                statesArray[i] = true;

            } catch (NoSuchElementException e) {
                // The enabled element was not found, therefore, it is disabled
                statesArray[i] = false;
            }
        }

        // The rest of the cameras are not visible
        winDriver.findElementByName(cameraNames[0]).click();  // Moves the mouse from the Properties panel to the cameras panel
        robot.mouseWheel(2);  // Scrolls down

        // Continues the process with the rest of the cameras
        for (int i = 7; i < 13; i++) {
            // Selects the camera on the Vicon Tracker GUI
            winDriver.findElementByName(cameraNames[i]).click();
            
            try {
                // Goes to the element
                winDriver.findElementByXPath(disabledPath).click();
                // The enabled element was found successfully
                statesArray[i] = true;

            } catch (NoSuchElementException e) {
                // The enabled element was not found, therefore, it is deisabled
                statesArray[i] = false;
            }
        }
        
        return statesArray;
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
        closeApp("Tracker.exe");  // Closes the Vicon Tracker
        winDriver.quit(); // Stops the WinApp driver     
        closeApp("WinAppDriver.exe"); // Shuts down the WinApp server
    }

    /*
    Auxiliary methods
    This methods work properly, but are not the best approach
    These depends on the mouse and GUI detection which are less reliable
    Although these are less precise, it is recommended to not delete this code
    They have proved to work and these can be useful as a backup
    
    @dev: This method opens the Vicon Tracker app 
    @param: none
    @return: void
    @author: Anddres Masis
    
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

    
    @dev: This method closes all the app, making sure the running environments shuts down. 
          It shuts down the Vicon tracker, the WinApp driver and its server 
          Note that it throws an excpetion, so it also must be added when calling this method
    @param: none
    @return: void
    @author: Anddres Masis
    
    protected void endSession() throws IOException {
        winDriver.findElementByName("Schließen").click();  // Closes the Vicon Tracker
        winDriver.quit(); // Stops the WinApp driver     
        new ProcessBuilder("taskkill", "/F", "/IM", "WinAppDriver.exe").start(); // Shuts down the WinApp server
    }
    
    @dev: it puts the subarea that contains the checkbox in the correct position
          to make sure the checkbox is visible
          Relies on the method arrangeAreaByXPath()
          Seems to be not necessary because the area of the checkbox
          always starts on the correct position when a camera is clicked
          Still, it is good to keep it in comments just in case
    @param: None
    @returns: void
    @author: Andres Masis
    private void arrangeCheckboxArea() throws InterruptedException {
        // Clicks on the Properties label (closest element)
        String xPath = "/Pane[@ClassName=\"#32769\"][@Name=\"Desktop 1\"]/Window[@Name=\"VICON TRACKER 3.10\"][@AutomationId=\"MainWindow\"]/Window[@ClassName=\"QDockWidget\"][@Name=\"RESOURCES\"]/Group[@ClassName=\"QWidget\"]/Group[@ClassName=\"VDataBrowser\"]/Custom[@ClassName=\"QSplitter\"]/Group[@ClassName=\"QTabWidget\"]/Custom[@ClassName=\"QStackedWidget\"]/Custom[@ClassName=\"QSplitter\"]/Group[@ClassName=\"QWidget\"]/Group[@ClassName=\"VParamListHeader\"]";
        arrangeAreaByXPath(xPath);
    }
     */
}
