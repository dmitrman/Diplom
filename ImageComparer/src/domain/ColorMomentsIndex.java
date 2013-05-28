/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author dgoncharenko
 */
public class ColorMomentsIndex {

    public ColorMomentsIndex(double[] h, double[] s, double[] v) {
        this.h = h;
        this.s = s;
        this.v = v;
    }
    
     double[] h;
     double[] s;
     double[] v;
    public double[] getH(){
    return h;
    }        
    public double[] getS(){
        return s;
    }
    public double[] getV(){
        return v;
    }
}
