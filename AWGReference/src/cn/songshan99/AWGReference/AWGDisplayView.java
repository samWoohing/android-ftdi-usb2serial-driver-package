package cn.songshan99.AWGReference;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class AWGDisplayView extends View {

	private double mDiameter;//diameter in mm!
	DisplayMetrics mDisplayMetrics;
	
	public AWGDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mDiameter=0;
		//get display metrics, dpi and relative dpi
		mDisplayMetrics = new DisplayMetrics();
		Activity hostactivity = (Activity) context;
		hostactivity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		if(mDiameter==0){
			//set the canvas to blank
			return;
		}
		
		//get the diameter
		
		//get screen width, height
		int width = getWidth();
		int height = getHeight();
	}
	
	public void setAWGWire(double diameter_in_mm){
		mDiameter=diameter_in_mm;
		invalidate();
		requestLayout();
	}
	
	//define the custom draw, PathShape can be used.
}
