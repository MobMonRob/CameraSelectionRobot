/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.dhbw.vicon.tracker.testspackage;

/**
 *
 * @author andres
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class RobotTest {

    public static void main(String[] args) {
        try {
            Robot robot = new Robot();

             /* Mouse click
            The amount of mask may change depending on the physical mouse*/
            
            // Simulate a left mouse click
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            // Simulate a right mouse click
            robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
            
            
            /*Mouse move
            Send the x, y coordinates*/
            
            // Simulate moving the mouse
            robot.mouseMove(50,50);

            
            /*Mouse wheel scrolling
            Negative values scroll up
            Positive values scroll down*/
            
            // Simulate scrolling up
            robot.mouseWheel(-3);
            // Simulate scrolling down
            robot.mouseWheel(3);
            
            
            /* Key press
            Use VK_CAPITAL for capital letters
            Combinations of keys is possible. e.g. Ctrl+C
            May need to check for virtual keys*/
            
            // Simulate pressing the "A" key
            robot.keyPress(KeyEvent.VK_A);
            robot.keyRelease(KeyEvent.VK_A);
            // Simulate pressing the Enter key
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            

            // Delays are useful for syncronization and to give time to the human user
            robot.delay(1000);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
