uniform mat4 uMvp;
uniform mat4 uMv;
uniform mat4 uNormalMatrix;

attribute vec3 aPosition;
attribute vec3 aColor;
attribute vec3 aNormal;

varying vec3 vColor;
varying vec3 vNormal;
varying vec3 vPosition;

void main() {	
	gl_Position = uMvp * vec4(aPosition, 1.0);	
	vColor = aColor;
	vNormal = vec3(uNormalMatrix * vec4(aNormal, 1.0));
	vPosition = vec3(uMv * vec4(aPosition, 1.0));	
		
}