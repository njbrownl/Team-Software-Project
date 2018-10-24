import Throwables.TimerAlreadyStartedException;
import Throwables.TimerIncompleteException;
import Throwables.TimerNotStartedException;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ApplicationGUI implements ActionListener {

    private JFrame window = new JFrame("Screen Time Detector");
    private JPanel panelMain = new JPanel();

    private JButton startButton = new JButton("Start Tracking");
    private JButton stopButton = new JButton("Stop Tracking");

    private void init() {

        JLabel title = new JLabel("<html><h1>Screen Time Detector</h1></html>");

        JPanel panelButtons = new JPanel();


        window.setSize(1000, 800);
        panelMain.setLayout(new BorderLayout());
        window.add(panelMain);

        window.add(title, BorderLayout.NORTH);

        panelButtons.add(startButton);
        panelButtons.add(stopButton);

        window.add(panelButtons, BorderLayout.SOUTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);

        window.setVisible(true);

    }

    private Tracking track = new Tracking();

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startButton) {
            try {
                track.totalScreenTimeStart();
                track.detectScreenChange();
            } catch (TimerAlreadyStartedException e1) {
                e1.printStackTrace();
            } catch (TimerNotStartedException e1) {
                e1.printStackTrace();
            } catch (TimerIncompleteException e1) {
                e1.printStackTrace();
            }
        }
        if(e.getSource() == stopButton){
            try {
                track.totalScreenTimeStop();
                track.stopDetection();

                ArrayList<TimePair> tempList = track.getPairs();


                    JLabel j = new JLabel("Total Time: " + String.valueOf(track.totalTime()) + " seconds");
                    window.add(j, BorderLayout.WEST);
                    window.repaint();
                    window.setVisible(true);

            } catch (TimerNotStartedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        ApplicationGUI obj = new ApplicationGUI();

        obj.init();
    }
}