package com.cosmintaran.survcalculator.MapControl.model;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.cosmintaran.survcalculator.MapControl.view.MapSurfaceView;

import java.util.ArrayList;

public class SceneFrame {

    ArrayList<Shape> shapes = new ArrayList<Shape>();
    private final MapSurfaceView _context;

    public SceneFrame( MapSurfaceView context ){
        _context = context;
        shapes.add(new PointShape(this,150 , 235 , 0));
        shapes.add(new PointShape(this,250 , 150 , 0));
        shapes.add(new PointShape(this,450 , 330 , 0));
        shapes.add(new PointShape(this,850 , 50  , 0));

    }

    public synchronized void drawOn( Canvas canvas) {
        for (Shape shape : shapes)
            if(!canvas.quickReject(shape.getBoundingBox(), Canvas.EdgeType.AA))
                shape.draw(canvas);
    }

    public RectF selectAt(float x, float y, float tolerance){
        for (Shape shape : shapes){

            if(x > shape.boundingBox.left - tolerance
                    && x < shape.boundingBox.right + tolerance){
                if(y > shape.boundingBox.top - tolerance && y < shape.boundingBox.bottom + tolerance){
                    shape._isSelected = !shape._isSelected;
                    return shape.boundingBox;
                }
            }
        }
        return null;
    }

    void clear() {
        shapes.clear();
    }

    public float dp2dx(int val){
        return  _context.dp2px(val);
    }
}
