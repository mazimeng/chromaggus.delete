precision mediump float;

uniform vec4 uLightPosition;

varying vec3 vColor;
varying vec3 vNormal;
varying vec3 vPosition;

void main() {
	vec3 lightVector = uLightPosition.xyz - vPosition;
	float distance = length(lightVector);

	//it's said the interpolation corrupts the normal so normalization here
	vec3 n = normalize(vNormal);
	
	
	float diffuse = max(dot(n, normalize(lightVector)), 0.0);
	
	diffuse = diffuse * (1.0 / distance);
	diffuse = diffuse + 0.2;
  	gl_FragColor = vec4(diffuse * vColor, 1.0);
}