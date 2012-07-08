package mzm.de.gui;

public class GlString {
	private String text = "";
	private GlFont font = null;
	public GlString(String t, GlFont f){
		this.text = t;
		this.font = f;
	}
	
	public void render(){
		for(int i=0; i<this.text.length(); ++i){
			int code = text.charAt(i);
			this.font.renderables.get(code).render();
		}
	}
}
