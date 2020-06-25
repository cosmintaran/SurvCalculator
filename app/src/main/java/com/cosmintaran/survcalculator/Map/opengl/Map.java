package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;
import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class Map extends GLSurfaceView  implements GLSurfaceView.Renderer {

    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private List<IDrawable> entities;
    private Context mContext;


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
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void onSurfaceCreated ( GL10 unused , EGLConfig config ) {
        ClearColor();
        entities.add(new Line(mContext,new SrvPoint2D(-0.9f,-0.9f), new SrvPoint2D(0.9f, 0.9f)));
        entities.add(new Circle(mContext,new SrvPoint2D(-0.5,-0.1),0.05f));
        entities.add(new Circle(mContext,new SrvPoint2D(0f,0f),0.01f));
    }

    @Override
    public void onSurfaceChanged ( GL10 unused , int width , int height ) {
        GLES30.glViewport(0 , 0 , width , height);
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    @Override
    public void onDrawFrame ( GL10 unused ) {
        ClearColor();
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        if (entities.size() <= 0) return;

        for (IDrawable entity : entities) {
            if (entity == null) continue;
            entity.draw(vPMatrix);
        }

    }

    public void ClearColor ( ) {
        GLES30.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        GLES30.glClearColor(0.596078431372549019607843137255f,0.603921568627450980392156862745f,0.6196078431372549019607843137255f,1.0f);
    }
}
