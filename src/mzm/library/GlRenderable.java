package mzm.library;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import mzm.de.Globals;
import mzm.de.GpuProgramBasic;
import mzm.de.interfaces.IRenderable;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class GlRenderable {
	private FloatBuffer positionBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer colorBuffer;
	private FloatBuffer textureCoordinateBuffer;
	
	
	private int primitiveMode=GLES20.GL_TRIANGLES;

	private int vertexCount = 0;	
	private float[] positionData;
	
	public int numberOfFloatsPerVertex = 3;
	public int numberOfFloatsPerColor = 3;
	
	public void GlRenderable(){
	}
	
	public void setPrimitiveMode(int mode){
		this.primitiveMode = mode;
	}
	
	public void bufferPosition(float[] positionData, int offset, int count){
		this.positionBuffer = ByteBuffer.allocateDirect(count * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.positionBuffer.put(positionData, offset, count).position(0);
		
		this.vertexCount = count/numberOfFloatsPerVertex;
		
		this.positionData = new float[count];
		System.arraycopy(positionData, offset, this.positionData, 0, count);
	}
	
	public void bufferNormal(float[] normalData, int offset, int count){
		this.normalBuffer = ByteBuffer.allocateDirect(count * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.normalBuffer.put(normalData, offset, count).position(0);
	}
	
	public void bufferTextureCoordinate(float[] textureCoordinateData, int offset, int count){
		this.textureCoordinateBuffer = ByteBuffer.allocateDirect(count * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.textureCoordinateBuffer.put(textureCoordinateData, offset, count).position(0);
	}
	
	public void bufferColor(float[] colorData, int offset, int count){
		this.colorBuffer = ByteBuffer.allocateDirect(count * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.colorBuffer.put(colorData, offset, count).position(0);		
	}
	
	public int getVertexCount(){
		return this.vertexCount;
	}
	
	public FloatBuffer getPositionBuffer(){
		this.positionBuffer.position(0);
		return this.positionBuffer;
	}
	
	public FloatBuffer getNormalBuffer(){
		this.normalBuffer.position(0);
		return this.normalBuffer;
	}
	
	public FloatBuffer getColorBuffer(){
		this.colorBuffer.position(0);
		return this.colorBuffer;
	}
	
	public FloatBuffer getTextureCoordinateBuffer(){
		this.textureCoordinateBuffer.position(0);
		return this.textureCoordinateBuffer;
	}
	
	public void submit(GpuProgramBasic program){
		//position attribute
		if(this.positionBuffer!=null){
			this.positionBuffer.position(0);
	        GLES20.glVertexAttribPointer(program.aPositionHandle, numberOfFloatsPerVertex, GLES20.GL_FLOAT, false,
	                0, this.positionBuffer);
	        Globals.checkGlError("glVertexAttribPointer aPositionHandle");
	        
	        GLES20.glEnableVertexAttribArray(program.aPositionHandle);
	        Globals.checkGlError("glEnableVertexAttribArray maPositionHandle");
		}
		
        //color attribute
        if(this.colorBuffer!=null){
			this.colorBuffer.position(0);
	        GLES20.glVertexAttribPointer(program.aColorHandle, numberOfFloatsPerColor, GLES20.GL_FLOAT, false,
	                0, this.colorBuffer);
	        Globals.checkGlError("glVertexAttribPointer aColorHandle");                
	        
	        GLES20.glEnableVertexAttribArray(program.aColorHandle);
	        Globals.checkGlError("glEnableVertexAttribArray aColorHandle");
        }
        
        if(this.normalBuffer!=null){
	        //normal attribute
	        this.normalBuffer.position(0);
	        GLES20.glVertexAttribPointer(program.aNormalHandle, numberOfFloatsPerVertex, GLES20.GL_FLOAT, false,
	                0, this.normalBuffer);
	        Globals.checkGlError("glVertexAttribPointer aNormalHandle");
	        
	        GLES20.glEnableVertexAttribArray(program.aNormalHandle);
	        Globals.checkGlError("glEnableVertexAttribArray aNormalHandle");
        }
        
        if(this.textureCoordinateBuffer!=null){
        	this.textureCoordinateBuffer.position(0);
	        GLES20.glVertexAttribPointer(program.aTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
	                0, this.textureCoordinateBuffer);
	        Globals.checkGlError("glVertexAttribPointer aTextureCoordinateHandle");
	        
	        GLES20.glEnableVertexAttribArray(program.aTextureCoordinateHandle);
	        Globals.checkGlError("glEnableVertexAttribArray aNormalHandle");
        }
	}
	
	
	public void render(){
        GLES20.glDrawArrays(primitiveMode, 0, this.vertexCount);
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES, this.triangleIndexes.length, GLES20.GL_UNSIGNED_SHORT, this.triangleIndexBuffer);
        Globals.checkGlError("glDrawArrays");
	}

}
