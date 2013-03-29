package cn.songshan99.AWGReference;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class AWGDisplayView extends View {

	private double mDiameter;//diameter in mm!
	private DisplayMetrics mDisplayMetrics;
	private ShapeDrawable mBackgroundRectangleDrawable;
	
	public AWGDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(isInEditMode()) return;
		
		mDiameter=0;
		//get display metrics, dpi and relative dpi
		mDisplayMetrics = new DisplayMetrics();
		Activity hostactivity = (Activity) getContext();
		hostactivity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		

	    mBackgroundRectangleDrawable = new ShapeDrawable(new RectShape());
	    mBackgroundRectangleDrawable.getPaint().setColor(getResources().getColor(R.color.White));
	    
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//get screen width, height
		int width = getWidth();
		int height = getHeight();
		//set the canvas to blank
		mBackgroundRectangleDrawable.setBounds(0, 0, width, height);
		mBackgroundRectangleDrawable.draw(canvas);
		
		if(mDiameter<=0) return;
		
		//requestLayout();
		//get the diameter
		//draw the actual wire size, call canvas.drawpath()
		
		
	}
	
	public void setAWGWire(double diameter_in_mm){
		mDiameter=diameter_in_mm;

		invalidate();
		requestLayout();
		
		// calculate the path here
		
	}
	
	private Path calculateCopperPath(double diameter_in_mm, double width, double height){
		Path path = new Path();
		
		//calculate 4 points and radius of the arc
		
		//use lineto method to complete the path
		
		return path;
	}
	
	private Path calculateInsulationPath(double diameter_in_mm, double width, double height){
		Path path = new Path();
		return path;
	}
	
}
