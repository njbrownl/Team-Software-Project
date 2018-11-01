import com.sun.jna.platform.win32.WinDef;

public class WindowType {

    private WinDef.HWND hwnd;
    private String title;
    private Timer timer;
    private WinDef.RECT rectangle;

    WindowType() {
        hwnd = null;
        title = null;
        timer = null;
        rectangle = null;
    }

    WindowType(WinDef.HWND hWnd, String s, Timer t, WinDef.RECT r) {
        hwnd = hWnd;
        title = s;
        timer = t;
        rectangle = r;
    }

    public void setHwnd(WinDef.HWND hwnd) {
        this.hwnd = hwnd;
    }

    public WinDef.HWND getHwnd() {
        return this.hwnd;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String getTitle() {
        return this.title;
    }

    public void setTimer(Timer t) {
        this.timer = t;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setRectangle(WinDef.RECT rectangle) {
        this.rectangle = rectangle;
    }

    WinDef.RECT getRectangle() {
        return this.rectangle;
    }
}
