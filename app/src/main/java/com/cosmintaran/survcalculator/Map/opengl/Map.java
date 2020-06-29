package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import android.opengl.Matrix;
import android.renderscript.Matrix4f;
import android.util.AttributeSet;

import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Map extends GLSurfaceView implements GLSurfaceView.Renderer {

    Matrix4f transf1Matrix4f;
    Matrix4f scale;
    Matrix4f orto;
    private List<IDrawable> entities;
    private Context mContext;
    private Camera _camera;
    private float xMax, yMax, xMin, yMin;

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
        _camera = new Camera();

        xMax = 455500f;
        yMax = 236100f;
        xMin = 454500f;
        yMin = 235200f;

        transf1Matrix4f = new Matrix4f();
        scale = new Matrix4f();
        orto = new Matrix4f();
    }

    @Override
    public void onSurfaceCreated ( GL10 unused , EGLConfig config ) {
        ClearColor();
        float xc = xMin + (xMax - xMin) / 2;
        float yc = yMin + (yMax - yMin) / 2;
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
        float xc = xMin + (xMax - xMin) * 0.5f;
        float yc = yMin + (yMax - yMin) *0.5f;

        transf1Matrix4f.loadTranslate(- xc , - yc , 0);
        //scale.loadScale((width / (xMax - xMin)) , (width / (xMax - xMin)) , 0.0f);
        scale.loadScale(0.25f , 0.25f , 1.0f);
        orto.loadOrtho(-width*1.5f , width*1.5f , -height*1.5f , height*1.5f , -1 , 1);
        //transf1Matrix4f.multiply(scale);
        orto.multiply(transf1Matrix4f);
    }

    @Override
    public void onDrawFrame ( GL10 unused ) {
        ClearColor();
        if (entities.size() <= 0) return;

        for (IDrawable entity : entities) {
            if (entity == null) continue;
            entity.draw(orto.getArray());
        }
    }

    public void ClearColor ( ) {
        GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        GLES30.glClearColor(0.596078431372549019607843137255f , 0.603921568627450980392156862745f , 0.6196078431372549019607843137255f , 1.0f);
    }



    private void CalcBoundaries ( ) {
        /*if(points.size() < 1) return;
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
        }*/
    }

    private float normalizeX ( double x ) {
        double w = getWidth();
        float rez = (float) ((x - xMin) * this.getWidth() / (xMax - xMin));
        return rez;
    }

    private float normalizeY ( double y ) {
        float rez = (float) (/*this.getHeight() - */(y - yMin) * getHeight() / (yMax - yMin));
        return rez;
    }

}
