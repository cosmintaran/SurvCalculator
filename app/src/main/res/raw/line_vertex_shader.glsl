
uniform mat4 u_MVP;
attribute vec4 vPosition;

void main()
{
	gl_Position = u_MVP * vPosition;
}