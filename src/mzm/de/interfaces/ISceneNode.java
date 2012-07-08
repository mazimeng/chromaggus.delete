package mzm.de.interfaces;

import mzm.de.IViewport;

public interface ISceneNode {
	void render(IViewport viewport, float elapsed);
	int getId();
}
