package mzm.de.interfaces;

public interface ICamera {
	float[] getMatrix();
	//void setMatrix(float[] matrix);
	void update(float elapsed);
}
