package Field;

import java.util.LinkedHashMap;
import java.util.Map;

import Simulator.Simulator;
import processing.core.*;

public class FieldDisplay {
    // Colors used for empty locations.
    private static final int EMPTY_COLOR = 0xFFFFFFFF;

    // Color used for objects that have no defined color.
    private static final int UNKNOWN_COLOR = 0x66666666;
    private static final int DEFAULT_EDGE_BUFFER = 20;

    private PApplet p;  // the applet we want to display on
    private Field f;    // the field object we'll be displaying
    private int x, y, w, h; // (x, y) of upper left corner of display
    // the width and height of the display
    private float dx, dy;  // calculate the width and height of each box
    // in the field display using the size of the field
    // and the width and height of the display

    // A map for storing colors for participants in the simulation
    private Map<Class, Integer> colors;

    public FieldDisplay(PApplet p, Simulator s) {
        this(p, s.getField());
    }

    public FieldDisplay(PApplet p, Field f) {
        this(p, f, DEFAULT_EDGE_BUFFER, DEFAULT_EDGE_BUFFER, p.width - 2*DEFAULT_EDGE_BUFFER, p.height/2 - 2* DEFAULT_EDGE_BUFFER);
    }

    public FieldDisplay(PApplet p, Field f, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.p = p;
        this.f = f;

        this.dx = (float)w / f.getWidth();
        this.dy = (float)h / f.getHeight();

        colors = new LinkedHashMap<Class, Integer>();
    }

    public void drawField(Field field) {
        Object animal;
        Integer animalColor;
        for (int col = 0; col < field.getWidth(); col++) {
            for (int row = 0; row < field.getHeight(); row++) {
                animal = field.getObjectAt(row, col);
                if (animal != null) {
                    animalColor = getColor(animal.getClass());
                    p.fill(animalColor);

                } else {
                    p.fill(this.EMPTY_COLOR);
                }
                p.rect(x + col * dx, y + row * dy, dx, dy);
            }
        }
    }

    /**
     * Define a color to be used for a given class of animal.
     * @param animalClass The animal's Class object.
     * @param color The color to be used for the given class.
     */
    public void setColor(Class animalClass, Integer color) {
        colors.put(animalClass, color);
    }

    /**
     * @return The color to be used for a given class of animal.
     */
    private Integer getColor(Class animalClass) {
        Integer col = colors.get(animalClass);
        if (col == null) {  // no color defined for this class
            return UNKNOWN_COLOR;
        } else {
            return col;
        }
    }

    public Location gridLocationAt(float mx, float my) {
        if (mx > x && mx < x + w && my > y && my < y+h) {
            return new Location((int)Math.floor((my-y)/dy), (int)Math.floor((mx-x)/dx));
        } else return null;
    }

    public int getBottomEdge() {
        return y + h;
    }

    public int getTopEdge() {
        return y;
    }

    public int getLeftEdge() {
        return x;
    }

    public int getRightEdge() {
        return x+w;
    }
}