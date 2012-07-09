package mzm.de;

import mzm.de.interfaces.ICamera;
import android.opengl.Matrix;

public class GlViewport3d implements IViewport {
	protected float width;
	protected float height;
	protected float x;
	protected float y;
	protected float far;
	protected float near;
	protected float[] matrix;
	
	public GlViewport3d(float x, float y, float width, float height, float far, float near){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		
		this.matrix = new float[16];
		Matrix.setIdentityM(this.matrix, 0);
		
		float ratio = (float) width / height;
        float fov = (float)Math.PI/6.0f;
        float h = (float)Math.tan(fov)*near;
        float w = ratio*h;
        Matrix.frustumM(matrix, 0, -w, w, -h, h, near, far);
	}
	

	@Override
	public float[] getMatrix() {
		// TODO Auto-generated method stub
		return this.matrix;
	}

	@Override
	public void calculate(float width, float height) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void use() {
		// TODO Auto-generated method stub
		
	}

}
