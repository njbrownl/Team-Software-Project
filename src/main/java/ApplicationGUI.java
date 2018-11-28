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
    //private JButton debugButton = new JButton("Debug");

    JButton sessionTimeCButton = new JButton("Session Time Chart");
    JButton totalTimeCButton = new JButton("Total Time Chart");

  //  private JPanel panel = new JPanel();

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

        panelMain.setLayout(new BorderLayout());
        window.add(panelMain);
        window.add(topBar, BorderLayout.NORTH);

        panelButtons.add(startButton);
        panelButtons.add(stopButton);
        //panelButtons.add(debugButton);
        panelButtons.add(sessionTimeCButton);
        panelButtons.add(totalTimeCButton);

        window.add(panelButtons, BorderLayout.SOUTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        sessionTimeCButton.addActionListener(this);
        totalTimeCButton.addActionListener(this);

       // debugButton.addActionListener(this);

        window.pack();
        window.setVisible(true);


    }

    private MultiTracking newTrack = new MultiTracking();
  //  private JLabel totalTimeLabel;

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
//            if (totalTimeLabel != null && !totalTimeLabel.getText().equals("Total Time: ")) {
//                totalTimeLabel.setText("Total Time: ");
//            }

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

                totalTime = newTrack.getTotalTimeRunSession();

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
//        if (e.getSource() == debugButton) {
//            Debug debug = new Debug();
//            debug.repaint();
//        }

        if(e.getSource() == sessionTimeCButton){
            JFrame chart = new JFrame("Session Time Chart");
            chart.setLayout(new BorderLayout());
            SessionTimePieChart sessionPieChart = new SessionTimePieChart();
            chart.add(sessionPieChart.createPanel());
            chart.setVisible(true);
            chart.pack();
            chart.toFront();
        }

        if(e.getSource() == totalTimeCButton) {
            JFrame chart = new JFrame("Total Time Chart");
            chart.setLayout(new BorderLayout());
            TotalTimePieChart totalPieChart = new TotalTimePieChart();
            chart.add(totalPieChart.createPanel());
            chart.setVisible(true);
            chart.pack();
            chart.toFront();
        }
    }

    public static void main(String[] args){
        ApplicationGUI obj = new ApplicationGUI();

        obj.init();
    }
}