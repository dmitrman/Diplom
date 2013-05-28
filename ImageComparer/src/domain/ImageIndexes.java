/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;

/**
 * Содержит описание всех векторов признаков изображения
 * 
 * @author Dima
 */
public class ImageIndexes {
   /** признаки формы */ 
   public CurvaturePhaseIndex curvPhaseIndex;    
   /** признаки цвета при полутоновом изображении */
   public double[] rgbhalfton;
   /** признаки цвета при делении полутонового изображения */
   public ArrayList<double[]> quadroTree;
   /** признаки цвета при делении изображения с учётом каждого канала цвета */
   public ArrayList<double[]> quadroTreeRGB;
   /** признаки цвета по методу цветовых моментов */
   public ColorMomentsIndex colorMomentsIndex;
}
