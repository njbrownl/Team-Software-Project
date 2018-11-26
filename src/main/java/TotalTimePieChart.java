import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TotalTimePieChart {

    private static JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Total Time Pie Chart",
                dataset,
                true,
                true,
                false
        );
        return chart;
    }

    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Database database = new Database();
        ArrayList<TimePair> totalData = database.selectTotalData();
        for (TimePair d : totalData) {
            dataset.setValue(d.getName(), new Double(d.getTime()));
        }
        return dataset;
    }

    private static PieSectionLabelGenerator createLabelGen() {
        PieSectionLabelGenerator generator = new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", new DecimalFormat("0.00s"), new DecimalFormat("0.00%")
        );
        return generator;
    }

    JPanel createPanel() {
        JFreeChart chart = createChart(createDataset());
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(createLabelGen());
        return new ChartPanel(chart);
    }

}
