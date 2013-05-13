/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

/**
 *
 * @author Dima
 */
 public class HistogramExample {
     
       public ChartPanel getHistogramPanel(){
            double[] value = new double[100];
       Random generator = new Random();
       for (int i=1; i < 100; i++) {
       value[i] = generator.nextDouble();}
           int number = 16;// число столбцов гистограммы
       HistogramDataset dataset = new HistogramDataset();
       
      // dataset.setType(HistogramType.SCALE_AREA_TO_1);
       dataset.addSeries("Histogram",value,number);
       String plotTitle = "Histogram"; 
       String xaxis = "number";
       String yaxis = "value"; 
       PlotOrientation orientation = PlotOrientation.VERTICAL; 
       boolean show = false; 
       boolean toolTips = false;
       boolean urls = false; 
      JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);
      return new ChartPanel(chart);
       }
     
     
       public static void main(String[] args) {
      /* double[] value = new double[100];
       Random generator = new Random();
       for (int i=1; i < 100; i++) {
       value[i] = generator.nextDouble();}
           int number = 16;// число столбцов гистограммы
       HistogramDataset dataset = new HistogramDataset();
       
      // dataset.setType(HistogramType.SCALE_AREA_TO_1);
       dataset.addSeries("Histogram",value,number);
       String plotTitle = "Histogram"; 
       String xaxis = "number";
       String yaxis = "value"; 
       PlotOrientation orientation = PlotOrientation.VERTICAL; 
       boolean show = false; 
       boolean toolTips = false;
       boolean urls = false; 
      JFreeChart chart = ChartFactory.createHistogram( plotTitle, xaxis, yaxis, 
                dataset, orientation, show, toolTips, urls);
       
       int width = 500;
       int height = 300; 
        try {
        ChartUtilities.saveChartAsPNG(new File("histogram.PNG"), chart, width, height);
        } catch (IOException e) {}
         
       JFrame f=new JFrame();
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     ///   HistPanel p=new HistPanel();
    
        f.add(new ChartPanel(chart));f.setSize(300,200);
        f.setVisible(true);*/
   }
 }