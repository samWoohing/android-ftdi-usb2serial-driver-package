package cn.songshan99.realicfootprint;

import cn.songshan99.realicfootprint.ICFootprint.Pad;
import cn.songshan99.realicfootprint.ICFootprint.Pin;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * The Class ICFootprintRender.
 * This class generate the paths that can be drawn on screen for a given footprint.
 */
public class ICFootprintRender {
	public static final int LAYER_DRAFT=5;
	public static final int LAYER_MASK=4;
	public static final int LAYER_CLEARANCE=3;
	public static final int LAYER_DRILL=2;
	public static final int LAYER_COPPER=1;
	
	private PathPaint mCopper,mDrill, mClearance, mDraftLine;
	
	private Color mCopperColor,mDrillColor,mClearanceColor,mDraftLineColor;
	
	public void rendorFootprint(ICFootprint footprint){
		//first, center the footprint
		//then, navigate through pins and draft lines to  
	}
	
	public class PathPaint{
		public static final int TYPE_COPPER = 1;
		public static final int TYPE_DRILL = 2;
		public static final int TYPE_CLEARANCE = 3;
		public static final int TYPE_DRAFTLINE= 4;
		public Path mPath;
		public Paint mPaint;
		public PathPaint(Path pth, Paint pnt){
			mPath=pth;mPaint=pnt;
		}
	}
	
	//Odd-even rule is preferred to fill the shapes!
	private void drawOnePin(Pin pin){
		//draw in copper
		//draw in drill
		//draw in clearance
	}
	
	private void drawOnePad(Pad pad){
		//draw in copper
		//draw in clearance
	}
	
	private void drawDraftLine(){
		//find the corrisponding width in ArrayList
		
	}
}
