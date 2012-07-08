uniform mat4 uM;

attribute vec2 aPosition;
attribute vec4 aColor;
attribute vec2 aTextureCoordinate;

varying vec2 vTextureCoordinate;
varying vec4 vColor;

void main() {	
	gl_Position = uM * vec4(aPosition, 0.0, 1.0);
	vColor = aColor;
	vTextureCoordinate = aTextureCoordinate;
}