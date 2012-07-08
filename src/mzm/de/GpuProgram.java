package mzm.de;

import java.io.InputStream;

import mzm.de.interfaces.IGpuProgram;

import android.opengl.GLES20;
import android.util.Log;

public abstract class GpuProgram implements IGpuProgram{
	private Shader vs;
	private Shader fs;
	private int programId;
	
//	public int aPositionHandle;
//	public int aColorHandle;
//	public int aNormalHandle;
//	
//	public int uMvpHandle;
//	public int uNormalMatrixHandle;
//	public int uLightPositionHandle;
//	public int uMvHandle;
	
	public void load(InputStream vi, InputStream fi) throws Exception{		
		vs = new Shader();
		vs.initialize(GLES20.GL_VERTEX_SHADER, vi);
		
		fs = new Shader();
		fs.initialize(GLES20.GL_FRAGMENT_SHADER, fi);
		
		this.initialize(vs, fs);
	}
	
	public void initialize(Shader vs, Shader fs){
		programId = GLES20.glCreateProgram();
		if (programId != 0) {
            GLES20.glAttachShader(programId, vs.getId());
            Globals.checkGlError("glAttachShader");
            
            GLES20.glAttachShader(programId, fs.getId());
            Globals.checkGlError("glAttachShader");
            
            GLES20.glLinkProgram(programId);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(programId, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES20.GL_TRUE) {
            	String message = "Could not link program: "+GLES20.glGetProgramInfoLog(programId);
                Log.v(Globals.TAG, message);
                GLES20.glDeleteProgram(programId);
                programId = 0;
                
                throw new RuntimeException(message);
            }
            
            this.initializeHandles();
        }
	}
	
	abstract protected void initializeHandles();
	
	public int getId(){
		return this.programId;
	}
	
	public int getUniformLocation(String uniformName){
		int location = GLES20.glGetUniformLocation(this.programId, uniformName);
        Globals.checkGlError("glGetUniformLocation "+uniformName);
        if (location == -1) {
    		String message = "Could not get uniform location for "+uniformName;
    		Log.v(Globals.TAG, message);
    		//throw new RuntimeException(message);
    		
        }
        
        Log.v(Globals.TAG, uniformName+" location: "+location);
        
        return location;
	}
	
	public int getAttributeLocation(String attributeName){
		int location = GLES20.glGetAttribLocation(this.programId, attributeName);
		Globals.checkGlError("glGetAttribLocation "+attributeName);
		if (location == -1) {            
            String message = "Could not get attrib location for "+attributeName;
    		Log.v(Globals.TAG, message);
    		//throw new RuntimeException("Could not get attrib location for "+attributeName);
        }
		
		Log.v(Globals.TAG, attributeName+" location: "+location);
		
		return location;
	}

	public void use(){
		GLES20.glUseProgram(this.programId);
		Globals.checkGlError("glUseProgram");
	}
}
