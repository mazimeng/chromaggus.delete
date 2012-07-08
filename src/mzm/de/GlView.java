package mzm.de;

import mzm.de.interfaces.IGameApplication;
import mzm.library.Vector2f;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

public class GlView extends GLSurfaceView {	
	public GlView(Context context) {
		super(context);
	}
	
	public void initialize(){
		this.setEGLContextClientVersion(2);
		
    	
    	
    	
    	String vendor = GLES20.glGetString(GLES20.GL_VENDOR);
    	String renderer = GLES20.glGetString(GLES20.GL_RENDERER);
    	String extensions = GLES20.glGetString(GLES20.GL_EXTENSIONS);
    	String shaderVersion = GLES20.glGetString(GLES20.GL_SHADING_LANGUAGE_VERSION);
    	String glVersion = GLES20.glGetString(GLES20.GL_VERSION);

        if(vendor!=null){
        	Log.v(Globals.TAG, vendor);
        }
        
        if(renderer!=null){
        	Log.v(Globals.TAG, renderer);
        }
        
        if(extensions!=null){
        	Log.v(Globals.TAG, extensions);
        }
        
        if(shaderVersion!=null){
        	Log.v(Globals.TAG, shaderVersion);
        }
        
        if(glVersion!=null){
        	
        }
	}
	
	public boolean onTouchEvent(MotionEvent e) {

//		for(int i=0; i<e.getPointerCount(); ++i){
//			int pointerId = e.getPointerId(i);
//			final float x = e.getX(i);
//            final float y = e.getY(i);
//            
//            final float deltaX = x - this.lastX[pointerId];
//            final float deltaY = y - this.lastY[pointerId];
//            
//			switch (e.getAction() & MotionEvent.ACTION_MASK) {
//	        case MotionEvent.ACTION_MOVE:
//	        	break;
//	        case MotionEvent.ACTION_UP:
//	        case MotionEvent.ACTION_POINTER_UP:
//	        	
//	        	break;
//	        case MotionEvent.ACTION_POINTER_DOWN:	        
//	        case MotionEvent.ACTION_DOWN:        
//
//	       
//			}
//			
//			this.lastX[pointerId] = x;
//            this.lastY[pointerId] = y;
//		}
//		for(int i=0; i<e.getPointerCount(); ++i){
//			int pointerId = e.getPointerId(i);
//			final float x = e.getX(i);
//            final float y = e.getY(i);
//            
//            final float deltaX = x - this.lastX[pointerId];
//            final float deltaY = y - this.lastY[pointerId];
//            
//            boolean rolling = false;
//            boolean turning = false;
//            boolean firing = false;
//            
//            //left bottom
//            {
//            	float radius = 150.0f;
//            	float centerx = radius;
//            	float centery = this.renderer.windowHeight-radius;
//            	float r2 = (x-centerx)*(x-centerx)+(y-centery)*(y-centery);
//            	if(r2<=(radius*radius)){            		
//	            	rolling = true;
//            	}
//            }
//            
//            //right bottom
//            {
//            	float radius = 150.0f;
//            	float centerx = this.renderer.windowWidth - radius;
//            	float centery = this.renderer.windowHeight - radius;
//            	float r2 = (x-centerx)*(x-centerx)+(y-centery)*(y-centery);
//            	if(r2<=(radius*radius)){
//            		turning = true;
//            		
//            	}
//            }
//            
//            //middle bottom
//            {
//            	float radius = 150.0f;
//            	float centerx = this.renderer.windowWidth*0.5f;
//            	float centery = this.renderer.windowHeight - radius;
//            	float r2 = (x-centerx)*(x-centerx)+(y-centery)*(y-centery);
//            	if(r2<=(radius*radius)){
//            		firing = true;
//            		
//            	}
//            }
//            
//			switch (e.getAction() & MotionEvent.ACTION_MASK) {
//	        case MotionEvent.ACTION_MOVE:
//	        	if(rolling){
//	        		this.renderer.rollX = deltaY;
//	            	this.renderer.rollZ = deltaX;
//	        	}
//            
//	        	if(turning){
//	        		this.queueEvent(new Runnable(){
//						public void run() {
//							renderer.setRotate(deltaY, deltaX, 0);
//							
//						}
//        			});
//	        	}
//	        	break;
//	        case MotionEvent.ACTION_UP:
//	        case MotionEvent.ACTION_POINTER_UP:
//	        	if(!rolling && !turning){
//	        		if(firing){
//	        			this.queueEvent(new Runnable(){
//							public void run() {
//								renderer.fire();
//								
//							}
//	        			});
//	        		}
//	        		else{
//		        		this.queueEvent(new Runnable(){
//							public void run() {
//								renderer.pick(x, y);
//								
//							}
//	        			});
//	        		}
//	        	}
//	        	break;
//	        case MotionEvent.ACTION_POINTER_DOWN:
//	        
//	        case MotionEvent.ACTION_DOWN:        
//
//	       
//			}
//			
//			this.lastX[pointerId] = x;
//            this.lastY[pointerId] = y;
//		}

        
        return true;
    }
	
}
