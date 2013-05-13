/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import service.ImageProcessing;

/**
 *
 * @author dgoncharenko
 */
public class QuadroTrees  {
    public TreeMap<Integer, String> treemap = new TreeMap<Integer, String>();
     TreeNode root=new TreeNode();
     TreeNode nodeA;
     TreeNode nodeB;
     TreeNode nodeC;
     TreeNode nodeD;
    int u=1;
    int n=3;
 public BufferedImage img;
 Graphics2D g;
    public QuadroTrees() {
        try {
            img =ImageIO.read(new File("C:\\1.jpg"));
            g=(Graphics2D) img.getGraphics();
        } catch (IOException ex) {
            Logger.getLogger(QuadroTrees.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    String str=new String();
    public void splitImage(BufferedImage img,TreeNode node){
       // if(node==null) node=root;
       // System.out.println("dggf");
        BufferedImage img1=img.getSubimage(0, 0, img.getWidth()/2, img.getHeight()/2);
        BufferedImage img2=img.getSubimage(img.getWidth()/2, img.getHeight()/2, img.getWidth()/2, img.getHeight()/2);
        BufferedImage img3=img.getSubimage(0, img.getHeight()/2, img.getWidth()/2, img.getHeight()/2);
        BufferedImage img4=img.getSubimage(img.getWidth()/2, 0, img.getWidth()/2, img.getHeight()/2);
    //   g.setColor(Color.black);
        g.drawRect(0, 0, img.getWidth()/2, img.getHeight()/2);
         g.setColor(Color.green);
         g.drawRect(img.getWidth()/2, img.getHeight()/2, img.getWidth()/2, img.getHeight()/2);
          g.setColor(Color.yellow);
          g.drawRect(0, img.getHeight()/2, img.getWidth()/2, img.getHeight()/2);
           g.setColor(Color.MAGENTA);
           g.drawRect(img.getWidth()/2, 0, img.getWidth()/2, img.getHeight()/2);
      //  g.drawRect(0, 0, img.getWidth(), img.getHeight());
     /*    writeImageToFile(img1, "jpg", "C:\\AAA"+u+".jpg");
          writeImageToFile(img2, "jpg", "C:\\BBB"+u+".jpg");
           writeImageToFile(img3, "jpg", "C:\\CCC"+u+".jpg");
            writeImageToFile(img4, "jpg", "C:\\DDD"+u+".jpg");*/
         //     writeImageToFile(img4, "jpg", "C:\\DDD"+u+".jpg");
        str="";
        int medium1=getAverage(img1);
        int medium2=getAverage(img2);
        int medium3=getAverage(img3);
        int medium4=getAverage(img4);
        System.out.println("dggf "+medium1+" "+medium2+" "+medium3+" "+medium4);
        nodeA=new TreeNode();nodeA.value=medium1;
        nodeB=new TreeNode();nodeB.value=medium2;
        nodeC=new TreeNode();nodeC.value=medium3;
        nodeD=new TreeNode();nodeD.value=medium4;
        node.a=nodeA;
        node.b=nodeB;
        node.c=nodeC;
        node.d=nodeD;
        int avg=30;
        
        if(medium1>avg)       
            str+="1";
        else
           str+="0";
        if(medium2>avg)
             
           str+="1";
        else
           str+="0";
        
        if(medium3>avg)           
         str+="1";
        else
         str+="0";
        
        if(medium4>avg)             
          str+="1";
        else
          str+="0";
        
         treemap.put(u, str);
         u++;
        // if(u<3){
       if(medium1>avg)       
          if((img1.getHeight()!=1)&&(img1.getWidth()!=1))
                  splitImage(img1,nodeA);
        if(medium2>avg)
        if((img2.getHeight()!=1)&&(img2.getWidth()!=1))
            splitImage(img2,nodeB);
         if(medium3>avg)
         if((img3.getHeight()!=1)&&(img3.getWidth()!=1))
            splitImage(img3,nodeC);
          if(medium4>avg)
        if((img4.getHeight()!=1)&&(img4.getWidth()!=1))
            splitImage(img4,nodeD);
        // }
         
    }
    public int getAverage(BufferedImage img){
            int iw=img.getWidth();
           int ih=img.getHeight();
           int s=0;
    for(int i=0;i<ih;i++){
    for(int j=0;j<iw;j++){
    //   s+=img.getRGB(j,i)&255;   
        int c= img.getRGB(j,i); 
            
               int a=(int)(((c&16711680)>>16)*0.3+((c&65280)>>8)*0.59+((c&255))*0.11);
           s+=a;    
    }
    }
    return s/(iw*ih);
    }
    public TreeNode returnRoot(){
        return root;
    }
    public  void writeImageToFile(BufferedImage img,String type,String file){
        try {               
            File file1 = new File(file);
            ImageIO.write(img, type, file1);
        } catch (IOException ex) {
            Logger.getLogger(ImageProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
}
}

 /*       TreeNode root=new TreeNode();
                               
                          QuadroTrees q=new QuadroTrees();
                          q.splitImage(ImageIO.read(new File("C:\\1.jpg")),root);
                          q.writeImageToFile(q.img,"jpg", "C:\\111.jpg");
                          NavigableMap nmap=q.treemap.descendingMap();
                         
                         System.out.println("Checking value");
                         System.out.println("Navigable map values: "+nmap);
                           } catch (IOException ex) {
                               Logger.getLogger(ImageComparer.class.getName()).log(Level.SEVERE, null, ex);
                           }
                           */
                   /* } catch (IOException ex) {
                        Logger.getLogger(ImageComparer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    */