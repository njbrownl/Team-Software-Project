import Throwables.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ApplicationGUI implements ActionListener {

    private JFrame window = new JFrame("Screen Time Detector");
    private JPanel panelMain = new JPanel();

    JButton startButton = new JButton("Start Tracking");
    JButton stopButton = new JButton("Stop Tracking");
    private JButton debugButton = new JButton("Debug");

    private JPanel panel = new JPanel();

    private JLabel on = new JLabel("<html><h1><font color='green'>ON<h1></font></html>");
    private JLabel off = new JLabel("<html><h1><font color='red'>OFF<h1></font></html>");
    private JPanel topBar = new JPanel();

    void init() {

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
        panelButtons.add(debugButton);

        //Scrollbar window when stopped
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);
        window.add(scrollPane, BorderLayout.CENTER);

        window.add(panelButtons, BorderLayout.SOUTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        debugButton.addActionListener(this);

        window.setVisible(true);

    }

    private MultiTracking newTrack = new MultiTracking();
    private JLabel totalTimeLabel;

    double totalTime;
    ArrayList<TimePair> tempList;

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            new Thread() {
                public void run() {
                    try {
                        Database db = new Database();
                        db.clearSessionData();
                        newTrack = new MultiTracking();
                        newTrack.startDetection();
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
            if (totalTimeLabel != null && !totalTimeLabel.getText().equals("Total Time: ")) {
                totalTimeLabel.setText("Total Time: ");
            }

            window.repaint();
            window.setVisible(true);
        }
        if(e.getSource() == stopButton){
            try {
                newTrack.stopDetection();

                Database db = new Database();
                tempList = newTrack.getMasterList();

                db.insertSessionData(tempList);
                db.insertTotalData(tempList);

                for (TimePair t: tempList) {
                    panel.add(new JLabel(t.toString()));
                }

                totalTime = newTrack.getTotalTimeRunSession();

                //TODO Remove temporary chart test
                SessionTimePieChart sessionPieChart = new SessionTimePieChart();
                window.add(sessionPieChart.createPanel());
                TotalTimePieChart totalPieChart = new TotalTimePieChart();
                window.add(totalPieChart.createPanel());
                //End temporary chart test

                totalTimeLabel = new JLabel("Total Time: " + String.valueOf(totalTime) + " seconds");
                window.add(totalTimeLabel, BorderLayout.WEST);
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
        if (e.getSource() == debugButton) {
            Debug debug = new Debug();
            debug.repaint();
        }
    }

    public static void main(String[] args){
        ApplicationGUI obj = new ApplicationGUI();

        obj.init();
    }
}