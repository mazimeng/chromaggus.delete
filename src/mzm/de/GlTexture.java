package mzm.de;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class GlTexture {
	private int[] textureId=new int[1];

	public void load(Bitmap bitmap){		
		GLES20.glGenTextures(1, textureId, 0);
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
		
		//Create Nearest Filtered Texture
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
		
		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		android.graphics.Matrix m = new android.graphics.Matrix();
		m.postScale(1, -1);
		Bitmap b = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, b, 0);
		b.recycle();
	}
	
	public void activate(){
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
	}
	
	public void deactivate(){
		GLES20.glBindTexture(GLES20.GL_TEXTURE0, 0);
	}

}
