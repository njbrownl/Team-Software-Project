import com.sun.jna.platform.win32.WinDef;

class WindowType {

    private String title;
    private Timer timer;
    private WinDef.RECT rectangle;

    WindowType(WinDef.HWND hWnd, String s, Timer t, WinDef.RECT r) {
        WinDef.HWND hwnd = hWnd;
        title = s;
        timer = t;
        rectangle = r;
    }

    String getTitle() {
        return this.title;
    }

    Timer getTimer() {
        return timer;
    }

    WinDef.RECT getRectangle() {
        return this.rectangle;
    }
}
