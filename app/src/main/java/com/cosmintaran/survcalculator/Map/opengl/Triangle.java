package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.opengl.GLES30;

public class Triangle extends Entity {


    // Set color with red, green, blue and alpha (opacity) values
    //loat color[] = {0.63671875f, 0.76953125f, 0.22265625f, 1.0f};


    public Triangle( Context c) {
        vertexCoords = new float[]{
                720, 1092f, // top
                360f, 500,  // bottom left
                1080f, 500f  // bottom right
        };

        init(c);
        color[0] = 0.63671875f;
        color[1] = 0.76953125f;
        color[2] = 0.22265625f;
        color[3] = 1.0f;
    }

    public void draw(float[] u_MVP) {

        shader.vBind();
        shader.vSetUniformMat4f("u_MVP" ,u_MVP );
        shader.vSetUniform4f("u_Color" , color[0] , color[1] , color[2] , color[3]);
        int posHandler = shader.iGetAttributeLocation("vPosition");

        GLES30.glEnableVertexAttribArray(posHandler);
        GLES30.glVertexAttribPointer(posHandler , COORDS_PER_VERTEX ,
                GLES30.GL_FLOAT , false ,
                vertexStride , vertexBuffer);


        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount);
        // Disable vertex array
        GLES30.glDisableVertexAttribArray(posHandler);
    }
}
