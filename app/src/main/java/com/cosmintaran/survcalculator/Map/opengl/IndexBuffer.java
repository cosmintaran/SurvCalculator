package com.cosmintaran.survcalculator.Map.opengl;

import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class IndexBuffer {


    private IntBuffer mRenderId;
    private int m_count;

    public  IndexBuffer(short[] data){
        mRenderId = IntBuffer.allocate(1);
        ByteBuffer dlb = ByteBuffer.allocateDirect(data.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        ShortBuffer vertexBuffer = dlb.asShortBuffer();
        vertexBuffer.put(data);
        vertexBuffer.position(0);

        GLES30.glGenBuffers(1, mRenderId);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mRenderId.get(0));
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, data.length * 2, vertexBuffer, GLES30.GL_STATIC_DRAW);
        m_count = data.length;
    }

    public int getCount(){ return m_count;}

    public void vBind(){
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mRenderId.get(0));
    }

    public void vUnbind(){
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

}
