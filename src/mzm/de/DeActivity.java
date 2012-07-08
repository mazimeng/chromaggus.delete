package mzm.de;

import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.opengl.GLES20;

import mzm.de.Globals;

//hi
public class DeActivity extends Activity {
	private GLSurfaceView glSurfaceView;
    

    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
    	boolean gles20Supported = this.detectOpenGLES20();
        Log.v(Globals.TAG, gles20Supported?"gles20 supported":"gles20 not supported");
        
        if(gles20Supported){            
        	GlView v = new GlView(this);
            v.initialize();
            
            GameApplication app = new GameApplication(this, v);
            
            RendererGl20 renderer = new RendererGl20(app);
        	v.setRenderer(renderer);
            
            this.glSurfaceView = v;
            setContentView(glSurfaceView);
            
            
//            glSurfaceView.setEGLContextClientVersion(2);
//        	renderer = new RendererGl20(this);
//        	glSurfaceView.setRenderer(renderer);
//        	
//        	
//	    	String vendor = GLES20.glGetString(GLES20.GL_VENDOR);
//	    	String renderer = GLES20.glGetString(GLES20.GL_RENDERER);
//	    	String extensions = GLES20.glGetString(GLES20.GL_EXTENSIONS);
//	    	String shaderVersion = GLES20.glGetString(GLES20.GL_SHADING_LANGUAGE_VERSION);
//	    	String glVersion = GLES20.glGetString(GLES20.GL_VERSION);
//	
//	        if(vendor!=null){
//	        	Log.v(Globals.TAG, vendor);
//	        }
//	        
//	        if(renderer!=null){
//	        	Log.v(Globals.TAG, renderer);
//	        }
//	        
//	        if(extensions!=null){
//	        	Log.v(Globals.TAG, extensions);
//	        }
//	        
//	        if(shaderVersion!=null){
//	        	Log.v(Globals.TAG, shaderVersion);
//	        }
//	        
//	        if(glVersion!=null){
//	        	Log.v(Globals.TAG, glVersion);
//	        }
        }
        
    }
    
    private boolean detectOpenGLES20() {
        ActivityManager am =
            (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x20000);
    }
    
    
}