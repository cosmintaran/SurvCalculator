package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLES30;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

class BSText
{
    //Reference to Activity Context
    private final Context mActivityContext;
    private int[] textures = new int[1];
    private  int textsize=16;int textX=12;int textY=116;
    private int textR=255,textG=0,textB=0;

    //Added for Textures
    private final FloatBuffer mCubeTextureCoordinates;
    private int mTextureUniformHandle;
    private int mTextureCoordinateHandle;
    private final int mTextureCoordinateDataSize = 2;
    private int mTextureDataHandle;
    private String texte="!";

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "attribute vec2 a_TexCoordinate;" +
                    "varying vec2 v_TexCoordinate;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "v_TexCoordinate = a_TexCoordinate;"+

                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "uniform sampler2D u_Texture;" +
                    "varying vec2 v_TexCoordinate;" +
                    "void main() {" +
                    "gl_FragColor = (vColor * texture2D(u_Texture, v_TexCoordinate));" +
                    "}";



    private final int shaderProgram;
    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 2;
    static float spriteCoords[] = {
            -0.15f, 0.15f,   // top left
            -0.15f, -0.15f,   // bottom left
            0.15f, -0.15f,   // bottom right
            0.15f,  0.15f }; //top right

    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices
    private final int vertexStride = COORDS_PER_VERTEX *4 ; //Bytes per vertex

    // Set color with red, green, blue and alpha (opacity) values
    // float color[] = { 1f, 1.0f,1f, 0f };
    float color[] = { 1f, 1f,1f, 1.0f };


    public BSText(final Context activityContext, int xx,int yy, int red,int green,int blue ,String tex, int size)
    {

        mActivityContext = activityContext;
        this.textX=xx;this.textY=yy;
        this.textR=red;this.textG=green;this.textB=blue;
        this.texte=tex;
        this.textsize=size;

        //Initialize Vertex Byte Buffer for Shape Coordinates / # of coordinate values * 4 bytes per float
        ByteBuffer bb = ByteBuffer.allocateDirect(spriteCoords.length * 4);
        //Use the Device's Native Byte Order
        bb.order(ByteOrder.nativeOrder());
        //Create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        //Add the coordinates to the FloatBuffer
        vertexBuffer.put(spriteCoords);
        //Set the Buffer to Read the first coordinate
        vertexBuffer.position(0);



        final float[] cubeTextureCoordinateData = {
                0.0f,0.0f, // top left
                0.0f,1.0f, // Top-right
                1.0f,1.0f, // Bottom-right
                1.0f,0.0f // Bottom-left
        };
        mCubeTextureCoordinates = ByteBuffer.allocateDirect(cubeTextureCoordinateData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeTextureCoordinates.put(cubeTextureCoordinateData).position(0);

        //Initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(spriteCoords.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

       // int vertexShader =   MyGLRenderer.loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        //int fragmentShader = MyGLRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);

        shaderProgram = GLES30.glCreateProgram();
        //GLES30.glAttachShader(shaderProgram, vertexShader);
        //GLES30.glAttachShader(shaderProgram, fragmentShader);

        //Texture Code
        GLES30.glBindAttribLocation(shaderProgram, 0, "a_TexCoordinate");
        GLES30.glLinkProgram(shaderProgram);


        //Load the texture
        mTextureDataHandle = loadTexture(mActivityContext);
    }

    public void Draw(float[] mvpMatrix)
    {

        //Add program to OpenGL ES Environment
        GLES30.glUseProgram(shaderProgram);

        //Get handle to vertex shader's vPosition member
        mPositionHandle = GLES30.glGetAttribLocation(shaderProgram, "vPosition");
        //Enable a handle to the triangle vertices
        GLES30.glEnableVertexAttribArray(mPositionHandle);
        //Prepare the triangle coordinate data
        GLES30.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, vertexStride, vertexBuffer);
        //Get Handle to Fragment Shader's vColor member
        mColorHandle = GLES30.glGetUniformLocation(shaderProgram, "vColor");
        //Set the Color for drawing the triangle
        GLES30.glUniform4fv(mColorHandle, 1, color, 0);
        //Set Texture Handles and bind Texture
        mTextureUniformHandle = GLES30.glGetAttribLocation(shaderProgram, "u_Texture");
        mTextureCoordinateHandle = GLES30.glGetAttribLocation(shaderProgram, "a_TexCoordinate");
        //Set the active texture unit to texture unit 0.
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        //Bind the texture to this unit.
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureDataHandle);
        //Tell the texture uniform sampler to use this texture in the shader by binding to texture unit 0.
        GLES30.glUniform1i(mTextureUniformHandle, 0);
        //Pass in the texture coordinate information
        mCubeTextureCoordinates.position(0);
        GLES30.glVertexAttribPointer(mTextureCoordinateHandle, mTextureCoordinateDataSize, GLES30.GL_FLOAT, false, 0, mCubeTextureCoordinates);
        GLES30.glEnableVertexAttribArray(mTextureCoordinateHandle);
        //Get Handle to Shape's Transformation Matrix
        mMVPMatrixHandle = GLES30.glGetUniformLocation(shaderProgram, "uMVPMatrix");
        //Apply the projection and view transformation
        GLES30.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, mvpMatrix, 0);

        // try

        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_CLAMP_TO_EDGE);


        //

        GLES30.glBlendFunc(GLES30.GL_ONE, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        //transpaency du bitmap+text !!!
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA);
        //Draw the triangle
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, drawOrder.length, GLES30.GL_UNSIGNED_SHORT, drawListBuffer);
        //Disable Vertex Array
        GLES30.glDisableVertexAttribArray(mPositionHandle);


    }

    public  int loadTexture(final Context context)
    {
        final int[] textureHandle = new int[1];
        GLES30.glGenTextures(1, textureHandle, 0);
        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling
            options.inMutable=true;
            // Read in the resource
            // final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
            // Create an empty, mutable bitmap
            Bitmap bitmap = Bitmap.createBitmap(256,256, Bitmap.Config.ARGB_8888);
            //   Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.checkerboard_transparent_256x256,options);
            //  bitmap.compress(Bitmap.CompressFormat.PNG, 100, bitmap);
            bitmap.setHasAlpha(true);
            // make transparent
            int [] allpixels = new int [ bitmap.getHeight()*bitmap.getWidth()];
            bitmap.getPixels(allpixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(),bitmap.getHeight());
            bitmap.setPixels(allpixels, 0, 256, 0, 0, 256, 256);

            for(int i =0; i<bitmap.getHeight()*bitmap.getWidth();i++){
                allpixels[i] = Color.alpha(Color.TRANSPARENT);
            }

            bitmap.setPixels(allpixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());

            // get a canvas to paint over the bitmap
            Canvas canvas = new Canvas(bitmap);

            //  bitmap.eraseColor(Color.TRANSPARENT);
            // get a background image from resources
            // note the image format must match the bitmap format
            //   Drawable background = context.getResources().getDrawable(R.drawable.checkerboard_transparent_256x256);
            //   background.setBounds(0, 0, 256, 256);
            //  background.draw(canvas); // draw the background to our bitmap
            // Draw the text
            Paint textPaint = new Paint();
            textPaint.setAlpha(100);
            textPaint.setTextSize(textsize);
            textPaint.setAntiAlias(true);
            textPaint.setARGB(0xff, textR, textG, textB);            // draw the text centered
            canvas.drawText(texte, textX,textY, textPaint);
            // Bind to the texture in OpenGL
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textureHandle[0]);

            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,

                    GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);

            GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,

                    GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
            // Set filtering
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0);
            // GLUtils.texImage2D(GLES20. GL_UNSIGNED_SHORT_5_5_5_1,0, bitmap, 0);
            // GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA,256,256, 0, GLES20.GL_RGBA,GLES20.GL_UNSIGNED_BYTE,textureHandle[0]);
            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();


        }
        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }
        return textureHandle[0];
    }

    void setCoords(float a1,float a2, float b1, float b2, float c1, float c2, float d1, float d2)
    {
        spriteCoords[0]=a1;spriteCoords[1]=a2;
        spriteCoords[2]=b1;spriteCoords[3]=b2;
        spriteCoords[4]=c1;spriteCoords[5]=c2;
        spriteCoords[6]=d1;spriteCoords[7]=d2;
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                spriteCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer.put(spriteCoords);
        vertexBuffer.position(0);
        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }
    public void setTextsize(int s)
    {
        this.textsize=s;
    }
    public void setTextColor(int red,int green, int blue)
    {
        this.textR=red;
        this.textG=green;
        this.textB=blue;
    }
    public void setTextXY(int xx,int yy)
    {
        this.textX=xx;
        this.textY=yy;
    }
    public void setText(String s)
    {
        this.texte=s;
    }
}
