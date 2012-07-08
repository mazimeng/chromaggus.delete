precision mediump float;

uniform vec4 uLightPosition;

uniform sampler2D sTexture;

varying vec3 vColor;
varying vec3 vNormal;
varying vec3 vPosition;
varying vec2 vTextureCoordinate;

void main() {
	vec3 lightVector = uLightPosition.xyz - vPosition;
	float distance = length(lightVector);

	//it's said the interpolation corrupts the normal so normalization here
	vec3 n = normalize(vNormal);
	
	
	float diffuse = max(dot(n, normalize(lightVector)), 0.0);
	
	diffuse = diffuse * (1.0 / distance);
	diffuse = diffuse + 0.5;
  	gl_FragColor = texture2D(sTexture, vTextureCoordinate) * diffuse;//vec4(diffuse * vColor, 1.0);
	gl_FragColor.w = 0.5;
}