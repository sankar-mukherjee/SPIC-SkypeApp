/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author SMuherejee
 */
import java.awt.Robot;
import java.awt.event.KeyEvent;
 
public class Sendkeys {
    
    public static void sendkeys(String text) {
       try {
           Robot robot = new Robot();
           String lol = text.toUpperCase();
           for(int i=0;i<lol.length();i++) {
               robot.keyPress(KeyEvent.VK_F5);
           }
       } catch(java.awt.AWTException exc) {
           System.out.println("error");
       }
    }
    
    public static void main(String[] args) {
    	try {
    		Runtime.getRuntime().exec("notepad");
    		Robot r = new Robot();
    		r.delay(1000);
    		sendkeys("Hello world!");
    	} catch(java.io.IOException exc) {
    		System.out.println("Error!");
    	} catch(java.awt.AWTException exc) {
    		System.out.println("Error!");
    	}
    }
}
