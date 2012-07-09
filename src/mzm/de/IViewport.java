package mzm.de;

import mzm.de.interfaces.ICamera;

public interface IViewport {
	float[] getMatrix();
	void calculate(int width, int height);
	void use();
	int getWidth();
	int getHeight();
}
