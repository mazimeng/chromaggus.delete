package mzm.library;

import java.util.ArrayList;
import java.util.LinkedList;

import mzm.de.Globals;
import mzm.de.GpuProgram2d;
import mzm.de.IViewport;
import mzm.de.interfaces.IRenderable;
import mzm.de.interfaces.ISceneNode;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class GlSceneNode2d extends SceneNode implements ISceneNode {	
	protected GpuProgram2d gpuProgram;
	
	public GlSceneNode2d(GpuProgram2d program){
		super();
		
		this.gpuProgram = program;
	}
	
//	@Override
//	public void render(IViewport viewport, float elapsed) {
//		this.preRender(viewport, elapsed);
//		for(IRenderable renderable: this.renderables){
//			GLES20.glVertexAttribPointer(gpuProgram.aPositionHandle, 2, GLES20.GL_FLOAT, false,
//	                0, renderable.getPositionBuffer());
//	        Globals.checkGlError("glVertexAttribPointer aPositionHandle");        
//	        GLES20.glEnableVertexAttribArray(p.aPositionHandle);
//	        Globals.checkGlError("glEnableVertexAttribArray maPositionHandle");
//			
////	        GLES20.glVertexAttribPointer(p.aColorHandle, 4, GLES20.GL_FLOAT, false,
////	                0, r.getColorBuffer());
////	        Globals.checkGlError("glVertexAttribPointer aColorHandle");        
////	        GLES20.glEnableVertexAttribArray(p.aColorHandle);
////	        Globals.checkGlError("glEnableVertexAttribArray aColorHandle");
//	        
//	        GLES20.glVertexAttribPointer(p.aTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
//	                0, r.getTextureCoordinateBuffer());
//	        Globals.checkGlError("glVertexAttribPointer aTextureCoordinateHandle");        
//	        GLES20.glEnableVertexAttribArray(p.aTextureCoordinateHandle);
//	        Globals.checkGlError("glEnableVertexAttribArray aTextureCoordinateHandle");
//			renderable.render();
//		}
//		
//		this.postRender(viewport, elapsed);
//	}
	
	protected void preRender(IViewport viewport, float elapsed){		
        float[] a = new float[16];
        
        Matrix.setIdentityM(a, 0);
        Matrix.multiplyMM(a, 0, viewport.getMatrix(), 0, viewport.getCamera().getMatrix(), 0);
        
        GLES20.glUniformMatrix4fv(this.gpuProgram.uMHandle, 1, false, a, 0);
        Globals.checkGlError("glUniformMatrix4fv uMHandle");
        
        float[] filter = {1, 1, 1, 1};
        GLES20.glUniform4fv(this.gpuProgram.uFilter, 1, filter, 0);
        Globals.checkGlError("glUniform4fv uFilter");
	}
	
	protected void postRender(IViewport viewport, float elapsed){
		
	}	

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
}
