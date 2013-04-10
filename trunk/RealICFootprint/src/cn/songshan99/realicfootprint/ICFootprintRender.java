package cn.songshan99.realicfootprint;

import android.graphics.Path;

/**
 * The Class ICFootprintRender.
 * This class generate the paths that can be drawn on screen for a given footprint.
 */
public class ICFootprintRender {
	private PathPaint mCopper,mDrill, mClearance, mDraftLine;
	
	
	public void rendorFootprint(ICFootprint footprint){
		//first, center the footprint
		//then, navigate through pins and draft lines to  
	}
	
	public class PathPaint{
		public static final int TYPE_COPPER = 1;
		public static final int TYPE_DRILL = 2;
		public static final int TYPE_CLEARANCE = 3;
		public static final int TYPE_DRAFTLINE= 4;
	}
}
