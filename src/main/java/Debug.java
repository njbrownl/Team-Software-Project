import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Debug extends JFrame{

    private Driver driver = new Driver();
    private ArrayList<WindowType> windowTypes = driver.newGetTitle();

    Debug() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) screenSize.getWidth(), (int) screenSize.getWidth());
        setTitle("*DEBUG* Screen Time Detector");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        for (WindowType windowType : windowTypes) {
            int height = windowType.getRectangle().toRectangle().height;
            int width = windowType.getRectangle().toRectangle().width;
            int x = windowType.getRectangle().toRectangle().x;
            int y = windowType.getRectangle().toRectangle().y;
            g.drawRect(x, y, width, height);
            g.drawString(windowType.getTitle(), x + (width / 2), y + (height / 2));
        }
    }
}
