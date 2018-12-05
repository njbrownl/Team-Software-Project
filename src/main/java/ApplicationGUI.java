import Throwables.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ApplicationGUI implements ActionListener {

    private JFrame window = new JFrame("Screen Time Detector");
    private JPanel panelMain = new JPanel();

    JButton startButton = new JButton("Start Tracking");
    JButton stopButton = new JButton("Stop Tracking");

    private JButton sessionTimeCButton = new JButton("Session Time Chart");
    private JButton totalTimeCButton = new JButton("Total Time Chart");
    private JButton metricsButton = new JButton("Numeric Metrics");

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
        panelButtons.add(sessionTimeCButton);
        panelButtons.add(totalTimeCButton);
        panelButtons.add(metricsButton);

        window.add(panelButtons, BorderLayout.SOUTH);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);
        sessionTimeCButton.addActionListener(this);
        totalTimeCButton.addActionListener(this);
        metricsButton.addActionListener(this);

        stopButton.setEnabled(false);

        window.pack();
        window.setVisible(true);
    }

    private MultiTracking newTrack = new MultiTracking();

    double totalTime;
    ArrayList<TimePair> tempList;

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            topBar.add(on);
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
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

            Component[] list = topBar.getComponents();
            topBar.remove(off);

            if (list.length == 1) {
                on = new JLabel("<html><h1><font color='green'>ON<h1></font></html>");
                topBar.add(on);
            }
        }


            window.repaint();
            window.setVisible(true);

        if(e.getSource() == stopButton){
            try {
                newTrack.stopDetection();
                startButton.setEnabled(true);
                stopButton.setEnabled(false);

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

        if(e.getSource() == metricsButton) {
            JFrame metrics = new JFrame("Numeric Metrics");
            metrics.setLayout(new GridLayout(5, 1));

            JPanel top = new JPanel();
            JLabel title = new JLabel("<html><h1>Numeric Metrics</h1></html>");
            top.add(title);

            Database db = new Database();

            JLabel mostViewedSession = new JLabel("Most Viewed Application (Session): " + db.mostTimeSession().getName()+ " " + String.format("%.3f", db.mostTimeSession().getTime()) + " seconds");
            JLabel mostViewedTotal = new JLabel("Most Viewed Application (Total): " + db.mostTimeTotal().getName()+ " " + String.format("%.3f", db.mostTimeTotal().getTime()) + " seconds");

            JLabel sessionAvg = new JLabel("Average Application View Time (Session): " + String.format("%.3f", db.sessionAvg()) + " seconds");
            JLabel totalAvg = new JLabel("Average Application View Time (Total): " + String.format("%.3f", db.totalAvg()) + " seconds");

            metrics.add(top);

            metrics.add(mostViewedSession);
            metrics.add(sessionAvg);
            metrics.add(mostViewedTotal);
            metrics.add(totalAvg);

            metrics.setVisible(true);
            metrics.pack();
            metrics.toFront();
        }
    }

    public static void main(String[] args){
        ApplicationGUI obj = new ApplicationGUI();
        obj.init();
    }
}