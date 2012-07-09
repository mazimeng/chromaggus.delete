package mzm.de;

import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.View.OnTouchListener;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import mzm.de.gui.FontFactory;
import mzm.de.gui.GlFont;
import mzm.de.interfaces.ICamera;
import mzm.de.interfaces.IGameApplication;
import mzm.de.interfaces.IGameLogic;
import mzm.library.GlRenderable;
import mzm.library.LoaderObj;
import mzm.library.Vector2f;
import mzm.library.Vector3f;

public class GameApplication implements IGameApplication{

	private Context context;
	private Shader vs;
	private Shader fs;
	private GpuProgramBasic program;
	private GpuProgramBasic programTextured;
	private GpuProgramBasic flatProgram;	
	private GpuProgram2d gpuProgram2d;
	
	private GpuProgramBasic uber;
	
	private float[] modelViewProjectionMatrix = new float[16];
	private float[] modelViewMatrix = new float[16];
	
	private float[] projectionMatrix = new float[16];	
    private float[] modelMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] lightMatrix = new float[16];
    
    public float[] view2dMatrix = new float[16];
    
    private float[] orthographicMatrix = new float[16];
    
    float[] lightPosition = {0.0f, 10.0f, 0.0f, 1.0f};
    
    GlRenderable point;
    GlRenderable environment;   
    GlRenderable mark;
    GlRenderable toy;
    GlRenderable unitCube;
    GlRenderable unitSphere;
    
    GlGeometry unitSquare;    
    
    GlTexture toyTexture;
    GlTexture bioTexture;
    GlTexture meTexture;
    GlTexture woodTexture;
    GlTexture aTexture;
    GlTexture crosshairTexture;
    GlTexture squareTexture;
    GlTexture worldMapTexture;
    
    GlFont font;
    
    GlCamera camera;

	private long elapsed;
	private long lastFrame;
	
	Character character;

	public int windowWidth;
	public int windowHeight;
	
	public Vector3f rayHitPoint;
	
	private float frameRate = 1000.0f/60.0f;
	
	
	private TaskManager taskManager;
	
	protected GameLogic gameLogic;
	
	protected GLSurfaceView view;
	protected OnTouchListener touchListener; 
	protected IRenderingSystem renderingSystem;
	protected IRenderableLayer worldLayer;
	protected GlCamera2d camera2d;
	
	public GlCamera2d getCamera(){
		return this.camera2d;
	}
	
	@Override
	public void step() {
		//Debug.startMethodTracing("de");
		long current = System.currentTimeMillis();
		elapsed = current - this.lastFrame;
		this.lastFrame = current;
		
		this.update(elapsed);
		this.gameLogic.update(elapsed);
		this.renderingSystem.update(elapsed);
		
		this.renderingSystem.render(elapsed);
		
		//this.render(elapsed);
		//this.renderOverlay(elapsed);
		
		//Debug.stopMethodTracing();
		
	}

	@Override
	public void resize(int width, int height) {
//		Matrix.setIdentityM(this.projectionMatrix, 0);
//		Matrix.setIdentityM(this.orthographicMatrix, 0);
		
		this.renderingSystem.resize(width, height);
		
//		{
//			GLES20.glViewport(0, 0, (int)width, (int)height);
//	        float ratio = (float) width / height;
//	        float fov = (float)Math.PI/6.0f;
//	        float near = 0.1f;
//	        float far = 100000.0f;
//	        float h = (float)Math.tan(fov)*near;
//	        float w = ratio*h;
//	        Matrix.frustumM(projectionMatrix, 0, -w, w, -h, h, near, far);
//	        
//	        this.windowHeight = (int)height;
//	        this.windowWidth = (int)width;
//		}
//		
//		{
//	        
//			////(0,0) at center of screen
//	        float left  = -0.5f*width;
//	        float right = 0.5f*width;
//	        float top = 0.5f*height;
//	        float bottom = -0.5f*height;
//	        float far = 1;
//	        float near = -1;
//	        
//	        Matrix.orthoM(this.orthographicMatrix, 0, left, right, bottom, top, near, far);
//	        
//	        //(0,0) at bottom left corner
////	        Matrix.orthoM(this.orthographicMatrix, 0, 
////	        		0, this.windowWidth, 
////	        		//-this.windowHeight, 0, 
////	        		0, this.windowHeight, 
////	        		-1, 1);
//	        
////	        float[] a = new float[16];
////	        float[] b = new float[16];
////	        
////	        Matrix.setIdentityM(a, 0);
////	        Matrix.setIdentityM(b, 0);
////	        
////	        System.arraycopy(this.orthographicMatrix, 0, a, 0, 16);
//	        //Matrix.translateM(b, 0, left, top, 0);
//	        
//	        //Matrix.multiplyMM(this.orthographicMatrix, 0, a, 0, b, 0);
//	        
////	        this.orthographicMatrix[0] = 2/(right-left);
////	        this.orthographicMatrix[5] = 2/(top-bottom);
////	        this.orthographicMatrix[10] = -2/(far-near);
////	        
////	        this.orthographicMatrix[12] = -(right+left)/(right-left);
////	        this.orthographicMatrix[13] = -(top+bottom)/(top-bottom);
////	        this.orthographicMatrix[14] = -(far+near)/(far-near);
//		}
	}

	@Override
	public void initialize() {
		this.view.setOnTouchListener(this.touchListener);
		
		
		
		this.initializeGpuProgram();
		this.initializeMeshes();		
		this.initializeFont();
		this.initializePhysics();
		
		
		
		this.camera = new GlCamera();
		
		this.camera.setPosition(new Vector3f(0, 0, 5));
		
		GlRenderingSystem rs = new GlRenderingSystem();
		rs.initialize();
		this.renderingSystem = rs;
		
		this.worldLayer = new GlRenderableLayer2d();
		
		{
			this.camera2d = new GlCamera2d();
			this.camera2d.setPosition(new Vector2f(300, 300));
			
		}
		
		this.worldLayer.setCamera(this.camera2d);
		
		
		GlViewport2d viewport = new GlViewport2d();
		this.worldLayer.setViewport(viewport);
		
		this.renderingSystem.addLayer(this.worldLayer);
		
		{
			GlSprite sprite = new GlSprite(this.gpuProgram2d, this.worldMapTexture, this.unitSquare);
			int w = 784*2;
    		int h = 1084*2;
    		sprite.setSize(new Vector2f(w, h));			
			this.worldLayer.addRenderable(sprite);
		}
		
		{
			GlSprite sprite = new GlSprite(this.gpuProgram2d, this.bioTexture, this.unitSquare);
			sprite.setSize(new Vector2f(100, 100));
			this.worldLayer.addRenderable(sprite);
		}
		
		
		
		
		
//		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//		GLES20.glEnable(GLES20.GL_CULL_FACE);	
//		GLES20.glFrontFace(GLES20.GL_CCW);
//		GLES20.glCullFace(GLES20.GL_BACK);
		
		
//		GLES20.glDepthFunc( GLES20.GL_LEQUAL );		
//		GLES20.glDepthMask( true );
//		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
//		
//		GLES20.glClearColor(0.3f, 0.2f, 0.5f, 0.0f);
		
	}
	
	
	
	public GameApplication(Context context, GLSurfaceView view){
		this.context = context;
		this.elapsed = 0;
		this.lastFrame = System.currentTimeMillis();
		this.view = view;
		
		this.taskManager =  new TaskManager();
		this.gameLogic = new GameLogic();
		this.touchListener = new TouchListener(this);
	}		
	
	private void update(float elapsed){		
		this.taskManager.update(elapsed);		
		this.camera.update(elapsed);
	}
	
	public IGameLogic getGameLogic(){
		return this.gameLogic;
	}
	
	
	private void renderOverlay(float elapsed){
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_BLEND); 
		{        	        
			float[] t = new float[16];
			float[] u = new float[16];
			
        	this.gpuProgram2d.use();        	
        	//this.squareTexture.activate();
        	
        	
//        	Matrix.setIdentityM(t, 0);
//        	Matrix.scaleM(t, 0, 50f, 50f, 50f);        	
//        	this.crosshairTexture.activate();
//        	this.drawOverlay(this.unitSquare, programOverlay, t);
//        	
//        	this.squareTexture.activate();
//        	
//        	float halfSize = 100f;
//        	Matrix.setIdentityM(t, 0);
//        	Matrix.translateM(t, 0, -0.5f*this.windowWidth+halfSize, -0.5f*this.windowHeight+halfSize, 0);
//        	Matrix.scaleM(t, 0, halfSize, halfSize, halfSize);
//        	this.drawOverlay(this.unitSquare, programOverlay, t);
//        	
//        	Matrix.setIdentityM(t, 0);
//        	Matrix.translateM(t, 0, 0.5f*this.windowWidth-halfSize, -0.5f*this.windowHeight+halfSize, 0);
//        	Matrix.scaleM(t, 0, halfSize, halfSize, halfSize);
//        	this.drawOverlay(this.unitSquare, programOverlay, t);
        	
//        	this.woodTexture.activate();
//        	Matrix.setIdentityM(t, 0);
//        	Matrix.translateM(t, 0, 0, 0, 0);
//        	Matrix.scaleM(t, 0, 1280, 500, 1);
//        	this.drawOverlay(this.unitSquare, programOverlay, t);
        	
//        	float[] temp = new float[16];
//        	Matrix.setIdentityM(view2dMatrix, 0);
//        	
//        	Matrix.translateM(view2dMatrix, 0, 0, 0, 0);
//        	{
//        		Matrix.setIdentityM(temp, 0);
//        		int w = 784*2;
//        		int h = 1084*2;
//	        	this.worldMapTexture.activate();
//	        	Matrix.setIdentityM(t, 0);
//	        	//Matrix.translateM(t, 0, 784*0.5f, h*0.5f, 0);
//	        	Matrix.scaleM(t, 0, w, h, 1);
//	        	Matrix.multiplyMM(temp, 0, view2dMatrix, 0, t, 0);
//	        	this.drawOverlay(this.unitSquare, programOverlay, temp);
//        	}
//        	
//        	{
//        		Matrix.setIdentityM(temp, 0);
//	        	float size = 100;
//	        	this.meTexture.activate();
//	        	Matrix.setIdentityM(t, 0);
//	        	//Matrix.translateM(t, 0, size*0.5f+100, size*0.5f, 0);
//	        	Matrix.scaleM(t, 0, size, size, 1);
//	        	Matrix.multiplyMM(temp, 0, view2dMatrix, 0, t, 0);
//	        	this.drawOverlay(this.unitSquare, programOverlay, temp);
//        	}
//        	this.bioTexture.activate();
//        	Matrix.setIdentityM(t, 0);
//        	Matrix.translateM(t, 0, 100, 100, 0);
//        	Matrix.scaleM(t, 0, halfSize, halfSize, halfSize);
//        	this.drawOverlay(this.unitSquare, programOverlay, t);
        	
        	
        	
        	//render fps
        	//this.aTexture.activate();\
//        	elapsedAccumulation+=elapsed;
//        	frameCount++;
//        	
//        	String text = "fps:"+(float)frameCount/elapsedAccumulation*1000.0f;
//        	for(int i=0; i<text.length(); ++i){
//        		java.lang.Character c = text.charAt(i);
//        		GlRenderable r = this.font.renderables.get(c);
//        		Glyph glyph = this.font.glyphs.get(c);
//        		float[] pos = {-400+i*glyph.width, 100, 0};
//        		this.drawOverlay(r, this.programOverlay, pos);
//        	}
//        	
//        	if(elapsedAccumulation>1000){
//        		this.frameCount= 0 ;
//        		elapsedAccumulation = 0;
//        	}        	        
        }
		GLES20.glDisable(GLES20.GL_BLEND);	
	}
	
	private void render(float elapsed){		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);		

		
		{
	    	Matrix.setIdentityM(this.viewMatrix, 0);

	    	this.camera.getViewMatrix(this.viewMatrix, 0);
	    	
		}
		
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);		
        {        	
        	this.programTextured.use();        	
        	this.drawLight(programTextured);
      
			
			this.woodTexture.activate();
			
			float[] cubeMatrix = new float[16];
			Matrix.setIdentityM(cubeMatrix, 0);
			//this.draw(this.unitCube, this.programTextured, cubeMatrix);
			
			//this.bioTexture.activate();
			//this.draw(this.unitCube, this.programTextured, this.platform1RigidBody, 5, 5, 5);
			
			
			///creature
			//this.draw(this.unitCube, this.programTextured, this.creature.body, 10, 20, 10);					
			//this.bioTexture.deactivate();
//			this.draw(this.unitSphere, this.programTextured, this.creature.leg1, 10, 10, 10);
//			this.draw(this.unitSphere, this.programTextured, this.creature.leg2, 10, 10, 10);
//			this.draw(this.unitSphere, this.programTextured, this.creature.leg3, 10, 10, 10);
			
			//bot
			//this.draw(this.unitCube, this.programTextured, this.bot.body, 10,20,10);
			//this.draw(this.unitCube, this.programTextured, this.bot.arm1, 5, 10, 5);
			
			GLES20.glEnable(GLES20.GL_BLEND); 
			
			GLES20.glDisable(GLES20.GL_BLEND); 
		}
        
        
        if(this.rayHitPoint!=null)
        {
        	this.flatProgram.use();
        	        	
        	Matrix.setIdentityM(this.modelMatrix, 0); 		    		
    		Matrix.setIdentityM(this.modelViewMatrix, 0);
    		Matrix.setIdentityM(this.modelViewProjectionMatrix, 0);
    		
	    	
        	Matrix.translateM(this.modelMatrix, 0, this.rayHitPoint.x, this.rayHitPoint.y, this.rayHitPoint.z);
	        Matrix.multiplyMM(this.modelViewMatrix, 0, this.viewMatrix, 0, this.modelMatrix, 0);	
	        Matrix.multiplyMM(modelViewProjectionMatrix, 0, this.projectionMatrix, 0, modelViewMatrix, 0);

	        GLES20.glUniformMatrix4fv(this.flatProgram.uMvpHandle, 1, false, modelViewProjectionMatrix, 0);
	        Globals.checkGlError("glUniformMatrix4fv uMvpHandle");
	        
	        this.point.submit(flatProgram);
	        this.point.render();	        
        }
        
        this.flatProgram.use();
        
		
		
		
	}

	private void initializeGpuProgram(){
		try{
			String vert = "vertexShader.vert";
			String frag = "fragmentShader.frag";
			
			//String vert = "vertexShader";
			//String frag = "fragmentShader";
			
			InputStream is = this.context.getAssets().open(vert);
			vs = new Shader();
			vs.initialize(GLES20.GL_VERTEX_SHADER, is);
			
			is = this.context.getAssets().open(frag);
			fs = new Shader();
			fs.initialize(GLES20.GL_FRAGMENT_SHADER, is);
			
			
		}
		catch(Exception ex){
			String msg = "vertexShader/fragmentShader program failed: "+ex.getMessage();
			Log.v(Globals.TAG, msg);
			throw new RuntimeException(msg);
		}
		
		this.program = new GpuProgramBasic();
		program.initialize(vs, fs);
		
		
		try{
			InputStream is = this.context.getAssets().open("flatVertexShader");
			vs = new Shader();
			vs.initialize(GLES20.GL_VERTEX_SHADER, is);
			
			is = this.context.getAssets().open("flatFragmentShader");
			fs = new Shader();
			fs.initialize(GLES20.GL_FRAGMENT_SHADER, is);
			
			
		}
		catch(Exception ex){
			String msg = "flatVertexShader program failed: "+ex.getMessage();
			Log.v(Globals.TAG, msg);
			throw new RuntimeException(msg);
		}
		
		this.flatProgram = new GpuProgramBasic();
		flatProgram.initialize(vs, fs);
		
		try{
			InputStream vi = this.context.getAssets().open("uber.vert");			
			
			InputStream fi = this.context.getAssets().open("uber.frag");
			this.uber = new GpuProgramBasic();
			uber.load(vi, fi);
		}
		catch(Exception ex){
			String msg = "uber program failed: "+ex.getMessage();
			Log.v(Globals.TAG, msg);
			throw new RuntimeException(msg);
		}
		
		try{
			InputStream vi = this.context.getAssets().open("textured.vert");			
			
			InputStream fi = this.context.getAssets().open("textured.frag");
			this.programTextured = new GpuProgramBasic();
			programTextured.load(vi, fi);
		}
		catch(Exception ex){
			String msg = "textured program failed: "+ex.getMessage();
			Log.v(Globals.TAG, msg);
			throw new RuntimeException(msg);
		}
		
		//overlay
		try{
			InputStream vi = this.context.getAssets().open("overlay.vert");			
			
			InputStream fi = this.context.getAssets().open("overlay.frag");
			this.gpuProgram2d = new GpuProgram2d();
			gpuProgram2d.load(vi, fi);
		}
		catch(Exception ex){
			String msg = "overlay program failed: "+ex.getMessage();
			Log.v(Globals.TAG, msg);
			throw new RuntimeException(msg);
		}
	}
	
	
	
	private void initializeMeshes(){
		try{												
			this.environment = this.createRenderable("world.obj");
			this.toy = this.createRenderable("toy.obj");
			this.unitCube = this.createRenderable("unitCube.obj");
			this.unitSphere = this.createRenderable("unitSphere.obj");
			
			this.toyTexture = this.createTexture("toy.png");
			this.bioTexture = this.createTexture("hazard-stripes-texture.jpg");
			this.meTexture  = this.createTexture("20100718141851ikdry.jpg");
			this.woodTexture = this.createTexture("Wood_floor_by_gnrbishop.jpg");
			this.aTexture = this.createTexture("font.png");
			this.crosshairTexture = this.createTexture("crosshair.png");
			this.squareTexture = this.createTexture("square.png");
			this.worldMapTexture = this.createTexture("UnderdarkWorldMap.jpg");
			
			{
				float[] p = new float[]{
					0.0f, 0.0f, 0.0f,
					1.0f, 0.0f, 0.0f,
					
					0.0f, 0.0f, 0.0f,
					0.0f, 1.0f, 0.0f,
					
					0.0f, 0.0f, 0.0f,
					0.0f, 0.0f, 1.0f,
				};
				
				float[] c = new float[]{
						1.0f, 0.0f, 0.0f,
						1.0f, 0.0f, 0.0f,
					
						0.0f, 1.0f, 0.0f,
						0.0f, 1.0f, 0.0f,
					
						0.0f, 0.0f, 1.0f,
						0.0f, 0.0f, 1.0f,
				};
				this.point = new GlRenderable();				
				this.point.bufferPosition(p, 0, p.length);
				this.point.bufferColor(c, 0, c.length);
				this.point.setPrimitiveMode(GLES20.GL_LINES);				
			}
			
			//overlay
			{
				float[] p = new float[]{
						-0.5f, 0.5f,
						
						0.5f, -0.5f,
						0.5f, 0.5f,
						
						
						-0.5f, 0.5f,
						
						-0.5f, -0.5f,
						0.5f, -0.5f,
						
					};
					
					float[] c = new float[]{
							1.0f, 1.0f, 1.0f, 0.5f,
							1.0f, 1.0f, 1.0f, 0.5f,								
							1.0f, 1.0f, 1.0f, 0.5f,
							
							1.0f, 1.0f, 1.0f, 0.5f,							
							1.0f, 1.0f, 1.0f, 0.5f,
							1.0f, 1.0f, 1.0f, 0.5f
					};
					
					float[] t = new float[]{
							0, 1,
							
							1, 0,
							1, 1,
							
							0, 1,
							
							0, 0,
							1, 0,
					};
					
					this.unitSquare = new GlGeometry();						
					this.unitSquare.bufferPosition(p, 0, p.length);
					this.unitSquare.bufferTextureCoordinate(t, 0, t.length);
					
					
			}
			
		}
		catch(Exception e){
			RuntimeException a = new RuntimeException(e.getMessage());
		}		
	}	
	
	private GlTexture createTexture(String fileName){
		InputStream is = null;
		GlTexture t = null;
		try {
			//BitmapFactory is an Android graphics utility for images
			
			is =  context.getAssets().open(fileName);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			t = new GlTexture();
			t.load(bitmap);
			
			bitmap.recycle();
			is.close();
		} 
		catch(Exception ex){
			throw new RuntimeException(ex.getMessage());
		}
		finally {
			try{
				is.close();
			}
			catch(Exception ex){
				
			}
		}
		
		return t;
	}
	
	private GlRenderable createRenderable(String fileName){
		GlRenderable renderable = null;
		try{
			mzm.library.LoaderObj lo = new LoaderObj();
			renderable = new GlRenderable();
			InputStream in = this.context.getAssets().open(fileName);
			lo.load(in);
			
			in.close();
			
			//position data
			float[] buffer = lo.getPositionArray();						
			renderable.bufferPosition(buffer, 0, buffer.length);
			
			//normal data
			buffer = lo.getNormalArray();	
			renderable.bufferNormal(buffer, 0, buffer.length);
			
			//texture data
			buffer = lo.getTextureCoordinateArray();	
			renderable.bufferTextureCoordinate(buffer, 0, buffer.length);
			
			//color data				
			buffer = new float[lo.countVertex()*3];
			for(int i=0; i<buffer.length; ++i){
				buffer[i] = 1.0f;
			}
			renderable.bufferColor(buffer, 0, buffer.length);
			
			this.toy = renderable;
		}
		catch(Exception e){
			throw new RuntimeException(e.getMessage());
		}
		
		return renderable;
	}
	
	
	
	public void drawLine(Vector3f start, Vector3f direction){
		
	}
	
	public void drawPoint(Vector3f p){
		
	}

//	protected void draw(GlRenderable r, GpuProgramBasic p, RigidBody rb){
//		this.draw(r,  p, rb, 1,1,1);
//	}
	protected void draw(GlRenderable r, GpuProgramBasic p, float[] matrix){
		Matrix.setIdentityM(this.modelViewProjectionMatrix, 0);
		Matrix.setIdentityM(this.modelViewMatrix, 0);
		Matrix.setIdentityM(this.modelMatrix, 0);
		
		final float[] a = new float[16];
		Matrix.setIdentityM(a, 0);
		
		Matrix.multiplyMM(this.modelMatrix, 0, a, 0, matrix, 0);
        Matrix.multiplyMM(this.modelViewMatrix, 0, this.viewMatrix, 0, this.modelMatrix, 0);	
        Matrix.multiplyMM(modelViewProjectionMatrix, 0, this.projectionMatrix, 0, modelViewMatrix, 0);
        
        GLES20.glUniformMatrix4fv(p.uMvpHandle, 1, false, modelViewProjectionMatrix, 0);
        Globals.checkGlError("glUniformMatrix4fv uMvpHandle");
        
        GLES20.glUniformMatrix4fv(p.uMvHandle, 1, false, this.modelViewMatrix, 0);
        Globals.checkGlError("glUniformMatrix4fv uNormalMatrixHandle");

        float[] normalMatrix = new float[16];
        Matrix.setIdentityM(normalMatrix, 0);
        Matrix.transposeM(normalMatrix, 0, this.modelViewMatrix, 0);
        Matrix.invertM(normalMatrix, 0, normalMatrix, 0);	        
        
        GLES20.glUniformMatrix4fv(p.uNormalMatrixHandle, 1, false, normalMatrix, 0);
        Globals.checkGlError("glUniformMatrix4fv uNormalMatrixHandle");
        
        r.submit(p);
        r.render();
	}
	
	private void drawLight(GpuProgramBasic p){		
		Vector3f lv = new Vector3f(0.0f, 10.0f, 0.0f);
		
		//lv.addLocal(this.character.getForward());
		this.lightPosition[0] = lv.x;
		this.lightPosition[1] = lv.y;
		this.lightPosition[2] = lv.z;
		float[] l = new float[]{0,0,0, 1.0f};
        
        //Matrix.setIdentityM(this.lightMatrix, 0);
        Matrix.multiplyMV(l, 0, this.viewMatrix, 0, this.lightPosition, 0);

        GLES20.glUniform4fv(p.uLightPositionHandle, 1, l, 0);
        Globals.checkGlError("glUniform3fv uLightPositionHandle");		
	}
	
	private void drawOverlay(GlRenderable r, GpuProgramOverlay p, float[] transform){		
        GLES20.glVertexAttribPointer(p.aPositionHandle, 2, GLES20.GL_FLOAT, false,
                0, r.getPositionBuffer());
        Globals.checkGlError("glVertexAttribPointer aPositionHandle");        
        GLES20.glEnableVertexAttribArray(p.aPositionHandle);
        Globals.checkGlError("glEnableVertexAttribArray maPositionHandle");
		
//        GLES20.glVertexAttribPointer(p.aColorHandle, 4, GLES20.GL_FLOAT, false,
//                0, r.getColorBuffer());
//        Globals.checkGlError("glVertexAttribPointer aColorHandle");        
//        GLES20.glEnableVertexAttribArray(p.aColorHandle);
//        Globals.checkGlError("glEnableVertexAttribArray aColorHandle");
        
        GLES20.glVertexAttribPointer(p.aTextureCoordinateHandle, 2, GLES20.GL_FLOAT, false,
                0, r.getTextureCoordinateBuffer());
        Globals.checkGlError("glVertexAttribPointer aTextureCoordinateHandle");        
        GLES20.glEnableVertexAttribArray(p.aTextureCoordinateHandle);
        Globals.checkGlError("glEnableVertexAttribArray aTextureCoordinateHandle");
        
        float[] a = new float[16];
        
        Matrix.setIdentityM(a, 0);
        Matrix.multiplyMM(a, 0, this.orthographicMatrix, 0, transform, 0);
        
        GLES20.glUniformMatrix4fv(p.uMHandle, 1, false, a, 0);
        Globals.checkGlError("glUniformMatrix4fv uMHandle");
        
        float[] filter = {1, 1, 1, 1};
        GLES20.glUniform4fv(p.uFilter, 1, filter, 0);
        Globals.checkGlError("glUniform4fv uFilter");
        
        r.render();
	}
	

	private void initializeFont(){
		InputStream is = null;
		GlTexture t = null;
		try {
			//BitmapFactory is an Android graphics utility for images
			
			is =  context.getAssets().open("font.xml");
			FontFactory fp = new FontFactory();
			this.font = fp.load(is);
			
		} 
		catch(Exception ex){
			throw new RuntimeException(ex.getMessage());
		}
		
	}
	
	protected void initializePhysics(){
		com.badlogic.gdx.utils.GdxNativesLoader.load();
		Vector2 v = new Vector2();
		World world = new World(v, true);
		//com.badlogic.gdx.physics.box2d.Transform t = new com.badlogic.gdx.physics.box2d.Transform();
	}
	
	public void QueueEvent(Runnable runnable){
		this.view.queueEvent(runnable);
	}
	
	public Vector2f toWorldCoordinate(Vector2f screen){
		int w = this.worldLayer.getViewport().getWidth();
		int h = this.worldLayer.getViewport().getHeight();

		Vector2f d = new Vector2f(screen.x - 0.5f*w, -(screen.y - 0.5f*h));
				
		Vector2f camPos = this.camera2d.getPosition();
		Vector2f c = camPos.add(d);
		return c;		
	}
	
	public void get(Vector2f screen){
		
	}
}
