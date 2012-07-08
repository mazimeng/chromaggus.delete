package mzm.de.interfaces;

import mzm.de.Globals;
import android.opengl.GLES20;
import android.util.Log;

public interface IGpuProgram {
	int getId();
	int getUniformLocation(String uniformName);
	
	int getAttributeLocation(String attributeName);

	void use();
}
