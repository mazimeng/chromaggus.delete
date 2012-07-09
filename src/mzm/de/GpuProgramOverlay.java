package mzm.de;

import mzm.de.interfaces.IGpuProgram;

public class GpuProgramOverlay extends GpuProgram implements IGpuProgram{
	public int uMHandle;
	public int uFilter;
	
	public int aPositionHandle;
	public int aColorHandle;
	public int aTextureCoordinateHandle;
	
	@Override
	protected void initializeHandles() {
		this.aColorHandle = this.getAttributeLocation("aColor");
		this.aPositionHandle = this.getAttributeLocation("aPosition");		
		this.aTextureCoordinateHandle = this.getAttributeLocation("aTextureCoordinate");
		
		this.uMHandle = this.getUniformLocation("uM");
		this.uFilter = this.getUniformLocation("uFilter");
		
		this.handles.put("aColor", aColorHandle);
		this.handles.put("aPosition", aPositionHandle);
		this.handles.put("aTextureCoordinate", aTextureCoordinateHandle);
		this.handles.put("uM", uMHandle);
		this.handles.put("uFilter", uFilter);
	}

	public int getHandle(String handleName) {
		return this.handles.get(handleName);
	}
	
}
