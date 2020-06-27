package com.cosmintaran.survcalculator.Map.opengl;

import android.opengl.Matrix;

public class Camera {

    private float _locationPosX;
    private float _locationPosY;

    private float _originPosX;
    private float _originPosY;

    private float _zoom;
    private float _rotation;
    private float[] matrixView;
    private boolean _isDirty;


    public Camera( ){

        _locationPosX = 0.0f;
        _locationPosY = 0.0f;
        _originPosX = 0.0f;
        _originPosY = 0.0f;

        _zoom = 1.0f;
        _rotation = 0.0f;
        matrixView = new float[16];

        _isDirty = true;

    }

    public void setLocation(float x, float y){
        _locationPosX = x;
        _locationPosY = y;
        _isDirty = true;
    }

    public void setOrigin(float x, float y){
        _originPosX = x;
        _originPosY = y;
        _isDirty = true;
    }

    public final float[] getMatrixView(){

        if(_isDirty) {
            Matrix.setIdentityM(matrixView , 0);
            Matrix.setLookAtM(matrixView , 0 , 0 , 0 , -3 , 0f , 0f , 0f , 0f , 1.0f , 0.0f);
            _isDirty = false;
        }
          return matrixView;
    }


}
