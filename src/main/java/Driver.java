import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.StdCallLibrary;

public class Driver {
    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class);
        HWND GetForegroundWindow();
        int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);
    }

    public static void main(String[] args) {
        byte[] windowText = new byte[512];

        PointerType hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
        System.out.println(Native.toString(windowText));
    }
}
