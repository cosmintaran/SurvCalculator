package com.cosmintaran.survcalculator.MapControl.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;

public class PointShape extends Shape {

    private SrvPoint2D point;
    private static Paint _textPaint;
    private static Paint _selectedTextPaint;
    private static final int TEXT_HEIGHT = 10;
    private static final int POINT_STROKE = 8;

    public PointShape ( SceneFrame context, double x , double y , double z ) {
        super(context);
        init(x , y , z);
    }

    private void init ( double x , double y , double z ) {
        point = new SrvPoint2D(x , y);
        point.Z = z;

        if (paint == null) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(_context.dp2dx(POINT_STROKE));
        }


        if(selectedPaint == null){
            selectedPaint = new Paint();
            selectedPaint.setAntiAlias(true);
            selectedPaint.setDither(true);
            selectedPaint.setColor(Color.RED);
            selectedPaint.setStyle(Paint.Style.STROKE);
            selectedPaint.setStrokeJoin(Paint.Join.ROUND);
            selectedPaint.setStrokeCap(Paint.Cap.ROUND);
            selectedPaint.setStrokeWidth(_context.dp2dx(POINT_STROKE));
        }

        if (_textPaint == null) {
            _textPaint = new Paint();
            _textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            _textPaint.setColor(Color.BLACK);
            _textPaint.setTextSize(_context.dp2dx(TEXT_HEIGHT));
        }

        if (_selectedTextPaint == null) {
            _selectedTextPaint = new Paint();
            _selectedTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            _selectedTextPaint.setColor(Color.RED);
            _selectedTextPaint.setTextSize(_context.dp2dx(TEXT_HEIGHT));
        }

        float radius = (float) (_context.dp2dx(POINT_STROKE )* 0.5);
        boundingBox = new RectF((float) (x - radius) ,
                (float) (y - radius) ,
                (float) (radius + x) ,
                (float) (radius + y));

    }

    @Override
    void draw ( Canvas canvas ) {
        canvas.drawText("Test" , (float) point.X + 10 , (float) point.Y , _isSelected? _selectedTextPaint: _textPaint);
        canvas.drawPoint((float) point.X , (float) point.Y , _isSelected? selectedPaint: paint);
    }
}
