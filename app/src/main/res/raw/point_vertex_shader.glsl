uniform mat4 uMVPMatrix;
attribute vec4 vPosition;


void main()
{
	gl_Position = uMVPMatrix * vPosition;
	gl_PointSize = 15.0;
}