package com.cosmintaran.survcalculator.MapControl.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public abstract class Shape {

    protected int color;
    protected static Paint paint;
    protected static Paint selectedPaint;
    protected RectF boundingBox;
    protected boolean _isSelected;
    protected final SceneFrame _context;

    public RectF getBoundingBox(){ return boundingBox;}

    abstract void draw( Canvas canvas);

    public Shape(SceneFrame context){
        _context = context;
    }
}
