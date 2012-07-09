package mzm.de;

import mzm.de.interfaces.ICamera;

public interface IViewport {
	float[] getMatrix();
	void calculate(float width, float height);
	void use();
}
