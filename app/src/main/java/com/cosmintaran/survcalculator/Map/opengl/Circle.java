package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.opengl.GLES30;

import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;


public class Circle extends Entity {

    private SrvPoint2D _center;
    private float _radius;
    private static final short NR_OF_EDGES = 10;

    public Circle ( Context c , SrvPoint2D center , float radius ) {
        _center = center;
        _radius = radius;
        vertexCoords = new float[(NR_OF_EDGES + 2) * 3];
        CalculateFan();
        init(c);
    }


    private void CalculateFan ( ) {

        int nrOfPoints = NR_OF_EDGES + 2;
        double angle = (Math.PI * 2) / NR_OF_EDGES;
        vertexCoords[0] = (float) _center.X;
        vertexCoords[1] = (float) _center.Y;
        vertexCoords[3] = 0.0f;

        for (int i = 1; i < nrOfPoints; ++i) {
            vertexCoords[i * 3] = (float) (_center.X + (_radius * Math.cos(i * angle)));
            vertexCoords[(i * 3) + 1] = (float) (_center.Y + (_radius * Math.sin(i * angle)));
            vertexCoords[(i*3) + 2] = 0.0f;
        }
    }

    @Override
    public void draw ( float[] u_MVP ) {

        shader.vBind();
        shader.vSetUniformMat4f("u_MVP" ,u_MVP );
        shader.vSetUniform4f("u_Color" , color[0] , color[1] , color[2] , color[3]);
        int posHandler = shader.iGetAttributeLocation("vPosition");

        GLES30.glEnableVertexAttribArray(posHandler);
        GLES30.glVertexAttribPointer(posHandler , COORDS_PER_VERTEX ,
                GLES30.GL_FLOAT , false ,
                vertexStride , vertexBuffer);


        GLES30.glDrawArrays(GLES30.GL_TRIANGLE_FAN, 0, NR_OF_EDGES + 2);

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(posHandler);
    }
}
