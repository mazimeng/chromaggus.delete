precision mediump float;

varying vec4 vColor;
varying vec2 vTextureCoordinate;

uniform sampler2D sTexture;
uniform vec4 uFilter;

void main() {	
	vec4 color = texture2D(sTexture, vTextureCoordinate);
	color = color * uFilter;
  	gl_FragColor = color;
}