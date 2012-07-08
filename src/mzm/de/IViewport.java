package mzm.de;

import mzm.de.interfaces.ICamera;

public interface IViewport {
	float[] getMatrix();
	void update(float width, float height);
	ICamera getCamera();
}
