package com.cosmintaran.survcalculator.MapControl.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Shape {
    protected int color;
    protected Paint paint;
    abstract void draw( Canvas canvas);
}
