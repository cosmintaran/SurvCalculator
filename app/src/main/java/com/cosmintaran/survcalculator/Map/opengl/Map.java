package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import android.renderscript.Matrix4f;
import android.util.AttributeSet;

import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Map extends GLSurfaceView implements GLSurfaceView.Renderer {

    private Matrix4f viewCameraMatrix;
    private Matrix4f ortographicProjectionMatrix;
    private List<IDrawable> entities;
    private Context mContext;
    private float xMax, yMax, xMin, yMin;
    private float _scaleFactor;
    private float _translateX;
    private float _translateY;

    public Map ( Context context ) {
        super(context);
        init(context);
    }

    public Map ( Context context , AttributeSet attrs ) {
        super(context , attrs);
        init(context);
    }

    private void init ( Context c ) {
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        GLES30.glEnable(GLES30.GL_LINE_WIDTH);
        setRenderer(this);
        entities = new ArrayList<>();
        mContext = c;

        xMax = 455500f;
        yMax = 236100f;
        xMin = 454500f;
        yMin = 235200f;
        _scaleFactor = 1.0f;
        _translateX = xMin + (xMax - xMin) * 0.5f;
        _translateY = yMin + (yMax - yMin) *0.5f;
        viewCameraMatrix = new Matrix4f();
        ortographicProjectionMatrix = new Matrix4f();
    }

    @Override
    public void onSurfaceCreated ( GL10 unused , EGLConfig config ) {
        ClearColor();
        float xc = xMin + (xMax - xMin) / 2;
        float yc = yMin + (yMax - yMin) / 2;

        //fit to the display at startup zoom extended
        if(getWidth() < getHeight()){
            _scaleFactor = (getWidth()/(xMax - xMin)) * 1.9f;
        }
        else {
            _scaleFactor = (getHeight()/(yMax - yMin)) * 1.9f;
        }

        entities.add(new Circle(mContext , new SrvPoint2D(xc , yc) , 10f));

        entities.add(new Circle(mContext , new SrvPoint2D(454500 , 235200) , 10f));
        entities.add(new Circle(mContext , new SrvPoint2D(455500 , 236100) , 10f));
        entities.add(new Circle(mContext , new SrvPoint2D(454500 , 236100) , 10f));
        entities.add(new Circle(mContext , new SrvPoint2D(455500 , 235200) , 10f));


        entities.add(new Line(mContext , new SrvPoint2D(454500 , 235200) , new SrvPoint2D(454500 , 236100)));
        entities.add(new Line(mContext , new SrvPoint2D(454500 , 236100) , new SrvPoint2D(455500 , 236100)));
        entities.add(new Line(mContext , new SrvPoint2D(455500 , 236100) , new SrvPoint2D(455500 , 235200)));
        entities.add(new Line(mContext , new SrvPoint2D(455500 , 235200) , new SrvPoint2D(454500 , 235200)));
        entities.add(new Triangle(mContext));
    }

    @Override
    public void onSurfaceChanged ( GL10 unused , int width , int height ) {
        GLES30.glViewport(0 , 0 , width , height);
        updateMatrixProjection();
    }

    private void updateMatrixProjection ( ) {
        viewCameraMatrix.loadTranslate(- _translateX , - _translateY , 0);
        ortographicProjectionMatrix.loadOrtho(-getWidth() / _scaleFactor , getWidth() / _scaleFactor , -getHeight() /_scaleFactor , getHeight() /_scaleFactor , -1 , 1);
        ortographicProjectionMatrix.multiply(viewCameraMatrix);
    }

    @Override
    public void onDrawFrame ( GL10 unused ) {
        ClearColor();
        if (entities.size() <= 0) return;

        for (IDrawable entity : entities) {
            if (entity == null) continue;
            entity.draw(ortographicProjectionMatrix.getArray());
        }
    }

    public void ClearColor ( ) {
        GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        GLES30.glClearColor(0.596078431372549019607843137255f , 0.603921568627450980392156862745f , 0.6196078431372549019607843137255f , 1.0f);
    }

    public void panTo ( float mPosX , float mPosY ) {
        _translateX -= mPosX * 1.2f;
        _translateY += mPosY * 1.2f;
        updateMatrixProjection();
    }

    public void scale ( float scaleFactor ) {
        _scaleFactor = scaleFactor;
        updateMatrixProjection();
    }
}
