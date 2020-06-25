package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.opengl.GLES20;

import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;
import com.cosmintaran.survcalculator.R;


public class Point extends Entity {

    private  SrvPoint2D _point;

    public Point( Context context, SrvPoint2D point2D ){
        /*_point = point2D;

        vertexCoords = new float[]{
                (float) point2D.X, (float) point2D.Y, 0
        };

        Init();

        int vertexShader = ShaderUtils.loadShader(GLES20.GL_VERTEX_SHADER, ShaderUtils.readShaderFileFromRawResource(context, R.raw.point_vertex_shader));
        int fragmentShader = ShaderUtils.loadShader(GLES20.GL_FRAGMENT_SHADER, ShaderUtils.readShaderFileFromRawResource(context, R.raw.point_pixel_shader));
        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);*/
    }


    @Override
    public void draw ( float[] m_MVP) {


       /* mPositionHandle = GLES20.glGetAttribLocation(mProgram , "vPosition");
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle , COORDS_PER_VERTEX , GLES20.GL_FLOAT , false ,
                vertexStride , vertexBuffer);
        mColorHandle = GLES20.glGetUniformLocation(mProgram , "vColor");
        GLES20.glUniform4fv(mColorHandle , 1 , color , 0);
        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram , "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mMVPMatrixHandle , 1 , false , mvpMatrix , 0);

        GLES20.glUseProgram(mProgram);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisable(mColorHandle);*/
    }
}
