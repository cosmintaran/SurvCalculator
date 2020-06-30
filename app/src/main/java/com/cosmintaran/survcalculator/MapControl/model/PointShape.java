package com.cosmintaran.survcalculator.MapControl.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;

public class PointShape extends Shape {

    private SrvPoint2D point;
    private Paint _textPaint;
    public PointShape (double x, double y, double z ){
        point = new SrvPoint2D(x,y);
        point.Z = z;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(20);

        _textPaint = new Paint();
        _textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        _textPaint.setColor(Color.BLACK);
        _textPaint.setTextSize(30);
    }

    @Override
    void draw ( Canvas canvas ) {
        canvas.drawText("Test",(float) point.X + 10,(float) point.Y,_textPaint);
        canvas.drawPoint((float) point.X, (float) point.Y, paint);
    }
}
