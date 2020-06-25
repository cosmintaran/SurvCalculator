package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Entity implements IDrawable {

    protected FloatBuffer vertexBuffer;
    protected static final int COORDS_PER_VERTEX = 2;
    protected final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex
    protected int vertexCount;

    protected float[] vertexCoords;
    protected float[] color = {1.0f , 0.0f , 0.0f , 1.0f};
    protected Shader shader;

    protected Entity ( ) {

    }

    protected void init ( Context c ) {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                vertexCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(vertexCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
        shader = new Shader(c);
        vertexCount = vertexCoords.length / COORDS_PER_VERTEX;
    }


    @Override
    public void draw ( float[] u_MVP ) {


    }
}
