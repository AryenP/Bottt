package Graph;

import java.util.ArrayList;

public class ScaledDataSet {
    private ArrayList<Float> xvals, yvals;
    private boolean yscaling = true;
    private boolean xscaling = true;
    private float inputymin, inputymax, scaledymin, scaledymax;
    private float inputxmin, inputxmax, scaledxmin, scaledxmax;
    private float xmin, xmax, ymin, ymax;
    private float yshiftval, yscaleval, xshiftval, xscaleval;

    public ScaledDataSet() {
        xvals = new ArrayList<Float>();
        yvals = new ArrayList<Float>();
        xmax = Float.MIN_NORMAL;
        ymax = Float.MIN_NORMAL;
        xmin = Float.MAX_VALUE;
        ymin = Float.MAX_VALUE;
    }

    // Calling this method will automatically scale
    // all points that get added with addPoint.
    // You tell it what your input min and max are
    // and what you want the scaled min and max to be.
    // ** addpoint will ignore data points not between
    // the input min and max! **
    public void setyScaling(float inputymin, float scaledymin,
            float inputymax, float scaledymax) {
        this.inputymin = inputymin;
        this.inputymax = inputymax;
        this.scaledymin = scaledymin;
        this.scaledymax = scaledymax;
        this.yscaling = true;
        this.yscaleval = (scaledymax - scaledymin)/(inputymax - inputymin);
        this.yshiftval = scaledymin - yscaleval*inputymin;
    }

    // todo:  check if re-scaling to existing scaling values!
    public void setxScaling(float inputxmin, float scaledxmin,
            float inputxmax, float scaledxmax) {
        this.inputxmin = inputxmin;
        this.inputxmax = inputxmax;
        this.scaledxmin = scaledxmin;
        this.scaledxmax = scaledxmax;
        this.xscaling = true;
        this.xscaleval = (float)(scaledxmax - scaledxmin)/(float)(inputxmax - inputxmin);
        this.xshiftval = scaledxmin - xscaleval*inputxmin;
    }

    public void addPoint(float x, float y) {
        if (yscaling) {
            // scale the input and then add!
            y = y*yscaleval + yshiftval;
            yvals.add(y);
        } else {
            System.out.println("Graph.ScaledDataSet couldn't add point " + x + ", "
                    + y + "; No y scale values set!");
        }

       if (xscaling) {
            x = x*xscaleval + this.xshiftval;
            xvals.add(x);
        } else {
            System.out.println("Graph.ScaledDataSet couldn't add point " + x + ", "
                    + y + "; No x scale values set!");
        }

        // update min and max values
        if (x < xmin) {
            xmin = x;
        } else if (x > xmax) {
            xmax = x;
        }

        if (y < ymin) {
            ymin = y;
        } else if (y > ymax) {
            ymax = y;
        }
    }

    public float getxmin() {
        return xmin;
    }

    public float getymin() {
        return ymin;
    }

    public float getxmax() {
        return xmax;
    }

    public float getymax() {
        return ymax;
    }

    // return number of data points in the set
    public int getSize() {
        return xvals.size();
    }

    public void clearData() {
        xvals.clear();
        yvals.clear();
        xmax = Float.MIN_NORMAL;
        ymax = Float.MIN_NORMAL;
        xmin = Float.MAX_VALUE;
        ymin = Float.MAX_VALUE;
    }

    public void clear() {
        xvals.clear();
        yvals.clear();
        xmax = Float.MIN_NORMAL;
        ymax = Float.MIN_NORMAL;
        xmin = Float.MAX_VALUE;
        ymin = Float.MAX_VALUE;
    }

    public float getx(int num) {
        return xvals.get(num);
    }
    
    public float gety(int num) {
        return yvals.get(num);
    }
}