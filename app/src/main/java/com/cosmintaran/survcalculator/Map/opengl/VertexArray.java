package com.cosmintaran.survcalculator.Map.opengl;

import android.opengl.GLES30;
import java.nio.IntBuffer;

public class VertexArray {
    private IntBuffer mRenderId;

    public VertexArray(){
        mRenderId = IntBuffer.allocate(1);
        GLES30.glGenVertexArrays(1,mRenderId);
    }

    public void vBind(){
        GLES30.glBindVertexArray(mRenderId.get(0));
    }

    public void vUnbind(){
        GLES30.glBindVertexArray(0);
    }

    public void vAddBuffer(VertexBuffer vb, VertexBufferLayout layout){
        vb.vBind();
        vBind();

        int offset = 0;
        int nrOfElements = layout.GetElements().size();
        for(int i = 0; i < nrOfElements; ++i){
            GLES30.glEnableVertexAttribArray(i);
            VertexBufferElement element = layout.GetElements().get(i);
            GLES30.glVertexAttribPointer(i,element.getCount(), element.getType(), element.getNormalized(),
                    layout.getStride(), offset);

            offset += element.getCount() * VertexBufferElement.GetSizeOfType(element.getType());
        }
    }
}
