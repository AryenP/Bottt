package Graph;

import java.util.HashMap;
import java.util.Set;
import processing.core.PApplet;
import processing.core.PFont;

/**
 * The Graph.Graph class represents and draws a 2d line graph
 * @author David
 *
 */

public class Graph {
	private PApplet graphicsWindow;
	private float xmin, xmax, ymin, ymax;
	private float dataxmin, dataxmax, dataymin, dataymax;
	private float dataxrange, datayrange;
	private float yshiftval, yscaleval, xshiftval, xscaleval;
	private boolean CONNECTED = true;
	private boolean wrap = true;
	
	// used by the plotdata method to tell itself
	// when it needs to clear data during a wrap
	private boolean cleardata = false;
	
	// DataSets for all past numbers of animals at any timestep
	private HashMap<Object, ScaledDataSet> dataSets;
	private HashMap<Object, Integer> colorMap;
	private static int[] defaultColors;
	private int nextColor = 0;
	public boolean drawLines = false;
	public boolean drawPoints = true;
	public int pointsize = 3;
	
	// labels etc.
	public String xlabel;
	public String ylabel;
	public String title;
	public int titlePointSize = 20;
	public int labelPointSize = 16;
	public int numXIncrements = 5;
	public int numYIncrements = 5;
	public int HSPACING = 20;
	PFont font;

	public Graph(PApplet p, float xmin, float ymin, float xmax, float ymax,
			float dataxmin, float dataymin, float dataxmax, float dataymax) {
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
		setDataRanges(dataxmin, dataxmax, dataymin, dataymax);
		this.graphicsWindow = p;
		this.dataSets = new HashMap<Object, ScaledDataSet>();
		this.colorMap = new HashMap<Object, Integer>();
		this.defaultColors = new int[3];
		this.defaultColors[0] = p.color(255, 100, 100);
		this.defaultColors[1] = p.color(100, 100, 255);
		this.defaultColors[2] = p.color(100, 255, 100);
		font = p.createFont("SansSerif", 20);
		p.textFont(font);
	}

	public void setDataRanges(float xmin, float xmax, float ymin, float ymax) {
		this.dataxmin = xmin;
		this.dataxmax = xmax;
		this.dataymin = ymin;
		this.dataymax = ymax;

		this.dataxrange = xmax - xmin;
		this.datayrange = ymax - ymin;

		this.xscaleval = (float) (xmax - xmin) / (float) (dataxmax - dataxmin);
		this.xshiftval = xmin - xscaleval * dataxmin;
		this.yscaleval = (ymax - ymin) / (dataymax - dataymin);
		this.yshiftval = ymin - yscaleval * dataymin;

		// this.xscaleval = (float) (dataxmax - dataxmin) / (float) (xmax - xmin);
		// this.xshiftval = dataxmin - xscaleval * xmin;
		// this.yscaleval = (dataymax - dataymin) / (ymax - ymin);
		// this.yshiftval = dataymin - yscaleval * ymin;
	}

	public void setPosition(float xmin, float xmax, float ymin, float ymax) {
		this.xmin = xmin;
		this.xmax = xmax;
		this.ymin = ymin;
		this.ymax = ymax;
	}

	public void draw() {
		int c;
		ScaledDataSet d;

		this.drawAxes();
		drawTitle();
		drawXAxisLabel();
		drawYAxisLabel();
		drawXIncrements();
		drawYIncrements();
		drawXIncrementLabels();
		drawYIncrementLabels();

		for (Object f : dataSets.keySet()) {
			d = dataSets.get(f);
			c = this.colorMap.get(f);
			graphicsWindow.fill(c); // change the color
			graphicsWindow.stroke(c); // change the color
			for (int i = 1; i < d.getSize(); i++) {
				if (drawLines) {
					graphicsWindow.line(d.getx(i - 1), d.gety(i - 1), d.getx(i), d.gety(i));
				}
				if (drawPoints) {
					graphicsWindow.ellipse(d.getx(i), d.gety(i), pointsize, pointsize);
				}
			}
		}
		graphicsWindow.stroke(0);
	}

	private void drawAxes() {
		graphicsWindow.stroke(graphicsWindow.color(0));
		graphicsWindow.line(xmin, ymin, xmin, ymax);
		graphicsWindow.line(xmin, ymax, xmax, ymax);
	}

	private void drawTitle() {
		if ((title != null) && (!title.equals(""))) {
			graphicsWindow.fill(0);
			graphicsWindow.textSize(titlePointSize);
			graphicsWindow.textAlign(graphicsWindow.LEFT);
			graphicsWindow.text(title, xmin + 10, ymin);
		}
	}

	private void drawYAxisLabel() {
		if ((ylabel != null) && (!ylabel.equals(""))) {
			graphicsWindow.fill(0);
			graphicsWindow.textSize(labelPointSize);
			graphicsWindow.textAlign(graphicsWindow.RIGHT);
			graphicsWindow.text(ylabel, xmin - HSPACING, (ymin + ymax) / 2);
		}
	}

	private void drawXAxisLabel() {
		if ((xlabel != null) && (!xlabel.equals(""))) {
			graphicsWindow.fill(0);
			graphicsWindow.textSize(labelPointSize);
			graphicsWindow.textAlign(graphicsWindow.CENTER);
			graphicsWindow.text(xlabel, (xmin + xmax) / 2, ymax + 22);
		}
	}

	private void drawXIncrements() {
		float dxinc = (xmax - xmin) / numXIncrements;

		for (float i = xmin; i < xmax; i += dxinc) {
			graphicsWindow.stroke(100);
			graphicsWindow.line(i, ymax, i, ymax + 4);
		}
	}

	private void drawXIncrementLabels() {
		float dxinc = (xmax - xmin) / numXIncrements;
		float dataxinc = (dataxmax - dataxmin) / numXIncrements;
		String text;

		float c = dataxmin;
		for (float i = xmin; i < xmax; i += dxinc) {
			graphicsWindow.fill(0);
			graphicsWindow.textSize(10);
			graphicsWindow.textAlign(graphicsWindow.CENTER);
			text = Float.toString(c);
			graphicsWindow.text(text, i, ymax + 10);
			c += dataxinc;
		}
	}

	private void drawYIncrementLabels() {
		float dyinc = (ymin - ymax) / numYIncrements;
		float datayinc = (dataymax - dataymin) / numYIncrements;
		String text;

		float c = dataymax;
		for (float i = ymax; i < ymin; i += dyinc) {
			graphicsWindow.fill(0);
			graphicsWindow.textSize(10);
			graphicsWindow.textAlign(graphicsWindow.CENTER);
			text = Float.toString(c);
			graphicsWindow.text(text, xmin - HSPACING, i);
			c -= datayinc;
		}
	}

	private void drawYIncrements() {
		float dyinc = (ymin - ymax) / numYIncrements;

		for (float i = ymax; i < ymin; i += dyinc) {
			graphicsWindow.stroke(100);
			graphicsWindow.line(xmin, i, xmin - HSPACING / 2, i);
		}
	}

	public void plotPoint(float x, float y, Object key) {
		if ((x > dataxmax) && (!wrap)) {
			return;
		}

		if (x < dataxmin) {
			return;
		}

		if (y < dataymin) {
			return;
		}

		if (y > dataymax) {
			return;
		}

		if ((x > dataxmax) && (wrap)) {
			// if we're wrapping, re-adjust data range
			cleardata = true;
			float datarange = dataxmax - dataxmin;
			while (x > dataxmax) {
				dataxmin += datarange;
				dataxmax += datarange;
			}
		}

		if (cleardata) {
			clearData();
		}

		if (dataSets.containsKey(key)) {
			ScaledDataSet d = dataSets.get(key);
			d.addPoint(x, y);
		} else {
			ScaledDataSet d = new ScaledDataSet();
			d.setxScaling(dataxmin, xmin, dataxmax, xmax);
			d.setyScaling(dataymin, ymax, dataymax, ymin);
			d.addPoint(x, y);
			dataSets.put(key, d);
			if (!colorMap.containsKey(key)) {
				colorMap.put(key, this.getNextColor());
			}
		}
	}

	// Clear all data sets. Re-create them empty
	// with the current scaling factors.
	private void clearData() {
		ScaledDataSet d;
		Set s = dataSets.keySet();

		dataSets.clear();

		for (Object f : s) {
			d = new ScaledDataSet();
			d.setxScaling(dataxmin, xmin, dataxmax, xmax);
			d.setyScaling(dataymin, ymin, dataymax, ymax);
			dataSets.put(f, d);
		}
		cleardata = false;
	}

	private int getNextColor() {
		int i = defaultColors[nextColor];
		nextColor = (nextColor + 1) % defaultColors.length;
		return i;
	}

	public float xDataToXCoordinate(float xdata) {
		return xdata * xscaleval + this.xshiftval;
	}

	public float xCoordinateToXData(float xcoord) {
		return (xcoord - this.xshiftval) / this.xscaleval;
	}

	public float yDataToYCoordinate(float ydata) {
		return ydata * yscaleval + this.yshiftval;
	}

	public float yCoordinateToYData(float ycoord) {
		return (ycoord - this.yshiftval) / this.yscaleval;
	}

	/**
	 * Define a color to be used for a given class of animal.
	 * 
	 * @param animalClass
	 *          The animal's Class object.
	 * @param color
	 *          The color to be used for the given class.
	 */
	public void setColor(Class animalClass, Integer color) {
		colorMap.put(animalClass, color);
	}

	public void clear() {
		clearData();
	}
}