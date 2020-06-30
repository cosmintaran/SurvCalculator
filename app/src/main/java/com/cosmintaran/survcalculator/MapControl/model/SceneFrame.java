package com.cosmintaran.survcalculator.MapControl.model;

import android.graphics.Canvas;
import java.util.ArrayList;

public class SceneFrame {

    ArrayList<Shape> shapes = new ArrayList<Shape>();

    public SceneFrame(){
        shapes.add(new PointShape(150 , 235 , 0));
        shapes.add(new PointShape(250 , 150 , 0));
        shapes.add(new PointShape(450 , 330 , 0));
        shapes.add(new PointShape(850 , 50 , 0));

    }

    public synchronized void drawOn( Canvas canvas) {
        canvas.drawARGB(255,156,155,152);
        for (Shape shape : shapes)
            shape.draw(canvas);
    }

    void clear() {
        shapes.clear();
    }
}
