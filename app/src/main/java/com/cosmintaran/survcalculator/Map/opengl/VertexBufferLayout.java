package com.cosmintaran.survcalculator.Map.opengl;

import android.opengl.GLES30;

import java.util.ArrayList;
import java.util.List;

public class VertexBufferLayout {

    private int m_stride;
    List<VertexBufferElement>m_Elements;

    public VertexBufferLayout ( ) {
        m_Elements = new ArrayList<>();
    }

    public void vPush(int type, int count){
        switch (type){
            case GLES30.GL_FLOAT:
                m_Elements.add(new VertexBufferElement(GLES30.GL_FLOAT,count,false));
                m_stride += count * VertexBufferElement.GetSizeOfType(GLES30.GL_FLOAT);
                break;
            case GLES30.GL_UNSIGNED_INT:
                m_Elements.add(new VertexBufferElement(GLES30.GL_UNSIGNED_INT,count,false));
                m_stride += count * VertexBufferElement.GetSizeOfType(GLES30.GL_UNSIGNED_INT);
                break;
            case GLES30.GL_UNSIGNED_BYTE:
                m_Elements.add(new VertexBufferElement(GLES30.GL_UNSIGNED_BYTE,count,false));
                m_stride += count * VertexBufferElement.GetSizeOfType(GLES30.GL_UNSIGNED_BYTE);
                break;
            default:
                break;
        }
    }


    public int getStride ( ) {
        return m_stride;
    }

    public List<VertexBufferElement>GetElements(){ return m_Elements;}
}

class VertexBufferElement{

    private int type;
    private int count;
    private boolean normalized;

public VertexBufferElement(int type, int count, boolean normalized ){
    this.type = type;
    this.count = count;
    this.normalized = normalized;
}

    static int GetSizeOfType( int type) {

        switch (type)
        {
            case GLES30.GL_FLOAT:         return 4;
            case GLES30.GL_UNSIGNED_INT:  return 4;
            case GLES30.GL_UNSIGNED_BYTE: return 1;
        }

        return 0;
    }

    public int getType ( ) {
        return type;
    }

    public int getCount ( ) {
        return count;
    }

    public boolean getNormalized ( ) {
        return normalized;
    }
}
