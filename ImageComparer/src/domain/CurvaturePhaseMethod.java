/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;


import filters.GaussianFilter;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;
import service.ImageProcessing;
import service.MatrixComputation;

/**
 *
 * @author dgoncharenko
 */
public class CurvaturePhaseMethod {

    int SIZE=300;  // размер окна для обработки
    int THRESHOLD=80;// порог бинаризации
    int N=15; // размерность гистограммы (вектора признаков)
    ImageProcessing ip=new ImageProcessing();
    MatrixComputation mat=new MatrixComputation();    
    float radius=5.36f;
    float sigma=3.36f;
            
    public CurvaturePhaseMethod() {
    }
public CurvaturePhaseIndex ComputeCurvaturePhaseIndex(BufferedImage img1){
       
             img1=getLaws(img1);
             ip.writeImageToFile((img1), "png", "C:\\BlurredImage.png");
             CurvaturePhaseIndex index=new CurvaturePhaseIndex(); 
             GaussianFilter fil=new GaussianFilter(radius,sigma);             
             img1=fil.filter(img1, null);
             
             img1=ip.toHalfToning(img1);
          
         //   img1=ip.toBinaryImage(img1, THRESHOLD);
               
           DenseMatrix64F ix= MatrixComputation.getDenseMatrix64FromImage(getLx(img1));  
           DenseMatrix64F iy= MatrixComputation.getDenseMatrix64FromImage(getLy(img1));
           DenseMatrix64F ixx= MatrixComputation.getDenseMatrix64FromImage(getLxx(img1));  
           DenseMatrix64F iyy= MatrixComputation.getDenseMatrix64FromImage(getLyy(img1));
           DenseMatrix64F ixy=MatrixComputation.getDenseMatrix64FromImage(getLxy(img1));
            
           DenseMatrix64F N= getNormalCurvature(ix,iy,ixx,iyy,ixy);
           DenseMatrix64F T=getTangentialCurvature(ix,iy,ixx,iyy,ixy);
           DenseMatrix64F P=  getPhase(iy, ix);
           DenseMatrix64F S=getShape(N,T);
           
              index.normal=getNormalVector(PutIntoSets(N));
              index.phase=getNormalVector(PutIntoSets(P));
              index.tangential=getNormalVector(PutIntoSets(T));
              index.shape=getNormalVector(PutIntoSets(S));
           
           
           return index;
         
       
}
public void printCurvaturePhaseIndex(CurvaturePhaseIndex c){
    System.out.println("Normal ");
    printIntMas(c.normal);
      System.out.println("Tangential ");
      printIntMas(c.tangential);
        System.out.println("Phase ");
        printIntMas(c.phase);
          System.out.println("Shape ");
          printIntMas(c.shape);
     
}
/*BufferedImage ImageBlurring(){
     GaussianFilter fil=new GaussianFilter(radius,sigma);             
     return(fil.filter(img1, null));
}*/
public void setSigma(float s){
    sigma=s;
}
public void setRadius(float r){
    radius=r;
}
  // первая производная Lx
BufferedImage getLx(BufferedImage img){
     float[] mas=new float[] { -0.5f,0f,0.5f};
     
       Kernel k = new Kernel(1, 3,mas );
              ConvolveOp op1 = new ConvolveOp(k);
              BufferedImage blurry = op1.filter(img, null);
       return blurry;
}
BufferedImage getLaws(BufferedImage img){
     float[] mas=new float[] {- 1f,  - 4f,  - 6f,   - 4f,  - 1f,
                              - 2f,  - 8f,  - 12f,  - 8f,  - 2f,  
                                0f,    0f,    0f,     0f,    0f,  
                                2f,    8f,    12f,    8f,    2f,  
                                1f,    4f,    6f,     4f,    1f };
     
       Kernel k = new Kernel(5, 5,mas );
              ConvolveOp op1 = new ConvolveOp(k);
              BufferedImage blurry = op1.filter(img, null);
       return blurry;
}
/*BufferedImage getLaws(BufferedImage img){
     float[] mas=new float[] {- 1f,  - 4f,  - 6f,   - 4f,  - 1f,
                              - 2f,  - 8f,  - 12f,  - 8f,  - 2f,  
                                0f,    0f,    0f,     0f,    0f,  
                                2f,    8f,    12f,    8f,    2f,  
                                1f,    4f,    6f,     4f,    1f };
     
       Kernel k = new Kernel(5, 5,mas );
              ConvolveOp op1 = new ConvolveOp(k);
              BufferedImage blurry = op1.filter(img, null);
       return blurry;
}
BufferedImage getLaws(BufferedImage img){
     float[] mas=new float[] {- 1f,  - 4f,  - 6f,   - 4f,  - 1f,
                              - 2f,  - 8f,  - 12f,  - 8f,  - 2f,  
                                0f,    0f,    0f,     0f,    0f,  
                                2f,    8f,    12f,    8f,    2f,  
                                1f,    4f,    6f,     4f,    1f };
     
       Kernel k = new Kernel(5, 5,mas );
              ConvolveOp op1 = new ConvolveOp(k);
              BufferedImage blurry = op1.filter(img, null);
       return blurry;
}
BufferedImage getLaws(BufferedImage img){
     float[] mas=new float[] {- 1f,  - 4f,  - 6f,   - 4f,  - 1f,
                              - 2f,  - 8f,  - 12f,  - 8f,  - 2f,  
                                0f,    0f,    0f,     0f,    0f,  
                                2f,    8f,    12f,    8f,    2f,  
                                1f,    4f,    6f,     4f,    1f };
     
       Kernel k = new Kernel(5, 5,mas );
              ConvolveOp op1 = new ConvolveOp(k);
              BufferedImage blurry = op1.filter(img, null);
       return blurry;
}*/
// первая производная Ly
BufferedImage getLy(BufferedImage img){
     float[] mas=new float[] { 0.5f,0.0f,-0.5f};
     
       Kernel k = new Kernel(3, 1,mas );
              ConvolveOp op1 = new ConvolveOp(k);
              BufferedImage blurry = op1.filter(img, null);
       return blurry;
}
BufferedImage getLxx(BufferedImage img){
     float[] mas=new float[] { 1.0f,-2.0f,1.0f};
     
       Kernel k = new Kernel(1, 3,mas );
              ConvolveOp op1 = new ConvolveOp(k);
              BufferedImage blurry = op1.filter(img, null);
       return blurry;
}
BufferedImage getLyy(BufferedImage img){
      float[] mas=new float[] { 1.0f,-2.0f,1.0f};
     
       Kernel k = new Kernel(3, 1,mas );
              ConvolveOp op1 = new ConvolveOp(k);
              BufferedImage blurry = op1.filter(img, null);
       return blurry;
}
BufferedImage getLxy(BufferedImage img){
     float[] mas=new float[] { -0.25f,0f,0.25f,0f,0f,0f,0.25f,0f,-0.25f};
     
       Kernel k = new Kernel(3, 3,mas );
              ConvolveOp op1 = new ConvolveOp(k);
              BufferedImage blurry = op1.filter(img, null);
       return blurry;
}
// тангенциальная кривизна
 DenseMatrix64F getTangentialCurvature(DenseMatrix64F ix,DenseMatrix64F iy,DenseMatrix64F ixx,DenseMatrix64F iyy,DenseMatrix64F ixy){
       
     DenseMatrix64F c;  
     c=mat.MatrixSubstraction(mat.MatrixMultiplication(ix, ix),mat.MatrixMultiplication(iy, iy));
     c=mat.MatrixMultiplication(c, ixy);
     c=mat.MatrixAddition(c,mat.MatrixMultiplication(mat.MatrixMultiplication(mat.MatrixSubstraction(ixx,iyy),ix),iy));     
     DenseMatrix64F a ;
     a=mat.MatrixAddition(mat.MatrixMultiplication(ix, ix),mat.MatrixMultiplication(iy, iy));
     CommonOps.add(a, 1);
     CommonOps.elementDiv(c, a); 
    return c;
} 
 
// получить нормальную кривизну
DenseMatrix64F getNormalCurvature(DenseMatrix64F ix,DenseMatrix64F iy,DenseMatrix64F ixx,DenseMatrix64F iyy,DenseMatrix64F ixy){
    
    DenseMatrix64F c;//=new DenseMatrix64F(ix.numRows,ix.numCols);
    c=mat.MatrixMultiplication(mat.MatrixMultiplication(ix, ix),iyy);    
    DenseMatrix64F a;// =new DenseMatrix64F(ix.numRows,ix.numCols);
    a=mat.MatrixMultiplication(mat.MatrixMultiplication(iy, iy),ixx);  
    c=mat.MatrixAddition(c, a);
    a=mat.MatrixMultiplication(ix, iy);
    a=mat.MatrixMultiplication(a,ixy);    
    CommonOps.scale(-2d, a);
    c=mat.MatrixAddition(c, a);
    a=mat.MatrixAddition(mat.MatrixMultiplication(ix, ix),mat.MatrixMultiplication(iy, iy));    
    CommonOps.elementDiv(c, a);
    return c;
}
// Shape index
DenseMatrix64F getShape(DenseMatrix64F N, DenseMatrix64F T){
    DenseMatrix64F C=new DenseMatrix64F(N.numRows,N.numCols);
    for(int i=0;i<N.numRows;i++){
        for (int j=0;j<N.numCols;j++){
           C.set(i, j, Math.atan((N.get(i, j)+T.get(i, j))/(N.get(i, j)-T.get(i, j)+1))); 
        }
    }
    return C;
}
// Phase
DenseMatrix64F getPhase(DenseMatrix64F iy,DenseMatrix64F ix){
     DenseMatrix64F P=new DenseMatrix64F(iy.numRows,iy.numCols);
    for(int i=0;i<iy.numRows;i++){
        for (int j=0;j<iy.numCols;j++){
           P.set(i, j, Math.atan2(iy.get(i, j),ix.get(i, j))); 
        }
    }
    return P;
}
void printAllDerivatives(BufferedImage blurry){
     
              ip.writeImageToFile(blurry, "png", "C:\\BBBB1.png");
                ip.writeImageToFile(getLx(blurry), "png", "C:\\333x.png");
                 ip.writeImageToFile(getLy(blurry), "png", "C:\\333y.png");
                  ip.writeImageToFile(getLxx(blurry), "png", "C:\\333xx.png");
                   ip.writeImageToFile(getLyy(blurry), "png", "C:\\333yy.png");
                    ip.writeImageToFile(getLxy(blurry), "png", "C:\\333xy.png");
                  
}   
// получить размерность матрицы
void getSizeM( DenseMatrix64F c){
     System.out.println("Rows "+c.numRows+" Colls= "+c.numCols);
}    
// получить сумму матрицы
double getSumM( DenseMatrix64F M){
    return CommonOps.sumRows(CommonOps.sumCols(M, null),null).get(0);
}
// получить вектор признаков размерности N из матрицы
 double[] PutIntoSets(DenseMatrix64F M){
     //mat.printMatrix(M);
     double[] mas=M.data;
     double max=CommonOps.elementMaxAbs(M);
     // System.out.println("Max= "+max+" "+((int)max/N));
     double[] regions=new double[N];
      for(int i=1;i<N;i++){
         regions[i]=0;
     }
     for(double y:mas){
         //System.out.println("f "+((int)Math.abs(N/max*y)));
         regions[(int)Math.abs((N-1)/max*y)]++;
     }
    // printIntMas(regions);
     return regions;
 } 
 // вывести массив
 void printIntMas(double[] mas){
     for (int i=0;i<mas.length;i++){
     System.out.print(mas[i]+" ");    
     }
      System.out.println();
 } 
 // получить среднее вектора
 double GetAverage(double[] mas){
     int sum=0;
       for(int i=0;i<mas.length;i++) {
         sum+=mas[i];
     }
       
       return sum/mas.length;
 }
 // получить стандартное среднеквадратическое отклонение
double getStandardDeviation(double[] mas){
    double avg=GetAverage(mas);
    double dev=0;
    for(int i=0;i<mas.length;i++){
        dev+=Math.pow((mas[i]-avg),2d);
    }
    dev/=mas.length;
    return Math.pow(dev, 0.5d);
}  
// получить корреляционный коеффициент вектора
public double CorrelationCoefficient(double[] f1,double[] f2){
    
    double avg1=GetAverage(f1);
    double avg2=GetAverage(f2);
    double dev1=getStandardDeviation(f1);
    double dev2=getStandardDeviation(f2);
    double corl=0;
    for(int i=0;i<f1.length;i++){
        corl+=(f1[i]-avg1)*(f2[i]-avg2);
    }
    corl/=f1.length;
    return corl/(dev1*dev2);
}
// нормирование вектора
double[] getNormalVector(double[] mas){
    
    double s=0;
    for(int i=0;i<mas.length;i++){
        s+=mas[i];
    }
    //s=imH*imW;
    double[] out=new double[mas.length];
    for(int i=0;i<mas.length;i++){
        out[i]=mas[i]/s;
    }
    return out;
}
public CurvaturePhaseIndex ComputeCurvaturePhaseIndexOriginal(BufferedImage img1){
       
            // img1=getLaws(img1);
           //  ip.writeImageToFile((img1), "png", "C:\\BlurredImage.png");
             CurvaturePhaseIndex index=new CurvaturePhaseIndex(); 
           
              float radius=5.36f;
           
             img1=ip.toHalfToning(img1);
  ///////////////////////////////////////////////////////////           
          BufferedImage img2;
             GaussianFilter fil=new GaussianFilter(radius,0);             
             img2=fil.filter(img1, null);
               
           DenseMatrix64F ix= MatrixComputation.getDenseMatrix64FromImage(getLx(img2));  
           DenseMatrix64F iy= MatrixComputation.getDenseMatrix64FromImage(getLy(img2));
           DenseMatrix64F ixx= MatrixComputation.getDenseMatrix64FromImage(getLxx(img2));  
           DenseMatrix64F iyy= MatrixComputation.getDenseMatrix64FromImage(getLyy(img2));
           DenseMatrix64F ixy=MatrixComputation.getDenseMatrix64FromImage(getLxy(img2));
            
           DenseMatrix64F N= getNormalCurvature(ix,iy,ixx,iyy,ixy);
           DenseMatrix64F T=getTangentialCurvature(ix,iy,ixx,iyy,ixy);
           DenseMatrix64F P1=  getPhase(iy, ix);
           DenseMatrix64F S1=getShape(N,T);
//////////////////////////////////////////////////////////////////////////////
          
            fil=new GaussianFilter(radius,radius/8);             
             img2=fil.filter(img1, null);
               
           ix= MatrixComputation.getDenseMatrix64FromImage(getLx(img2));  
           iy= MatrixComputation.getDenseMatrix64FromImage(getLy(img2));
           ixx= MatrixComputation.getDenseMatrix64FromImage(getLxx(img2));  
           iyy= MatrixComputation.getDenseMatrix64FromImage(getLyy(img2));
           ixy=MatrixComputation.getDenseMatrix64FromImage(getLxy(img2));
            
            N= getNormalCurvature(ix,iy,ixx,iyy,ixy);
            T=getTangentialCurvature(ix,iy,ixx,iyy,ixy);
           DenseMatrix64F P2=  getPhase(iy, ix);
           DenseMatrix64F S2=getShape(N,T);
//////////////////////////////////////////////////////////////////////////////
          
            fil=new GaussianFilter(radius,radius/4);             
             img2=fil.filter(img1, null);
               
           ix= MatrixComputation.getDenseMatrix64FromImage(getLx(img2));  
           iy= MatrixComputation.getDenseMatrix64FromImage(getLy(img2));
           ixx= MatrixComputation.getDenseMatrix64FromImage(getLxx(img2));  
           iyy= MatrixComputation.getDenseMatrix64FromImage(getLyy(img2));
           ixy=MatrixComputation.getDenseMatrix64FromImage(getLxy(img2));
            
            N= getNormalCurvature(ix,iy,ixx,iyy,ixy);
            T=getTangentialCurvature(ix,iy,ixx,iyy,ixy);
           DenseMatrix64F P3=  getPhase(iy, ix);
           DenseMatrix64F S3=getShape(N,T);
//////////////////////////////////////////////////////////////////////////////
          
            fil=new GaussianFilter(radius,radius/2);             
             img2=fil.filter(img1, null);
               
           ix= MatrixComputation.getDenseMatrix64FromImage(getLx(img2));  
           iy= MatrixComputation.getDenseMatrix64FromImage(getLy(img2));
           ixx= MatrixComputation.getDenseMatrix64FromImage(getLxx(img2));  
           iyy= MatrixComputation.getDenseMatrix64FromImage(getLyy(img2));
           ixy=MatrixComputation.getDenseMatrix64FromImage(getLxy(img2));
            
            N= getNormalCurvature(ix,iy,ixx,iyy,ixy);
            T=getTangentialCurvature(ix,iy,ixx,iyy,ixy); 
           DenseMatrix64F P4=  getPhase(iy, ix);
           DenseMatrix64F S4=getShape(N,T);
//////////////////////////////////////////////////////////////////////////////
          
             fil=new GaussianFilter(radius,radius);             
             img2=fil.filter(img1, null);
               
           ix= MatrixComputation.getDenseMatrix64FromImage(getLx(img2));  
           iy= MatrixComputation.getDenseMatrix64FromImage(getLy(img2));
           ixx= MatrixComputation.getDenseMatrix64FromImage(getLxx(img2));  
           iyy= MatrixComputation.getDenseMatrix64FromImage(getLyy(img2));
           ixy=MatrixComputation.getDenseMatrix64FromImage(getLxy(img2));
            
            N= getNormalCurvature(ix,iy,ixx,iyy,ixy);
            T=getTangentialCurvature(ix,iy,ixx,iyy,ixy);
           DenseMatrix64F P5=  getPhase(iy, ix);
           DenseMatrix64F S5=getShape(N,T);
//////////////////////////////////////////////////////////
           
         
           
           
           
           
           
           
           
           
           
           
           
           
           
           return index;
         
       
}
}
