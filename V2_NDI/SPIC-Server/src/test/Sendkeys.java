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
import java.io.InputStream;
import java.io.OutputStream;
import java.awt.AWTException;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.HWND;
import static com.sun.jna.platform.win32.WinUser.WM_KEYDOWN;
import com.sun.jna.win32.W32APIOptions;

public class Sendkeys {

    String line;
    OutputStream stdin = null;
    InputStream stderr = null;
    InputStream stdout = null;

    public void sendkeys(String text) throws InterruptedException, AWTException {
        try {
            Process process = Runtime.getRuntime().exec("notepad");
           
            User32 user32 = User32.instance;
            HWND hWnd = user32.FindWindow(null, "Untitled - Notepad");

            boolean s = user32.PostMessage(hWnd, WM_KEYDOWN , 0x74, 0);
            System.out.println(s);

        } catch (java.io.IOException exc) {
            System.out.println("Error!");
        }

    }

    public static void main(String[] args) throws InterruptedException, AWTException {
        Sendkeys s = new Sendkeys();
        s.sendkeys("Hello world!");

    }

    public interface User32 extends W32APIOptions {
        User32 instance = (User32) Native.loadLibrary("user32", User32.class, DEFAULT_OPTIONS);
        boolean ShowWindow(HWND hWnd, int nCmdShow);
        boolean PostMessage(HWND hWnd, int Msg, int wParam, int lParam);
        HWND FindWindow(String winClass, String title);       
    }
}
