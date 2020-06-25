package com.cosmintaran.survcalculator.Map.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MapView extends View {

    private List<DrawablePoint> points = new ArrayList<>();
    private List<DrawableLine> lines = new ArrayList<>();
    private Paint mPaint;
    private Paint selectedPaint;
    private double xMin, yMin, xMax, yMax;

    public MapView(Context context) {
        super(context);
        init();
    }

    public MapView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        selectedPaint = new Paint();
        init();
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        selectedPaint = new Paint();
        init();
    }

    public MapView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        selectedPaint = new Paint();
        init();
    }


    public void addPoint(DrawablePoint point) {
        points.add(point);
    }

    public void addLine(DrawableLine line) {
        lines.add(line);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        CalcBoundaries();
        if(canvas == null) return;
        for (DrawableLine l : lines) {
            if(l == null) continue;
            canvas.drawLine(normalizeX(l.getStartPoint().X), normalizeY(l.getStartPoint().Y), normalizeX(l.getEndPoint().X), normalizeY(l.getEndPoint().Y),
                    l.isSelected == false ? mPaint : selectedPaint);
        }

        for (DrawablePoint p : points) {
            if(p == null) continue;
            //canvas.drawPoint(normalizeX(p.getX()), normalizeY(p.getY()), p.isSelected == false ? mPaint : selectedPaint);

            //canvas.drawPoint(540, 950, mPaint);
            float x = normalizeX(p.getX());
            float y = normalizeY(p.getY());
            Log.println(Log.INFO, "",String.format("x:%f , y:%f",x,y));
            canvas.drawPoint(x, y, mPaint);
        }
    }

    private void init() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(18);
        selectedPaint = new Paint();
        selectedPaint.setColor(Color.RED);
        selectedPaint.setStrokeWidth(16);
        selectedPaint.setAntiAlias(true);
        selectedPaint.setDither(true);
        selectedPaint.setColor(Color.BLACK);
        selectedPaint.setStyle(Paint.Style.STROKE);
        selectedPaint.setStrokeJoin(Paint.Join.ROUND);
        selectedPaint.setStrokeCap(Paint.Cap.ROUND);
        selectedPaint.setStrokeWidth(12);
    }


    private void CalcBoundaries() {
        if(points.size() < 1) return;
        if(points.size() == 1){
            DrawablePoint p = points.get(0);
            double width = getWidth() * 0.5;
            double height = getHeight() * 0.5;

            xMax = p.getX() + width ;
            xMin = p.getX() -  width;

            yMax = p.getY() + height;
            yMin = p.getY() - height;
            return;
        }

        for (DrawablePoint p : points) {
            if (p.getX() > xMax)
                xMax = p.getX();

            if (p.getX() < xMin)
                xMin = p.getX();

            if (p.getY() > yMax)
                yMax = p.getY();

            if (p.getY() < yMin)
                yMin = p.getY();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void zoomExtend(){
        postInvalidate();
    }


    private float normalizeX(double x) {
        double w = getWidth();
        float rez =  (float) ((x - xMin) * this.getWidth() / (xMax - xMin));
        return rez;
    }

    private float normalizeY(double y) {
        float rez = (float) (this.getHeight() - (y - yMin) * getHeight() / (yMax - yMin));
        return rez;
    }
}
