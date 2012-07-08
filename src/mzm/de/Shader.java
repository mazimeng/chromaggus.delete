package mzm.de;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.opengl.GLES20;
import android.util.Log;

public class Shader {
	
	private int shaderType;
	private int shaderId;	
	
	public Shader(){
		
	}
	
	public int getId(){
		return this.shaderId;
	}
	
	public void initialize(int shaderType, String sourceCode){
		int shader = GLES20.glCreateShader(shaderType);
		
		if (shader != 0) {
            GLES20.glShaderSource(shader, sourceCode);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0) {
            	String message = "Could not compile shader " + shaderType + " : ["+GLES20.glGetShaderInfoLog(shader)+"]";
                Log.v(Globals.TAG, message);

                GLES20.glDeleteShader(shader);
                shader = 0;
                
                throw new RuntimeException(message);
            }
        }

		this.shaderId = shader;
	}
	
	public void initialize(int shaderType, InputStream sourceCode) throws Exception{
		//InputStream is = this.context.getAssets().open("vertexShader");
		InputStreamReader isr = new  InputStreamReader(sourceCode, "UTF-8");
		StringBuilder sb = new StringBuilder();

		int bufferSize = 1024;
		char[] buffer = new char[bufferSize];
		
		int read = 0;
		while((read = isr.read(buffer))>0){
			sb.append(buffer, 0, read);
		}
		
		String vertexShaderSource = sb.toString();
		
		this.initialize(shaderType, vertexShaderSource);
	}
	
	public void delete(){
		GLES20.glDeleteShader(this.shaderId);
	}
}
