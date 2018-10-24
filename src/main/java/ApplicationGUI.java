import Throwables.TimerAlreadyStartedException;
import Throwables.TimerIncompleteException;
import Throwables.TimerNotStartedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ApplicationGUI implements ActionListener {

    private JFrame window = new JFrame("Screen Time Detector");
    private JPanel panelMain = new JPanel();

    private JButton startButton = new JButton("Start Tracking");
    private JButton stopButton = new JButton("Stop Tracking");

    private JPanel panel = new JPanel();

    private JLabel on = new JLabel("<html><h1><font color='green'>ON<h1></font></html>");
    private JLabel off = new JLabel("<html><h1><font color='red'>OFF<h1></font></html>");
    private JPanel topBar = new JPanel();

    private void init() {

        JLabel title = new JLabel("<html><h1>Screen Time Detector - </h1></html>");
        off = new JLabel();
        JPanel panelButtons = new JPanel();

        off = new JLabel("<html><h1><font color='red'>OFF<h1></font></html>");
        topBar.add(title);
        topBar.add(off);

        window.setSize(1000, 800);
        panelMain.setLayout(new BorderLayout());
        window.add(panelMain);

        window.add(topBar, BorderLayout.NORTH);

        panelButtons.add(startButton);
        panelButtons.add(stopButton);

        //Scrollbar window when stopped
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);
        window.add(scrollPane, BorderLayout.CENTER);

        window.add(panelButtons, BorderLayout.SOUTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);

        window.setVisible(true);

    }

    private Tracking track = new Tracking();

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            new Thread() {
                public void run() {
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
            }.start();
            topBar.remove(off);
            on = new JLabel("<html><h1><font color='green'>ON<h1></font></html>");
            topBar.add(on);
            window.repaint();
            window.setVisible(true);
        }
        if(e.getSource() == stopButton){
            try {
                track.totalScreenTimeStop();
                track.stopDetection();

                ArrayList<TimePair> tempList = track.getPairs();

                for (TimePair t: tempList) {
                    panel.add(new JLabel(t.toString()));
                }

                JLabel j = new JLabel("Total Time: " + String.valueOf(track.totalTime()) + " seconds");
                window.add(j, BorderLayout.WEST);
                topBar.remove(on);
                topBar.add(off);
                window.repaint();
                window.setVisible(true);

            } catch (TimerNotStartedException e1) {
                e1.printStackTrace();
            } catch (TimerIncompleteException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        ApplicationGUI obj = new ApplicationGUI();

        obj.init();
    }
}