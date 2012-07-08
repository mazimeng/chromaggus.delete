package mzm.library;

import java.util.ArrayList;
import java.util.LinkedList;

import mzm.de.GpuProgram2d;
import mzm.de.IViewport;
import mzm.de.interfaces.IRenderable;
import mzm.de.interfaces.ISceneNode;

import android.opengl.Matrix;

public abstract class SceneNode implements ISceneNode {
	protected LinkedList<IRenderable> renderables = new LinkedList<IRenderable>();	
	protected float[] matrix = new float[16];
	
	public SceneNode(){
		Matrix.setIdentityM(this.matrix, 0);
	}
	
	public float[] getMatrix(){
		return this.matrix;
	}

	@Override
	public void render(IViewport viewport, float elapsed) {
		this.preRender(viewport, elapsed);
		for(IRenderable renderable: this.renderables){
			renderable.render();
		}
		
		this.postRender(viewport, elapsed);
	}
	
	abstract protected void preRender(IViewport viewport, float elapsed);
	
	abstract protected void postRender(IViewport viewport, float elapsed);
	
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
