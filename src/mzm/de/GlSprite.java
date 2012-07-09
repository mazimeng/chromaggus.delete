package mzm.de;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import mzm.de.interfaces.IRenderable;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class GlSprite implements IRenderable{	
	protected GpuProgram2d gpuProgram;
	
	protected float[] matrix;
	
	protected GlTexture texture;
	protected GlGeometry geo;
	
	public GlSprite(GpuProgram2d program, GlTexture texture, GlGeometry geo){
		this.gpuProgram = program;
		this.texture = texture;
		this.geo = geo;
		this.matrix = new float[16];
		Matrix.setIdentityM(this.matrix, 0);
	}
	
	
	public void setGpuProgram(GpuProgram2d program){
		this.gpuProgram = program;
	}
	
	public void setTexture(GlTexture texture){
		this.texture = texture;
	}
	
	public void render(IRenderableLayer layer, float elapsed){
		this.gpuProgram.use();
		this.texture.activate();
		
		float[] a = new float[16];
		float[] b= new float[16];
		Matrix.setIdentityM(a, 0);
		Matrix.setIdentityM(b, 0);
		
		Matrix.multiplyMM(a, 0, layer.getCamera().getMatrix(), 0, this.matrix, 0);
		
		Matrix.multiplyMM(b, 0, layer.getViewport().getMatrix(), 0, a, 0);

		GLES20.glUniformMatrix4fv(this.gpuProgram.uMHandle, 1, false, b, 0);
		Globals.checkGlError("glUniformMatrix4fv uMHandle");

		float[] filter = { 1, 1, 1, 1 };
		GLES20.glUniform4fv(this.gpuProgram.uFilter, 1, filter, 0);
		Globals.checkGlError("glUniform4fv uFilter");
		
		GLES20.glVertexAttribPointer(this.gpuProgram.aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, this.geo.getPositionBuffer());
        Globals.checkGlError("glVertexAttribPointer aPositionHandle");        
        GLES20.glEnableVertexAttribArray(this.gpuProgram.aPositionHandle);
        Globals.checkGlError("glEnableVertexAttribArray maPositionHandle");
		
        
        GLES20.glVertexAttribPointer(this.gpuProgram.aTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
                0, geo.getTextureCoordinateBuffer());
        Globals.checkGlError("glVertexAttribPointer aTextureCoordinateHandle");        
        GLES20.glEnableVertexAttribArray(this.gpuProgram.aTextureCoordinateHandle);
        Globals.checkGlError("glEnableVertexAttribArray aTextureCoordinateHandle");
		
        this.geo.render();
	}

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void setMatrix(float[] m){
		System.arraycopy(m, 0, this.matrix, 0, this.matrix.length);
	}
	
	public float[] getMatrix(){
		return matrix;
	}

}
