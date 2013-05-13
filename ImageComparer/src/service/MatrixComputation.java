/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

/**
 *
 * @author dgoncharenko
 */
public class MatrixComputation {

    public MatrixComputation() {
    }
    
    // ускоренное произведение матриц
public DenseMatrix64F  MatrixMultiplication( DenseMatrix64F a, DenseMatrix64F b){
    
       DenseMatrix64F c =new DenseMatrix64F(a.numRows,a.numCols);
    //   CommonOps.multTransB(a,b,c);
        CommonOps.elementMult(a, b, c);
        //System.out.println(" "+(int)c.data[0]);
     //  CommonOps.sumCols(CommonOps.sumRows(c, null), null);
      // CommonOps.add(a,b,c);
      // printMatrix(CommonOps.sumRows(CommonOps.sumCols(c, null), null));
        return c;
    
}
// ускоренное сложение матриц
  // ускоренное произведение матриц
public DenseMatrix64F  MatrixAddition( DenseMatrix64F a, DenseMatrix64F b){
    DenseMatrix64F c =new DenseMatrix64F(a.numRows,a.numCols);  
    CommonOps.add(a,b,c);
    return c;
}
public DenseMatrix64F  MatrixSubstraction( DenseMatrix64F a, DenseMatrix64F b){
    DenseMatrix64F c =new DenseMatrix64F(a.numRows,a.numCols);
  /*  CommonOps.changeSign(b);
    CommonOps.add(a,b,c);*/
    CommonOps.sub(a, b, c);
    return c;
}

// вывод матрицы
public static  void printMatrix(DenseMatrix64F m){
    for(int i=0; i<m.getNumRows();i++){
        for(int j=0;j<m.getNumCols();j++){
            System.out.print(" El= "+m.get(i,j));
        }
         System.out.println("\n");
    }
}
//
public void fillMatrix(DenseMatrix64F m)
{
    for(int i=0;i<3;i++){
        for(int j=0;j<3;j++){
            m.set(i, j, 3.2*(i+j));
        }
    }
}
public DenseMatrix64F getDenseMatrix64FromArrayList(ArrayList<Integer> ar){
    
    DenseMatrix64F a=new  DenseMatrix64F(1, ar.size());
    for(int i=0;i<ar.size();i++){
        a.set(0, i, ar.get(i));
    }
    return a;
}

public static DenseMatrix64F getDenseMatrix64FromImage(BufferedImage img){
     final int iw=img.getWidth();
               final int ih=img.getHeight();
  DenseMatrix64F a=new  DenseMatrix64F(ih, iw);

      
   for(int i=0;i<ih;i++){
    for(int j=0;j<iw;j++){
            int c= img.getRGB(j,i); 
           
            a.set(i,j,c&255);
          
   //         System.out.println(c&255);
    }
   }
   return a;
}
 public static DenseMatrix64F getDenseMatrix64FromDoubleArray(double[] ar){
     
  DenseMatrix64F a=new  DenseMatrix64F(1, ar.length);

      
  
    for(int j=0;j<ar.length;j++){
            double c= ar[j]; 
            a.set(0,j, c);
    }
  
   return a;
}
}
