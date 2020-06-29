package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.opengl.GLES30;
import com.cosmintaran.survcalculator.Map.helperClasses.Line2D;
import com.cosmintaran.survcalculator.Map.helperClasses.SrvPoint2D;


public class Line extends Entity {

    private Line2D _line2D;

    public Line (Context c, SrvPoint2D startPoint , SrvPoint2D endPoint) {
        _line2D = new Line2D(startPoint , endPoint);
        vertexCoords = new float[]{
                (float) startPoint.X , (float) startPoint.Y , 0 ,
                (float) endPoint.X , (float) endPoint.Y , 0
        };

        init(c);
        GLES30.glLineWidth(2);
    }

    @Override
    public void draw ( float[] u_MVP) {
        shader.vBind();
        shader.vSetUniformMat4f("u_MVP" ,u_MVP );
        shader.vSetUniform4f("u_Color" , color[0] , color[1] , color[2] , color[3]);
        int posHandler = shader.iGetAttributeLocation("vPosition");
        GLES30.glEnableVertexAttribArray(posHandler);
        GLES30.glVertexAttribPointer(posHandler , COORDS_PER_VERTEX ,
                GLES30.GL_FLOAT , false ,
                vertexStride , vertexBuffer);
        GLES30.glDrawArrays(GLES30.GL_LINES, 0, vertexCount);

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(posHandler);
    }

    public void setColor ( float[] color ) {
        this.color = color;
    }

    public void setLineWidth ( int width ) {
        GLES30.glLineWidth(width);
    }
}
