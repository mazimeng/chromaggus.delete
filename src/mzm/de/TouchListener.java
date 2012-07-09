package mzm.de;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import mzm.input.IInputInterpreter;

public class TouchListener implements OnTouchListener, ITouchHandler {
	private float lastX[] = new float[10];
	private float lastY[] = new float[10];
	private GameApplication app;
	
	private ITouchState state;
	
	public TouchListener(GameApplication app){
		this.app = app;
	}
	

	@Override
	public boolean onTouch(View arg0, MotionEvent e) {		
		if(this.state== null)
		{
			this.state = new TouchStateDown(this.app, e);
		}
		
		this.state.process(this);
		
//		for(int i=0; i<e.getPointerCount(); ++i){
//			int pointerId = e.getPointerId(i);
//			final float x = e.getX(i);
//	        final float y = e.getY(i);
//	        
//	        final float deltaX = x - this.lastX[pointerId];
//	        final float deltaY = y - this.lastY[pointerId];
//	        
//			switch (e.getAction() & MotionEvent.ACTION_MASK) {
//	        case MotionEvent.ACTION_MOVE:
//	        	
//	        	break;
//	        	
//	        case MotionEvent.ACTION_UP:
//	        case MotionEvent.ACTION_POINTER_UP:
//	        case MotionEvent.ACTION_OUTSIDE:
//			case MotionEvent.ACTION_CANCEL:
//				
//	        	break;
//	        	
//	        case MotionEvent.ACTION_POINTER_DOWN:	        
//	        case MotionEvent.ACTION_DOWN:       
//	       
//			}
//			
//			this.lastX[pointerId] = x;
//	        this.lastY[pointerId] = y;
//		}
//		return false;
		return true;
	}


	@Override
	public void setState(ITouchState state) {
		// TODO Auto-generated method stub
		this.state = state;
	}
}
