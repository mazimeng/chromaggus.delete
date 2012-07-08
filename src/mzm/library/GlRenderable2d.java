package mzm.library;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import mzm.de.Globals;
import mzm.de.GpuProgram;
import mzm.de.GpuProgram2d;
import mzm.de.GpuProgramBasic;
import mzm.de.interfaces.IRenderable;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class GlRenderable2d implements IRenderable {
	private FloatBuffer positionBuffer;
	private FloatBuffer textureCoordinateBuffer;
	
	protected GpuProgram2d gpuProgram;
	private int primitiveMode=GLES20.GL_TRIANGLES;

	private int vertexCount = 0;	
	private float[] positionData;
	
	public int numberOfFloatsPerVertex = 2;
	public int numberOfFloatsPerColor = 4;
	
	public void GlRenderable(GpuProgram2d program){
		this.gpuProgram = program;
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
	
	public void bufferTextureCoordinate(float[] textureCoordinateData, int offset, int count){
		this.textureCoordinateBuffer = ByteBuffer.allocateDirect(count * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		this.textureCoordinateBuffer.put(textureCoordinateData, offset, count).position(0);
	}
	
	public int getVertexCount(){
		return this.vertexCount;
	}
	
	public FloatBuffer getPositionBuffer(){
		this.positionBuffer.position(0);
		return this.positionBuffer;
	}
	
	public FloatBuffer getTextureCoordinateBuffer(){
		this.textureCoordinateBuffer.position(0);
		return this.textureCoordinateBuffer;
	}
	
	protected void submit(){
		GLES20.glVertexAttribPointer(this.gpuProgram.aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, this.positionBuffer);
        Globals.checkGlError("glVertexAttribPointer aPositionHandle");        
        GLES20.glEnableVertexAttribArray(this.gpuProgram.aPositionHandle);
        Globals.checkGlError("glEnableVertexAttribArray maPositionHandle");
		
        
        GLES20.glVertexAttribPointer(this.gpuProgram.aTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
                0, textureCoordinateBuffer);
        Globals.checkGlError("glVertexAttribPointer aTextureCoordinateHandle");        
        GLES20.glEnableVertexAttribArray(this.gpuProgram.aTextureCoordinateHandle);
        Globals.checkGlError("glEnableVertexAttribArray aTextureCoordinateHandle");
	}
	
	
	public void render(){
		this.submit();
		
        GLES20.glDrawArrays(primitiveMode, 0, this.vertexCount);
        //GLES20.glDrawElements(GLES20.GL_TRIANGLES, this.triangleIndexes.length, GLES20.GL_UNSIGNED_SHORT, this.triangleIndexBuffer);
        Globals.checkGlError("glDrawArrays");
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
