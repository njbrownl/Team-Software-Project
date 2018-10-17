import javax.swing.*;
import java.awt.*;

public class ApplicationGUI {

    private void init() {
        JFrame window = new JFrame("Screen Time Detector");
        JPanel panel = new JPanel();
        window.setSize(1000, 800);
        panel.setLayout(new BorderLayout());
        window.add(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
    }


    public static void main(String[] args){
        ApplicationGUI obj = new ApplicationGUI();

        obj.init();
    }

}




