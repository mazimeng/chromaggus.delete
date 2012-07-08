package mzm.de.interfaces;

import mzm.library.Vector3f;

public interface Spatial {	
	Vector3f getDirection();
	
	Vector3f getPosition();
	
	Vector3f getUp();
	
	void setDirection(Vector3f direction);
	void setPosition(Vector3f position);
	void setUp(Vector3f up);
}
