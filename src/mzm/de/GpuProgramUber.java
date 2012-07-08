package mzm.de;

public class GpuProgramUber extends GpuProgram{
	public int MCVertex;
	//public int aColorHandle;
	public int MCNormal;
	
	public int MVMatrix;
	public int MVPMatrix;
	public int NormalMatrix;
	public int WCLightPos;
	public int ViewPosition;
	public int WCtoLC;
	public int WCtoLCit;
	
	public int MCtoWC;
	public int MCtoWCit;
	
	protected void initializeHandles(){
//		this.aColorHandle = this.getAttributeLocation("aColor");
//		this.aPositionHandle = this.getAttributeLocation("aPosition");
//		this.aNormalHandle = this.getAttributeLocation("aNormal");
//		
//		this.uMvpHandle = this.getUniformLocation("uMvp");
//		this.uNormalMatrixHandle = this.getUniformLocation("uNormalMatrix");
//		this.uLightPositionHandle = this.getUniformLocation("uLightPosition");
//		this.uMvHandle = this.getUniformLocation("uMv");
	}
}
