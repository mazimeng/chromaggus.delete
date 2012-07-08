package mzm.de;

import android.opengl.GLES20;
import android.opengl.GLU;
import android.util.Log;

public class Globals {
	public static final String TAG="DE";
	
	public static final float PIOVER180 = (float)Math.PI/180.0f;
	
	public static boolean DEBUG = true;
	
	public static void checkGlError(String op) {
		
		if(!DEBUG){
			return;
		}
		
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.v(Globals.TAG, op + ": glError("+error+") " + GLU.gluErrorString(error));
            throw new RuntimeException(op + ": glError("+error+") " + GLU.gluErrorString(error));
            
        }
    }
}
