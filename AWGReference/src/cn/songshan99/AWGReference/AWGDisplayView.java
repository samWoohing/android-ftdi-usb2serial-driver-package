package cn.songshan99.AWGReference;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class AWGDisplayView extends View {

	private float mDiameter;//diameter in mm!
	private DisplayMetrics mDisplayMetrics;
	private ShapeDrawable mBackgroundRectangleDrawable;
	private Paint mStrokePaint;
	private Paint mCopperGradientPaint;
	private Paint mInsulationGradientPaint;
	
	//constants for drawings
	private static float copperLength = 0.6f;
	private static float copperXPos = 0.2f;
	private static float insulationLength = 0.2f;
	private static float insulationDiameterRatio = 1.5f;
	private static float insulationXPos = 0f;
	private static float inch2mm = 25.4f;
	private static float arcRatio = 0.3f;
	
	public AWGDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(isInEditMode()) return;
		
		mDiameter=0;
		//get display metrics, dpi and relative dpi
		mDisplayMetrics = new DisplayMetrics();
		Activity hostactivity = (Activity) getContext();
		hostactivity.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		
		mStrokePaint = new Paint();
		mStrokePaint.setColor(android.graphics.Color.BLACK);
		mStrokePaint.setStyle(Paint.Style.STROKE);
		mStrokePaint.setStrokeWidth(0);
		mStrokePaint.setAlpha(255);
		mStrokePaint.setAntiAlias(true);

		mCopperGradientPaint = new Paint();
		mCopperGradientPaint.setStyle(Paint.Style.FILL);
		mCopperGradientPaint.setColor(getResources().getColor(R.color.White));
		mCopperGradientPaint.setAlpha(255);
		
		mInsulationGradientPaint = new Paint();
		mInsulationGradientPaint.setStyle(Paint.Style.FILL);
		mInsulationGradientPaint.setColor(getResources().getColor(R.color.White));
		mInsulationGradientPaint.setAlpha(255);
		
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
		
		//if(mDiameter<=0) return;
		
		//requestLayout();
		//draw the actual wire size, call canvas.drawpath()
		canvas.drawPath(calculateCopperPath(0.5f,width,height), mStrokePaint);
		canvas.drawPath(calculateCopperPath(0.5f,width,height), mCopperGradientPaint);
		canvas.drawPath(calculateInsulationPath(0.5f,width,height), mStrokePaint);
		canvas.drawPath(calculateInsulationPath(0.5f,width,height), mInsulationGradientPaint);
		//test purpose
		
	}
	
	public void setAWGWire(float diameter_in_mm){
		mDiameter=diameter_in_mm;

		invalidate();
		requestLayout();
		
		// calculate the path here
		
	}
	
	private Path calculateCopperPath(float diameter_in_mm, float width, float height){
		Path path = new Path();
		
		float diameter_in_px = diameter_in_mm * mDisplayMetrics.ydpi/inch2mm;
		
		//calculate 4 points and radius of the arc
		float upperLeftX = copperXPos * width;
		float upperLeftY = height/2 - diameter_in_px/2;
		float upperRightX = (copperXPos + copperLength) * width;
		float upperRightY = height/2 - diameter_in_px/2;
		float lowerRightX = (copperXPos + copperLength) * width;
		float lowerRightY = height/2 + diameter_in_px/2;
		float lowerLeftX = copperXPos * width;
		float lowerLeftY = height/2 + diameter_in_px/2;		
		
		RectF rectf = new RectF(	upperRightX - diameter_in_px * arcRatio,
								upperRightY,
								lowerRightX + diameter_in_px * arcRatio,
								lowerRightY);

		
		//use lineto method to complete the path
		path.moveTo(upperLeftX, upperLeftY);
		path.lineTo(upperRightX, upperRightY);
		path.arcTo(rectf, -90, 180);
		//path.lineTo(lowerRightX, lowerRightY);
		path.lineTo(lowerLeftX, lowerLeftY);
		path.close();
		
		//set the shader
		mCopperGradientPaint.setShader(new LinearGradient((upperLeftX+upperRightX)/2, height/2, (upperLeftX+upperRightX)/2, lowerLeftY, 
				getResources().getColor(R.color.Goldenrod)|(0xFF<<24), 0x372B09|(0xFF<<24), Shader.TileMode.MIRROR));
		return path;
	}
	
	private Path calculateInsulationPath(float diameter_in_mm, float width, float height){
		Path path = new Path();
		
		float diameter_in_px = insulationDiameterRatio * diameter_in_mm * mDisplayMetrics.ydpi/inch2mm;
		// calculate 4 points and radius of the arc
		float upperLeftX = insulationXPos * width;
		float upperLeftY = height / 2 - diameter_in_px / 2;
		float upperRightX = (insulationXPos + insulationLength) * width;
		float upperRightY = height / 2 - diameter_in_px / 2;
		float lowerRightX = (insulationXPos + insulationLength) * width;
		float lowerRightY = height / 2 + diameter_in_px / 2;
		float lowerLeftX = insulationXPos * width;
		float lowerLeftY = height / 2 + diameter_in_px / 2;
		
		RectF rectf = new RectF(upperRightX - diameter_in_px * arcRatio,
								upperRightY,
								lowerRightX + diameter_in_px * arcRatio,
								lowerRightY);

		// use lineto method to complete the path
		path.moveTo(upperLeftX, upperLeftY);
		path.lineTo(upperRightX, upperRightY);
		path.arcTo(rectf, -90, 180);
		//path.lineTo(lowerRightX, lowerRightY);
		path.lineTo(lowerLeftX, lowerLeftY);
		path.close();
		
		//set the shader
		mInsulationGradientPaint.setShader(new LinearGradient((upperLeftX+upperRightX)/2, height/2, (upperLeftX+upperRightX)/2, lowerLeftY, 
				getResources().getColor(R.color.DodgerBlue)|(0xFF<<24), getResources().getColor(R.color.DarkBlue)|(0xFF<<24), Shader.TileMode.MIRROR));
		return path;
	}
	
}
