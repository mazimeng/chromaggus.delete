package mzm.de.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mzm.library.GlRenderable;

public class GlFont {
	public Map<java.lang.Character, Glyph> glyphs = new HashMap<java.lang.Character, Glyph>();
	public ArrayList<BitmapEntity> maps = new ArrayList<BitmapEntity>();
	
	public Map<java.lang.Character, GlRenderable> renderables = new HashMap<java.lang.Character, GlRenderable>();
	public int baseWidth = 0;
	public int baseHeight = 0;
}
