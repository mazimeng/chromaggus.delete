package mzm.de;

import java.io.InputStream;

import android.opengl.GLES20;
import android.util.Log;

public class GpuProgramBasic extends GpuProgram{
	public int aPositionHandle;
	public int aColorHandle;
	public int aNormalHandle;
	public int aTextureCoordinateHandle;
	
	public int uMvpHandle;
	public int uNormalMatrixHandle;
	public int uLightPositionHandle;
	public int uMvHandle;
	
	protected void initializeHandles(){
		this.aColorHandle = this.getAttributeLocation("aColor");
		this.aPositionHandle = this.getAttributeLocation("aPosition");
		this.aNormalHandle = this.getAttributeLocation("aNormal");
		this.aTextureCoordinateHandle = this.getAttributeLocation("aTextureCoordinate");
		
		this.uMvpHandle = this.getUniformLocation("uMvp");
		this.uNormalMatrixHandle = this.getUniformLocation("uNormalMatrix");
		this.uLightPositionHandle = this.getUniformLocation("uLightPosition");
		this.uMvHandle = this.getUniformLocation("uMv");
	}	
}
