package cn.songshan99.realicfootprint;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import cn.songshan99.FootprintParser.FootprintParserLexer;
import cn.songshan99.FootprintParser.FootprintParserParser;
import cn.songshan99.FootprintParser.FootprintParserParser.ElementContext;
import cn.songshan99.FootprintParser.ICFootprint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;


public class ICFootprintView extends View {

	//private ICFootprint mICFootprint;
	private ICFootprintRender mICFootprintRender;
	private float mLastTouchX=0,mLastTouchY=0;
	
	public DisplayMetrics mDisplayMetrics;//TODO: change to private later
	
	private int mActivePointerId = MotionEvent.INVALID_POINTER_ID;
	
	private void setmICFootprintRender(ICFootprintRender mICFootprintRender) {
		this.mICFootprintRender = mICFootprintRender;
	}
	
	
	public ICFootprintRender getmICFootprintRender() {
		return mICFootprintRender;
	}


	public ICFootprintView(Context context) {
		super(context);
		if(isInEditMode()) return;
		init();
	}

	public ICFootprintView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if(isInEditMode()) return;
		init();
	}

	public ICFootprintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(isInEditMode()) return;
		init();
	}
	
	private void init(){
		mDisplayMetrics = new DisplayMetrics();
		Activity hostactivity = (Activity) getContext();
		hostactivity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(isInEditMode()) return;
		//TODO: draw the default thing, such as background, at here
		
		if(mICFootprintRender==null) return;
			
		ICFootprintRender.drawFootprintRender(mICFootprintRender, canvas);
		
	}
	
	public void setICFootprint(ICFootprint footprint){
		if(footprint == null) return;
		//set the footprint to center of the screen
		float dx, dy;
		dx = ICFootprint.CentiMil.PixelToCentiMil(getWidth()/2, mDisplayMetrics.densityDpi);
		dy = ICFootprint.CentiMil.PixelToCentiMil(getHeight()/2, mDisplayMetrics.densityDpi);
		footprint.offsetTheFootprint(dx, dy);
		mICFootprintRender = new ICFootprintRender(footprint, mDisplayMetrics);
	}
	
	public static ICFootprint parseFootprintFile(InputStream stream) throws IOException{
		//TODO: change input to file stream?? catch exceptions and let upper level know
		ANTLRInputStream instream = new ANTLRInputStream(stream);
		FootprintParserLexer lexer = new FootprintParserLexer(instream);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		FootprintParserParser parser = new FootprintParserParser(tokens);
		ElementContext element = parser.element();
		return element.footprint;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) { // Let the
														// ScaleGestureDetector
														// inspect all events.
		//The onTouchEvent shall perform following behaviors:
		//1. when there is a finger move, the footprint shall be moved with the finger movement.
		//2. when the footprint is about to move "out of" the window, the move action shall be ignored.
		//3. when multi-finger touch, only track the movement of the first finger.
		final int action = MotionEventCompat.getActionMasked(event);
		final int pointerIndex;
		switch (action) {
		case MotionEvent.ACTION_DOWN:{
			mActivePointerId = MotionEventCompat.getPointerId(event, 0);//always track the first finger
			pointerIndex = MotionEventCompat.findPointerIndex(event,
					mActivePointerId);
			mLastTouchX=MotionEventCompat.getX(event, pointerIndex);
			mLastTouchY=MotionEventCompat.getY(event, pointerIndex);
			break;
		}

		case MotionEvent.ACTION_MOVE: {
			// Find the index of the active pointer and fetch its position
			mActivePointerId = MotionEventCompat.getPointerId(event, 0);//always track the first finger
			pointerIndex = MotionEventCompat.findPointerIndex(event,
					mActivePointerId);
			ICFootprint footprint = mICFootprintRender.getmICFootprint();
			RectF rect = footprint.calculateFootprintOverallBoundRectangle();
			
			float x,y,dx,dy;
			x=MotionEventCompat.getX(event, pointerIndex);
			y=MotionEventCompat.getY(event, pointerIndex);
			
			dx =  x- mLastTouchX;
	        dy =  y- mLastTouchY; 
	        
	        mLastTouchX=x;mLastTouchY=y;
	        
	        mICFootprintRender.offsetFootprintAndRecalculateRender(dx,dy,this.getWidth(),this.getHeight(),15,mDisplayMetrics);
			//dx and dy are the offset needed in centiMil.
//			float dx = ICFootprint.CentiMil.PixelToCentiMil(
//					MotionEventCompat.getX(event, pointerIndex),
//					mDisplayMetrics.xdpi) - rect.centerX();
//			float dy = ICFootprint.CentiMil.PixelToCentiMil(
//					MotionEventCompat.getY(event, pointerIndex),
//					mDisplayMetrics.ydpi) - rect.centerY();
//
//			footprint.offsetTheFootprint(dx, dy);
			
//			mICFootprintRender = createFootprintRender(mICFootprint,
//					mDisplayMetrics);
//			mICFootprintRender.setLayerVisible(ICFootprintRender.LAYER_COPPER,
//					true);
//			mICFootprintRender.setLayerVisible(ICFootprintRender.LAYER_DRAFT,
//					true);
//			mICFootprintRender.setLayerVisible(ICFootprintRender.LAYER_DRILL,
//					true);
//			mICFootprintRender.setLayerVisible(ICFootprintRender.LAYER_MASK,
//					false);
//			// render.setLayerVisible(ICFootprintRender.LAYER_CLEARANCE, true);
//			mICFootprintRender.setLayerColor(ICFootprintRender.LAYER_COPPER,
//					getResources().getColor(R.color.Black));
//			mICFootprintRender.setLayerColor(ICFootprintRender.LAYER_DRAFT,
//					getResources().getColor(R.color.Green));
//			mICFootprintRender.setLayerColor(ICFootprintRender.LAYER_DRILL,
//					getResources().getColor(R.color.Red));
//			mICFootprintRender.setLayerColor(ICFootprintRender.LAYER_MASK,
//					getResources().getColor(R.color.Red));
			this.invalidate();
		}

		case MotionEvent.ACTION_UP:

		case MotionEvent.ACTION_CANCEL:

		case MotionEvent.ACTION_POINTER_UP:

		}
		return true;
	}
}
