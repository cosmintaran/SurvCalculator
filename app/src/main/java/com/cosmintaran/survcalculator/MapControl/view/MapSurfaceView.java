package com.cosmintaran.survcalculator.MapControl.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import com.cosmintaran.survcalculator.MapControl.model.SceneFrame;

public class MapSurfaceView extends View {

    private SceneFrame sceneFrame;
    private float _scaleFactor = 1.0f;
    private float _scalePosX, _scalePosY;
    private float _translateX = 0.0f;
    private float _translateY = 0.0f;

    public MapSurfaceView ( Context context ) {
        super(context);
        init();
    }

    public MapSurfaceView ( Context context , AttributeSet attrs ) {
        super(context , attrs);
        init();
    }

    public MapSurfaceView ( Context context , AttributeSet attrs , int defStyleAttr ) {
        super(context , attrs , defStyleAttr);
        init();
    }

    public MapSurfaceView ( Context context , AttributeSet attrs , int defStyleAttr , int defStyleRes ) {
        super(context , attrs , defStyleAttr , defStyleRes);
        init();
    }

    private void init ( ) {
        sceneFrame = new SceneFrame();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw( Canvas canvas ){
        super.onDraw(canvas);
        canvas.scale(_scaleFactor,_scaleFactor, _scalePosX, _scalePosY);
        canvas.translate(_translateX, _translateY);
        //canvas.setMatrix();
        sceneFrame.drawOn(canvas);
    }

    public void scale ( float scaleFactor,float mLastTouchX, float mLastTouchY ) {
        _scaleFactor = scaleFactor;
        _scalePosX = mLastTouchX;
        _scalePosY = mLastTouchY;
        postInvalidate();
    }

    public void panTo ( float dx , float dy ) {
        _translateX+=dx;
        _translateY+=dy;
        postInvalidate();
    }
}
