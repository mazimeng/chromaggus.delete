package mzm.de;

import java.util.ArrayList;
import java.util.LinkedList;

import mzm.de.interfaces.ICamera;
import mzm.de.interfaces.IRenderable;
import mzm.de.interfaces.ISceneNode;
import mzm.library.SceneNode;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class GlRenderableLayer implements IRenderableLayer{	
	protected LinkedList<IRenderable> renderables = new LinkedList<IRenderable>();
	protected IViewport viewport;
	protected ICamera camera;
	protected float[] projectionViewMatrix;
	
	public GlRenderableLayer(){
		super();
		projectionViewMatrix = new float[16];
		Matrix.setIdentityM(projectionViewMatrix, 0);
	}
	
	
	protected void preRender(float elapsed){		
//        float[] a = new float[16];
//        
//        Matrix.setIdentityM(a, 0);
//        Matrix.multiplyMM(a, 0, viewport.getMatrix(), 0, viewport.getCamera().getMatrix(), 0);
//        
//        GLES20.glUniformMatrix4fv(this.gpuProgram.uMHandle, 1, false, a, 0);
//        Globals.checkGlError("glUniformMatrix4fv uMHandle");
//        
//        float[] filter = {1, 1, 1, 1};
//        GLES20.glUniform4fv(this.gpuProgram.uFilter, 1, filter, 0);
//        Globals.checkGlError("glUniform4fv uFilter");
	}
	
	protected void postRender(float elapsed){
		
	}	

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void render(float elapsed) {
		this.preRender(elapsed);
		
		for(IRenderable renderable: this.renderables){
			renderable.render(this, elapsed);
		}
		
		this.postRender(elapsed);
	}

	public void addRenderable(IRenderable renderable) {
		// TODO Auto-generated method stub
		this.renderables.add(renderable);
	}


	@Override
	public float[] getProjectionViewMatrix() {
		return this.projectionViewMatrix;
	}


	@Override
	public ICamera getCamera() {
		return this.camera;
	}


	@Override
	public IViewport getViewport() {
		return this.viewport;
	}


	@Override
	public void update(float elapsed) {
		Matrix.multiplyMM(projectionViewMatrix, 0, 
				this.getViewport().getMatrix(), 0, 
				this.getCamera().getMatrix(), 0);
	}


	@Override
	public void setCamera(ICamera camera) {
		this.camera = camera;
		
	}


	@Override
	public void setViewport(IViewport viewport) {
		this.viewport = viewport;
	}
}
