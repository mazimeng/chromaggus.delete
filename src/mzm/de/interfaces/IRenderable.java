package mzm.de.interfaces;

import mzm.de.GpuProgram;
import mzm.de.IRenderableLayer;

public interface IRenderable {
	void render(IRenderableLayer layer, float elapsed);
	int getId();
}
