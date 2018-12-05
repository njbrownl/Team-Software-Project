import Throwables.TimerAlreadyStartedException;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinUser.WINDOWINFO;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.win32.StdCallLibrary;

import java.util.ArrayList;

class Driver {

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class);
        boolean EnumWindows(WinUser.WNDENUMPROC wndenumproc, int listParam);
        boolean IsWindowVisible(PointerType hWnd);
        int GetWindowInfo(PointerType hWnd, WINDOWINFO lpwndpl);
        int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
        boolean GetWindowRect(PointerType hWnd, RECT lpRect);
    }

    private ArrayList<WindowType> windowList = new ArrayList<WindowType>();
    private ArrayList<String> titlesList = new ArrayList<String>();

    private boolean add = true;

    ArrayList<WindowType> newGetTitle() {
        User32.INSTANCE.EnumWindows(new WinUser.WNDENUMPROC() {
            public boolean callback(HWND hwnd, Pointer pointer) {

                RECT rect = new RECT();
                WINDOWINFO info = new WINDOWINFO();
                User32.INSTANCE.GetWindowInfo(hwnd, info);
                User32.INSTANCE.GetWindowRect(hwnd, rect);

                if (User32.INSTANCE.IsWindowVisible(hwnd) && rect.left > -32000) {
                    byte[] windowText = new byte[512];
                    User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
                    String title = Native.toString(windowText);

                    for (WindowType windowType : windowList) {
                        RECT currentRectangle = windowType.getRectangle();
                        if (currentRectangle.bottom >= rect.bottom && currentRectangle.left <= rect.left && currentRectangle.right >= rect.right && currentRectangle.top <= rect.top) {
                            add = false;
                        }
                    }

                    char[] titleCharacters = title.toCharArray();
                    int hyphenCount = 0;
                    ArrayList<Integer> hyphenLocations = new ArrayList<Integer>();

                    for (int i = 0; i < titleCharacters.length; i++) {
                        if (titleCharacters[i] == '-') {
                            hyphenCount++;
                            hyphenLocations.add(i);
                        }
                    }

                    if (hyphenCount >= 2) {
                        title = title.substring(hyphenLocations.get(hyphenLocations.size() - 2) + 1);
                    }

                    if (!title.equals("") && add && !(titlesList.contains(title))) {
                        WindowType temp = new WindowType(hwnd, title, new Timer(), rect);
                        try {
                            temp.getTimer().setStartTime();
                        } catch (TimerAlreadyStartedException e) {
                            e.printStackTrace();
                        }
                        windowList.add(temp);
                        titlesList.add(title);
                    }
                }
                return true;
            }
        }, 0);
        return windowList;
    }

}