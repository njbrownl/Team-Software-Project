import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplicationGUI implements ActionListener {


    JButton startButton = new JButton("Start Tracking");
    JButton stopButton = new JButton("Stop Tracking");

    Timer time = new Timer();

    private void init() {
        JFrame window = new JFrame("Screen Time Detector");
        JPanel panelMain = new JPanel();
        JLabel title = new JLabel("<html><h1>Screen Time Detector</h1></html>");

        //JLabel timeSpent = new JLabel("" + time.totalWatchTime());

        JPanel panelButtons = new JPanel();


        window.setSize(1000, 800);
        panelMain.setLayout(new BorderLayout());
        window.add(panelMain);

        window.add(title, BorderLayout.PAGE_START);

        window.add(timeSpent, BorderLayout.CENTER);

        panelButtons.add(startButton);
        panelButtons.add(stopButton);

        window.add(panelButtons, BorderLayout.SOUTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton.addActionListener(this);

        window.setVisible(true);

    }

    public static void main(String[] args){
        ApplicationGUI obj = new ApplicationGUI();

        obj.init();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton){
            time.startWatch();
        }
    }
}




