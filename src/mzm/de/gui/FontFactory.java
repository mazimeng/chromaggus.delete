package mzm.de.gui;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import mzm.library.GlRenderable;
import mzm.library.HtmlUtility;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FontFactory {
	public GlFont load(InputStream in) throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		Document doc = builder.parse(in);
		
		GlFont font = new GlFont();
		
		//font
		NodeList fonts = doc.getElementsByTagName("font");
		font.baseWidth = Integer.parseInt(fonts.item(0).getAttributes().getNamedItem("base").getNodeValue());
		font.baseHeight = Integer.parseInt(fonts.item(0).getAttributes().getNamedItem("height").getNodeValue());
		
		//bitmaps
		NodeList bitmaps = doc.getElementsByTagName("bitmap");
		for(int i=0; i<bitmaps.getLength(); ++i){
			Node node = bitmaps.item(i);
			BitmapEntity b = new BitmapEntity();
			
			String id = node.getAttributes().getNamedItem("id").getNodeValue();
			String name = node.getAttributes().getNamedItem("name").getNodeValue();
			String size = node.getAttributes().getNamedItem("size").getNodeValue();
			
			String[] s = size.split("x");
			b.id = Integer.parseInt(id);
			b.name = name;
			b.width = Integer.parseInt(s[0]);
			b.height = Integer.parseInt(s[1]);
			font.maps.add(b);
		}
		
		//glyphs
		NodeList glyphList = doc.getElementsByTagName("glyph");
		for(int i=0; i<glyphList.getLength(); ++i){
			Node node = glyphList.item(i);
			Glyph glyph = new Glyph();
			
			String ch = node.getAttributes().getNamedItem("ch").getNodeValue();
			String code = node.getAttributes().getNamedItem("code").getNodeValue();			
			String bm = node.getAttributes().getNamedItem("bm").getNodeValue();
			String origin = node.getAttributes().getNamedItem("origin").getNodeValue();
			String size = node.getAttributes().getNamedItem("size").getNodeValue();
			String aw = node.getAttributes().getNamedItem("aw").getNodeValue();
			String lsb = node.getAttributes().getNamedItem("lsb").getNodeValue();

			ch = HtmlUtility.decode(ch);
			String[] o = origin.split(",");
			String[] s = size.split("x");
			glyph.x = Integer.parseInt(o[0]);
			glyph.y = Integer.parseInt(o[1]);
			glyph.width = Integer.parseInt(s[0]);
			glyph.height = Integer.parseInt(s[1]);
			glyph.code = Integer.parseInt(code, 16);
			glyph.symbol = ch.charAt(0);
			
			font.glyphs.put(glyph.symbol, glyph);
			
			GlRenderable r = this.makeRenderable(glyph, font.maps.get(0));
			font.renderables.put(glyph.symbol, r);
		}
		
		return font;
	}
	
	private GlRenderable makeRenderable(Glyph glyph, BitmapEntity bitmap){
		GlRenderable r = new GlRenderable();
		r.numberOfFloatsPerVertex = 2;
		
		float[] positionData = new float[]{
			0, 0,			
			glyph.width, 0,
			0, glyph.height,
			
			
			0, glyph.height,			
			glyph.width, 0,
			glyph.width, glyph.height,
			
		};
		
		float left = (float)glyph.x/(float)bitmap.width;
		float right = (float)(glyph.x+glyph.width)/(float)bitmap.width;
		float top = (float)(bitmap.height-glyph.y)/(float)bitmap.height;
		float bottom = (float)(bitmap.height - (glyph.y+glyph.height))/(float)bitmap.height;
		
		
		float[] textureCoordinateData = new float[]{
			left, bottom,
			right, bottom,
			left, top,
			
			
			
			
			left, top,
			right, bottom,
			right, top,
			
			
			
		};
		
//		textureCoordinateData = new float[]{
//				0, 0.9f, 
//				0, 1,
//				0,1, 0.9f,
//				
//				0, 1,
//				0.1f, 1.0f,
//				0.1f, 0.9f
//			};
		
		r.bufferPosition(positionData, 0, positionData.length);
		r.bufferTextureCoordinate(textureCoordinateData, 0, textureCoordinateData.length);
		
		return r;
	}
}
