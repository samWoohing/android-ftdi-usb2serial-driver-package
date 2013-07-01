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
	private boolean mLockICFootprint=false;
	private static float BORDER_MARGIN=15.0f;
	
	public static final int DIR_CW=-1;
	public static final int DIR_CCW=1;
	
	public boolean ismLockICFootprint() {
		return mLockICFootprint;
	}


	public void setmLockICFootprint(boolean mLockICFootprint) {
		this.mLockICFootprint = mLockICFootprint;
	}

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
		//TODO: change to read local calibrated display metrics
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
		float dx, dy;//TODO: separate xdpi and ydpi?
		dx = ICFootprint.CentiMil.PixelToCentiMil(getWidth()/2, mDisplayMetrics.xdpi);
		dy = ICFootprint.CentiMil.PixelToCentiMil(getHeight()/2, mDisplayMetrics.ydpi);
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
		int pointerIndex;
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
			if(mLockICFootprint) return true;//Do nothing if the IC is locked.
			// Find the index of the active pointer and fetch its position
			//mActivePointerId = MotionEventCompat.getPointerId(event, 0);//always track the first finger
			pointerIndex = MotionEventCompat.findPointerIndex(event,
					mActivePointerId);
			if(pointerIndex == -1)pointerIndex = MotionEventCompat.findPointerIndex(event,
					MotionEventCompat.getPointerId(event, 0));//Note: this is try to fix the exception when getX canoot find the index.
			
			ICFootprint footprint = mICFootprintRender.getmICFootprint();
			RectF rect = footprint.calculateFootprintOverallBoundRectangle();
			
			float x,y,dx,dy;
			x=MotionEventCompat.getX(event, pointerIndex);
			y=MotionEventCompat.getY(event, pointerIndex);
			
			dx =  x- mLastTouchX;
	        dy =  y- mLastTouchY; 
	        
	        mLastTouchX=x;mLastTouchY=y;
	        
	        mICFootprintRender.offsetFootprintAndRecalculateRender(dx,dy,this.getWidth(),this.getHeight(),BORDER_MARGIN,mDisplayMetrics);
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
	
	public void rotateICFootprint(int dir){
		//rotate the footprint
		mICFootprintRender.rotateICFootprint(dir, mDisplayMetrics);
		//check if it goes out of the screen, if yes, put it back
		//mICFootprintRender.offsetFootprintAndRecalculateRender(0, 0, this.getWidth(),this.getHeight(),BORDER_MARGIN,mDisplayMetrics);
		//
		this.invalidate();
	}
	
	public void setLockICFootprint(boolean isLocked){
		mLockICFootprint = isLocked;
	}
	
	public void updateDisplayMetrics(DisplayMetrics dm){
		mDisplayMetrics = dm;
		mICFootprintRender.recalculateAllLayers(mDisplayMetrics);
		this.invalidate();
	}
}
