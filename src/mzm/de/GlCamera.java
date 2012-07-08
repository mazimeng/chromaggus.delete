package mzm.de;

import android.opengl.Matrix;
import mzm.de.interfaces.Spatial;
import mzm.library.Vector3f;

public class GlCamera implements Spatial{
	private Spatial following;
	private Vector3f followOffset;
	
	private Vector3f direction;
	private Vector3f position;
	private Vector3f up;
	
	public GlCamera(){
		this.up = new Vector3f(0, 1, 0);
		this.direction = new Vector3f(0, 0, -1);  //default negative z
		this.position = new Vector3f(0, 0, 0);

		this.followOffset = new Vector3f(0, 1, 5);
	}
	
	
	public void update(float elapsed){
		if(following!=null){
			this.direction = this.following.getDirection();
			this.up = this.following.getUp();
			//this.position = this.following.getPosition().add(this.direction.mult(-5).add(this.up));
			
			Vector3f r = this.up.cross(this.direction).mult(-1.5f);
			Vector3f u = this.up.mult(1f);
			this.position = this.following.getPosition().add(this.direction.mult(-3.5f).add(u).add(r));
		}
	}

	public void follow(Spatial target){
		this.following = target;
	}
	
	public void free(){
		this.following = null;
	}

	public Vector3f getDirection() {
		// TODO Auto-generated method stub
		return this.direction;
	}


	public Vector3f getPosition() {
		// TODO Auto-generated method stub
		return this.position;
	}


	public Vector3f getUp() {
		// TODO Auto-generated method stub
		return this.up;
	}


	public void setDirection(Vector3f direction) {
		// TODO Auto-generated method stub
		this.direction = new Vector3f(direction);
	}


	public void setPosition(Vector3f position) {
		// TODO Auto-generated method stub
		this.position = new Vector3f(position);
	}


	public void setUp(Vector3f up) {
		// TODO Auto-generated method stub
		this.up = new Vector3f(up);
	}
	
	public void getViewMatrix(float[] outMatrix, int offset){
		Vector3f target = this.position.add(this.direction);
		
		Matrix.setLookAtM(outMatrix, offset, 
				this.position.x, this.position.y, this.position.z, 
				target.x, target.y, target.z, 
				this.up.x, this.up.y, this.up.z);
	}
}
