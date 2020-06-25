package com.cosmintaran.survcalculator.Map.opengl;

import android.content.Context;
import android.opengl.GLES30;
import com.cosmintaran.survcalculator.R;
import java.util.HashMap;

public class Shader {

    private int mProgram;
    private final Context _context;
    private HashMap<String, Integer> mLocationsBuff;

    public Shader( Context context){
        _context = context;
        mLocationsBuff = new HashMap<>();
        mProgram = iCreateProgram(context);
        vBind();
    }


    public void vBind(){
        GLES30.glUseProgram(mProgram);
    }

    public void vUnbind(){
        GLES30.glUseProgram(0);
    }

    public void vSetUniform4f(String uName, float v0, float v1, float v2, float v3){
        GLES30.glUniform4f(iGetUniformLocation(uName),v0,v1,v2,v3);
    }

    public void vSetUniform1i(String uName, int val){
        GLES30.glUniform1ui(iGetUniformLocation(uName), val);
    }

    public void vSetUniformMat4f(String uName, float[] matrix)
    {
        GLES30.glUniformMatrix4fv(iGetUniformLocation(uName),1,false,matrix,0);
    }


    private static int iCreateProgram(Context c){
        int program = GLES30.glCreateProgram();
        int vertexShader = ShaderUtils.loadShader(GLES30.GL_VERTEX_SHADER, ShaderUtils.readShaderFileFromRawResource(c, R.raw.line_vertex_shader));
        int fragmentShader = ShaderUtils.loadShader(GLES30.GL_FRAGMENT_SHADER, ShaderUtils.readShaderFileFromRawResource(c, R.raw.line_pixel_shader));

        GLES30.glAttachShader(program, vertexShader);
        GLES30.glAttachShader(program, fragmentShader);
        GLES30.glLinkProgram(program);
        GLES30.glValidateProgram(program);
        final int[] validateStatus = new int[1];
        GLES30.glGetProgramiv(program, GLES30.GL_VALIDATE_STATUS, validateStatus, 0);
        if(validateStatus[0] == 0){
            GLES30.glDeleteProgram(program);
        }

        GLES30.glDeleteShader(vertexShader);
        GLES30.glDeleteShader(fragmentShader);

        return program;
    }

    private int iGetUniformLocation ( String uName){
        if(mLocationsBuff.containsKey(uName))
            return mLocationsBuff.get(uName);

        int location = GLES30.glGetUniformLocation(mProgram, uName);
        if(location < 0) return location;
        mLocationsBuff.put(uName, location);
        return location;
    }

    public int iGetAttributeLocation(String aName){
        int location = GLES30.glGetAttribLocation(mProgram, aName);
        if(location < 0) return location;
        mLocationsBuff.put(aName, location);
        return location;
    }
}
