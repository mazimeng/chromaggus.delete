package mzm.de;

import mzm.de.interfaces.ICamera;
import android.opengl.GLES20;

import android.opengl.Matrix;

public class GlViewport2d implements IViewport{
	protected int width;
	protected int height;
	protected float far;
	protected float near;
	protected float[] matrix;
	protected boolean use;
	
	public GlViewport2d(){
        this.matrix = new float[16];
		Matrix.setIdentityM(this.matrix, 0);
		this.use = false;
	}
	
	public float[] getProjection(){
		return this.matrix;
	}

	@Override
	public float[] getMatrix() {
		// TODO Auto-generated method stub
		return this.matrix;
	}

	@Override
	public void calculate(int width, int height) {
		float left  = -0.5f*width;
        float right = 0.5f*width;
        float top = 0.5f*height;
        float bottom = -0.5f*height;
        far = 1;
        near = -1;
        
        this.width = width;
        this.height = height;
        
		Matrix.setIdentityM(this.matrix, 0);
        Matrix.orthoM(this.matrix, 0, left, right, bottom, top, near, far);
	}
	
	public void use(){
		if(!this.use){
			GLES20.glViewport(0, 0, (int)width, (int)height);
			this.use=true;
		}
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}
}
