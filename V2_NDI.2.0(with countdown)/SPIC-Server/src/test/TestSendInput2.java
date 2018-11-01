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
import com.sun.jna.Platform;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.LastErrorException;
import com.sun.jna.platform.win32.WinUser.*;
import com.sun.jna.platform.win32.WinDef.*;
import com.sun.jna.platform.win32.*;
import com.sun.jna.win32.*;

public class TestSendInput2 {

    public interface MyUser32 extends Library {

        public static final MyUser32 INSTANCE = (MyUser32) Native.loadLibrary("user32", MyUser32.class, W32APIOptions.DEFAULT_OPTIONS);

        public abstract int SendInput(int nInputs, WinUser.INPUT[] pInputs, int cbSize);

        public abstract int GetMessage(MSG lpMsg, HWND hWnd, int wMsgFilterMin, int wMsgFilterMax);

        public abstract boolean TranslateMessage(MSG lpMsg);

        public abstract LRESULT DispatchMessage(MSG lpMsg);

        public abstract int VkKeyScan(char a);

    }//interface

    static final MyUser32 user32 = MyUser32.INSTANCE;

    boolean quit = false;

    final String str;

    public TestSendInput2(String str) {

        this.str = str;
        System.out.println("Constr");

        int size = 2 * str.length();
        final WinUser.INPUT[] in = (WinUser.INPUT[]) new WinUser.INPUT().toArray(size);

        for (int i = 0; i < size; i++) {

            in[i].type = new WinDef.DWORD(WinUser.INPUT.INPUT_KEYBOARD);
            in[i].input = new WinUser.INPUT.INPUT_UNION();

            in[i].input.setType(KEYBDINPUT.class);

        }

        System.out.println("will send string: " + str);
        int pos = 0;
        for (int i = 0; i < str.length(); i++) {
            int code = user32.VkKeyScan(str.charAt(i));
            System.out.println(code);
            in[pos].input.ki.wVk = new WinDef.DWORD(code).getLow();
            in[pos].input.ki.dwFlags = new WinDef.DWORD(0);
            in[pos + 1].input.ki.wVk = new WinDef.DWORD(code).getLow();
            in[pos + 1].input.ki.dwFlags = new WinDef.DWORD(WinUser.KEYBDINPUT.KEYEVENTF_KEYUP);
            pos += 2;

        }//for
        // int out = user32.SendInput(size, in,  in[0].size());

        WorkerThread w = new WorkerThread(in);
        w.start();

         //System.out.println("out: " + out);
        //    if (out == 0) {
        //       throw new LastErrorException(Native.getLastError());
        //     }
        quit = true;

    }//constr

    class WorkerThread extends Thread {

        WinUser.INPUT[] in;

        //int size;

        public WorkerThread(WinUser.INPUT[] in) {
            this.in = in;
            //this.size = size;
            System.out.println("in.size: " + in.length);
            setDaemon(false);
        }

        public void run() {
            System.out.println("click in the notepad now");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
            int out = user32.SendInput(in.length, in, in[0].size());

            if (out == 0) {
                throw new LastErrorException(Native.getLastError());
            }

        }//run
    }//class

    public static void main(String[] args) {
        new TestSendInput2("Hello World");

        System.out.println("bye from main");
    }
}//class

