package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.renderscript.Matrix4f;
import android.util.AttributeSet;

import com.cosmintaran.survcalculator.Map.drawable.DrawablePoint;
import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Map extends GLSurfaceView  implements GLSurfaceView.Renderer {

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
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

    private void init(Context c) {
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);
        GLES30.glEnable(GLES30.GL_LINE_WIDTH);
        setRenderer(this);
        entities = new ArrayList<>();
        mContext = c;
        _camera = new Camera();

        xMax = 455000f;
        yMax = 235300f;
        xMin = 452000f;
        yMin = 235100f;
    }

    @Override
    public void onSurfaceCreated ( GL10 unused , EGLConfig config ) {
        ClearColor();
        //entities.add(new Line(mContext,new SrvPoint2D(200,500), new SrvPoint2D(450, 650)));
       // entities.add(new Circle(mContext,new SrvPoint2D(200,500),5f));
        entities.add(new Circle(mContext,new SrvPoint2D(720f,1092f),20f));
        entities.add(new Triangle(mContext));
        Matrix.setIdentityM(modelMatrix,0);
        Matrix4f m4f = new Matrix4f();
        m4f.loadTranslate(-(xMin + (xMax - xMin)), -(yMin + (yMax - yMin)),0);
        modelMatrix = m4f.getArray();
    }

    @Override
    public void onSurfaceChanged ( GL10 unused , int width , int height ) {

        GLES30.glViewport(0 , 0 , width , height);
        Matrix.setIdentityM(projectionMatrix,0);

        Matrix.orthoM(projectionMatrix, 0, 0, width ,  0 ,  height, -1, 1);

    }

    @Override
    public void onDrawFrame ( GL10 unused ) {
        ClearColor();

        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, _camera.getMatrixView(), 0);

        if (entities.size() <= 0) return;

        for (IDrawable entity : entities) {
            if (entity == null) continue;
            entity.draw(projectionMatrix);
        }
    }

    public void ClearColor ( ) {
        GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        GLES30.glClearColor(0.596078431372549019607843137255f,0.603921568627450980392156862745f,0.6196078431372549019607843137255f,1.0f);
    }

    private void CalcBoundaries() {
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

    private float normalizeX(double x) {
        double w = getWidth();
        float rez =  (float) ((x - xMin) * this.getWidth() / (xMax - xMin));
        return rez;
    }

    private float normalizeY(double y) {
        float rez = (float) (/*this.getHeight() - */(y - yMin) * getHeight() / (yMax - yMin));
        return rez;
    }

}
