package com.cosmintaran.survcalculator.Map.opengl;

import android.opengl.GLES30;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VertexBuffer {

    private IntBuffer mRenderId;

    public VertexBuffer( float[] data, int size ){
        mRenderId = IntBuffer.allocate(1);
        GLES30.glGenBuffers(1,mRenderId);
        ByteBuffer dlb = ByteBuffer.allocateDirect(size);
        dlb.order(ByteOrder.nativeOrder());
        FloatBuffer buff = dlb.asFloatBuffer();
        buff.put(data);
        buff.position(0);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mRenderId.get(0));
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, size, buff,GLES30.GL_STATIC_DRAW);
    }

    public void vBind(){
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mRenderId.get(0));
    }

    public void vUnbind(){
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
    }
}
