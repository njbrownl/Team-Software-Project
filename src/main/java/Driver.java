import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.Structure;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;

import java.util.ArrayList;

public class Driver {

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class);

        boolean EnumWindows(WinUser.WNDENUMPROC wndenumproc, int listParam);
        boolean IsWindowVisible(PointerType hWnd);
        int GetWindowInfo(PointerType hWnd, WINDOWINFO lpwndpl);
        int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
        boolean GetWindowRect(PointerType hWnd, RECT lpRect);
    }

    String getTitle() {
        byte[] windowText = new byte[512];

        //PointerType hwnd = User32.INSTANCE.GetForegroundWindow();
        //User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
        return Native.toString(windowText);
    }

    private ArrayList<WindowType> windowList = new ArrayList<WindowType>();

    private boolean add = true;

    private void newGetTitle() {
        User32.INSTANCE.EnumWindows(new WinUser.WNDENUMPROC() {
            public boolean callback(HWND hwnd, Pointer pointer) {

                WINDOWINFO info = new WINDOWINFO();
                User32.INSTANCE.GetWindowInfo(hwnd, info);

                RECT rect = new RECT();

                //System.out.println(rect.left);
                User32.INSTANCE.GetWindowRect(hwnd, rect);

                if (User32.INSTANCE.IsWindowVisible(hwnd) && rect.left > -32000 && rect.bottom < 1080) {
                    byte[] windowText = new byte[512];
                    User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
                    String title = Native.toString(windowText);

                    for (WindowType windowType : windowList) {
                        RECT currentRectangle = windowType.getRectangle();
                        //System.out.println(windowType.getTitle() + " " + title + " " + currentRectangle.left + " " + rect.left);
                        if (currentRectangle.bottom >= rect.bottom && currentRectangle.left <= rect.left && currentRectangle.right >= rect.right && currentRectangle.top <= rect.top) {
                            add = false;
                        }
                    }

                    if (!title.equals("") && add) {
                        windowList.add(new WindowType(hwnd, title, new Timer(), rect));
                        //System.out.println(title + " " + rect.right + " " + rect.left + " " + rect.top + " " + rect.bottom);
                    }
                }
                return true;
            }
        }, 0);
    }


    public static class Rectangle extends Structure {
        public int left, top, right, bottom;
    }

    public static void main(String[] args) {
        Driver sys = new Driver();
        sys.newGetTitle();
        for (WindowType windowType : sys.windowList) {
            System.out.println(windowType.getTitle());
        }
    }
}
