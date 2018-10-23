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

    String getTitle() {
        byte[] windowText = new byte[512];

        PointerType hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
        return Native.toString(windowText);
    }

    public static void main(String[] args) {
        Driver sys = new Driver();
        System.out.println(sys.getTitle());
    }
}
