/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;
import service.ImageProcessing;

/**
 *
 * @author Dima
 */
public class HistogramRGB extends javax.swing.JFrame {  
    
    ImageProcessing ip =new ImageProcessing();
    int N=20;
    double[][] r;
     double[][] g;
      double[][] b;
       double[][] rgb;
        String plotTitle = "Histogram"; 
       String xaxis = "number";
       String yaxis = "value"; 
       PlotOrientation orientation = PlotOrientation.VERTICAL; 
       boolean show = false; 
       boolean toolTips = false;
       boolean urls = false; 
       
       String current;
    public HistogramRGB(BufferedImage im,String title)  {
        
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        
        jPanel1.setLayout(new BorderLayout());
        jComboBox1.addItem("RGB");jComboBox1.addItem("R");jComboBox1.addItem("G");jComboBox1.addItem("B");
        this.setTitle(title);      
        setImage(im);
        final DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("RGB", createSeries("RGB"));
        current="RGB";
      //  dataset.addSeries("Series1", createSeries(1));
        final JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart, false);
      //  chartPanel.setPreferredSize(new Dimension(640, 480));
        jPanel1.add(chartPanel, BorderLayout.CENTER);
        XYPlot plot = (XYPlot) chart.getPlot();
                  final XYItemRenderer rend = plot.getRenderer();
                  rend.setSeriesPaint(0,Color.BLACK);
        jComboBox1.addActionListener(new ActionListener(){
  
            @Override
            public void actionPerformed(ActionEvent e) {
                 dataset.removeSeries(current);
                 dataset.addSeries((String)jComboBox1.getSelectedItem(),createSeries((String)jComboBox1.getSelectedItem()));
                 current=(String)jComboBox1.getSelectedItem();
                
                  switch((String)jComboBox1.getSelectedItem()){
                      case "RGB": rend.setSeriesPaint(0,Color.BLACK);break;
                      case "R":  rend.setSeriesPaint(0,Color.RED);break;
                      case "G":  rend.setSeriesPaint(0,Color.GREEN);break;
                      case "B":  rend.setSeriesPaint(0,Color.BLUE);break;
                  }
               
                 
            }
            
        } );
        
    }

    private HistogramRGB() {
        
    }
    
    private void setImage(BufferedImage img) {
        
       
        rgb= PutIntoSets(ip.toHalfToningImage(img));        
        r=PutIntoSets(ip.getRedChannelOfImage(img));
        g=PutIntoSets(ip.getGreenChannelOfImage(img));
        b=PutIntoSets(ip.getBlueChannelOfImage(img));
      
    }

    boolean f=true;
    JFreeChart chart;

double[][] createSeries(String str){
      switch((String)jComboBox1.getSelectedItem()){
            case "RGB": return(rgb);
            case "G":   return(g);
            case "B":  return(b);
                case "R":  return(r);
        }
        return null;
}
   private JFreeChart createChart(XYDataset dataset) {

        // create the chart...
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Color histogram", // chart title
            "Intervals", // domain axis label
            "Range", // range axis label
            dataset,  // initial series
            PlotOrientation.VERTICAL, // orientation
            true, // include legend
            true, // tooltips?
            false // URLs?
            );

        // set chart background
        chart.setBackgroundPaint(Color.white);

        // set a few custom plot features
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        
       
        // set the plot's axes to display integers
        TickUnitSource ticks = NumberAxis.createIntegerTickUnits();
        NumberAxis domain = (NumberAxis) plot.getDomainAxis();
        domain.setStandardTickUnits(ticks);
        NumberAxis range = (NumberAxis) plot.getRangeAxis();
        range.setStandardTickUnits(ticks);

        // render shapes and lines
        XYLineAndShapeRenderer renderer =
            new XYLineAndShapeRenderer(true, true);
        plot.setRenderer(renderer);
        //renderer.setBaseShapesVisible(true);
       // renderer.setBaseShapesFilled(true);

        // set the renderer's stroke
      /*  Stroke stroke = new BasicStroke(
            3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL);
        renderer.setBaseOutlineStroke(stroke);*/

        // label the points
       // NumberFormat format = NumberFormat.getNumberInstance();
       // format.setMaximumFractionDigits(2);
  /*      XYItemLabelGenerator generator =
            new StandardXYItemLabelGenerator(
                StandardXYItemLabelGenerator.DEFAULT_ITEM_LABEL_FORMAT,
                format, format);
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);*/

        return chart;
    }
 double[][] PutIntoSets(ArrayList<Integer> mas){
     double[] regions=new double[N];
     double[][] reg=new double[2][N];
 
     for(int i=1;i<N;i++){
         regions[i]=0;
     }
     for(int y:mas){
         regions[(int)y/N]++;
     }
     double s=0;
      for(double y:regions){
          s+=y;
      }
      int i=0;
         for(double y:regions){
             reg[0][i]=i;
             reg[1][i]=y/s;
             i++;
         }
     System.out.println(" *** "+s);    
     //printIntMas(regions);
     return reg;
 }   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Канал:");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 234, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(69, 69, 69)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(161, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HistogramRGB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HistogramRGB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HistogramRGB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HistogramRGB.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HistogramRGB().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
