package mzm.de;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.os.Debug;
import android.util.Log;
import mzm.de.gui.FontFactory;
import mzm.de.gui.GlFont;
import mzm.de.gui.GlString;
import mzm.de.gui.Glyph;
import mzm.de.interfaces.IGameApplication;
import mzm.library.CameraFps;
import mzm.library.GlRenderable;
import mzm.library.LoaderObj;
import mzm.library.Matrix4f;
import mzm.library.NormalHelper;
import mzm.library.Quaternion;
import mzm.library.Vector3f;

public class RendererGl20 implements GLSurfaceView.Renderer{
	protected IGameApplication app;
	
	
	public RendererGl20(IGameApplication app){
		this.app = app;
	}
	
	public void onDrawFrame(GL10 gl) {
		this.app.step();
	}
	
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.app.resize(width, height);
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {		
		this.app.initialize();
	}	
}
