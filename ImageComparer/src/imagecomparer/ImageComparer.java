/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagecomparer;

import presentation.MainWindow;

/**
 *
 * @author Dima
 */
public class ImageComparer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
            //        try {
                        //   try {
                               try {
                              for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                                  if ("Nimbus".equals(info.getName())) {
                                      javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                      break;
                                  }
                              }
                          } catch (ClassNotFoundException ex) {
                              java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                          } catch (InstantiationException ex) {
                              java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                          } catch (IllegalAccessException ex) {
                              java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                          } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                              java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                          }
                         MainWindow w=new MainWindow();
                          w.setVisible(true);
                          System.out.println("edt");
                        
                        /*  ImageIndexes i=new ImageIndexes();
                          i.a=new double[]{12,45,45};
                         String n= new Gson().toJson(i);
                         System.out.println(n);
                         ImageIndexes list= new Gson().fromJson(n,ImageIndexes.class);
                          
                          */
                          long before = System.currentTimeMillis();

/*
                          RGBBrightnessMethod m=new RGBBrightnessMethod();
                          ArrayList<double[]>  h=m.CompputeQuadroTreeBY_R_G_B(ImageIO.read(new File("C:\\Users\\dgoncharenko\\Dropbox\\DIPLOM\\101_ObjectCategories\\101_ObjectCategories\\TEST\\image_0001 (2).jpg")));
                           ArrayList<double[]>  h1=m.CompputeQuadroTreeBY_R_G_B(ImageIO.read(new File("C:\\Users\\dgoncharenko\\Dropbox\\DIPLOM\\101_ObjectCategories\\101_ObjectCategories\\TEST\\image_0001 (2).jpg")));
                          double d= m.ComputeQuadroDifferenceEvclid_R_G_B(h, h1);
                          long after = System.currentTimeMillis();
            long diff = after - before;
            System.out.println(diff+" "+d);
                                        
                          
                          
                          */
                          
                          
                          
                          
                       
       
    }
}
