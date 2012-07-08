uniform mat4 uMvp;
uniform mat4 uMv;
uniform vec4 uLightPosition;
uniform mat4 uNormalMatrix;

attribute vec3 aPosition;
attribute vec3 aColor;
attribute vec3 aNormal;

varying vec3 vColor;
varying vec3 vNormal;
varying vec3 vPosition;

const vec3 skyColor = vec3(1.0, 1.0, 1.0);
const vec3 groundColor = vec3(0.0, 0.0, 0.0);

void main() {	
	gl_Position = uMvp * vec4(aPosition, 1.0);	
	
	vec3 norm = normalize(vec3(uNormalMatrix * vec4(aNormal, 1.0)));
	vec3 pos = vec3(uMv * vec4(aPosition, 1.0));	
	
	vec3 light = normalize(uLightPosition.xyz - pos);
	float costheta = dot(norm, light);
	float a= costheta * 0.5 + 0.5;
	
	vColor = mix(groundColor, skyColor, a) * aColor;	
}