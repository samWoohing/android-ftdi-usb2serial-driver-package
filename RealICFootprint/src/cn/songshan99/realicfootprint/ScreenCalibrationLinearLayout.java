package cn.songshan99.realicfootprint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;



public class ScreenCalibrationLinearLayout extends LinearLayout {
	
	private static float mWidthArrowLocation = 0.1f;
	private static float mHeightArrowLocation = 0.9f;
	private static float mArrowSize = 0.05f;
	private static float mWidthTextLocation = 0.3f;
	private static float mHeightTextLocation = 0.7f;
	private static float mTextOffset = 0.01f;//How far away the text is from the arrow
	
	private Paint mStrokePaint = new Paint(){
		{
		setColor(getResources()
				.getColor(R.color.RoyalBlue));//TODO: choose better color?
		setStyle(Paint.Style.STROKE);
		setStrokeWidth(2);
		setTextSize(20);//TODO: detail this number
		setAlpha(255);
		setAntiAlias(true);
		}
	};

	
	private static final float OneInchToMM=25.4f;
	private static final String STR_INCH = "inch";
	private static final String STR_MM = "mm";
	private static final String STR_H = "Height:";
	private static final String STR_W = "Width:";

	private float xDPI=120, yDPI=120;
	private int mDisplayUnit = ScreenCalibrationActivity.UNIT_MM;
	
	public ScreenCalibrationLinearLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public ScreenCalibrationLinearLayout(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ScreenCalibrationLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setDisplayUnit(int unit, float xdpi, float ydpi){
		mDisplayUnit = unit;
		xDPI = xdpi; yDPI = ydpi;
		this.invalidate();
	}
	
	public float calculateXDPI(float measured_x, int unit) {
		// convert the unit to inch
		if (unit == ScreenCalibrationActivity.UNIT_MM)
			return (float) this.getWidth() / (measured_x / OneInchToMM);
		else if (unit == ScreenCalibrationActivity.UNIT_INCH)
			return (float) this.getWidth() / measured_x;
		else
			return 0.0f;
	}
	
	public float calculateYDPI(float measured_y, int unit) {
		// convert the unit to inch
		if (unit == ScreenCalibrationActivity.UNIT_MM)
			return (float) this.getHeight() / (measured_y / OneInchToMM);
		else if (unit == ScreenCalibrationActivity.UNIT_INCH)
			return (float) this.getHeight() / measured_y;
		else
			return 0.0f;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//draw the width and height arrows.
		drawDimIndications(canvas);
		
		
	}
	
	private void drawDimIndications(Canvas canvas){
		float widthArrow[],heightArrow[];
		
		widthArrow= getWidthArrow(canvas);
		heightArrow= getHeightArrow(canvas);
		
		canvas.drawLines(widthArrow, mStrokePaint);
		canvas.drawLines(heightArrow, mStrokePaint);
		
		drawWidthText(canvas);
		drawHeightText(canvas);
	}
	
	private void drawWidthText(Canvas canvas){
		float x,y;
		x=this.getWidth()*mWidthTextLocation;
		y=(mWidthArrowLocation-mTextOffset)*this.getHeight();
		canvas.drawText(getWidthText(), x, y, mStrokePaint);
	}
	
	private void drawHeightText(Canvas canvas){
		float x,y;
		y=this.getHeight()*mHeightTextLocation;
		x=(mHeightArrowLocation-mTextOffset)*this.getWidth();
		canvas.rotate(-90,x,y);
		canvas.drawText(getHeightText(), x, y, mStrokePaint);
		canvas.restore();
	}
	
	private String getWidthText(){
		float width;
		if(this.mDisplayUnit==ScreenCalibrationActivity.UNIT_INCH){
			width = this.getWidth() / xDPI;
			return STR_W+" "+String.format("%.2f", width) + " " + STR_INCH;
		}else if(this.mDisplayUnit==ScreenCalibrationActivity.UNIT_MM){
			width = this.getWidth() / xDPI * OneInchToMM;
			return STR_W+" "+String.format("%.1f", width) + " " + STR_MM;
		}else{
			return "";
		}
	}
	
	private String getHeightText(){
		float height;
		if(this.mDisplayUnit==ScreenCalibrationActivity.UNIT_INCH){
			height = this.getHeight() / yDPI;
			return STR_H+" "+String.format("%.2f", height) + " " + STR_INCH;
		}else if(this.mDisplayUnit==ScreenCalibrationActivity.UNIT_MM){
			height = this.getHeight() / yDPI * OneInchToMM;
			return STR_H+" "+String.format("%.1f", height) + " " + STR_MM;
		}else{
			return "";
		}
	}
	
	private float[] getWidthArrow(Canvas canvas){
		float width, height, widthArrow[], arrowY, headOffset;
		widthArrow = new float[20];
		width = this.getWidth();
		height = this.getHeight();
		
		arrowY = mWidthArrowLocation*height;
		headOffset = mArrowSize * width;
				
		widthArrow[0]= 0;
		widthArrow[1]= arrowY;
		widthArrow[2]= width;
		widthArrow[3]= arrowY;
		
		widthArrow[4]= 0;
		widthArrow[5]= arrowY;
		widthArrow[6]= headOffset;
		widthArrow[7]= arrowY-headOffset;
		
		widthArrow[8]= 0;
		widthArrow[9]= arrowY;
		widthArrow[10]= headOffset;
		widthArrow[11]= arrowY+headOffset;
		
		widthArrow[12]= width;
		widthArrow[13]= arrowY;
		widthArrow[14]= width-headOffset;
		widthArrow[15]= arrowY-headOffset;
		
		widthArrow[16]= width;
		widthArrow[17]= arrowY;
		widthArrow[18]= width-headOffset;
		widthArrow[19]= arrowY+headOffset;
		
		return widthArrow;
	}
	
	private float[] getHeightArrow(Canvas canvas){
		float width, height, heightArrow[], arrowX, headOffset;
		heightArrow = new float[20];
		width = this.getWidth();
		height = this.getHeight();
		
		arrowX = mHeightArrowLocation*width;
		headOffset = mArrowSize * width;
		
		heightArrow[0]= arrowX;
		heightArrow[1]= 0;
		heightArrow[2]= arrowX;
		heightArrow[3]= height;
		
		heightArrow[4]= arrowX;
		heightArrow[5]= 0;
		heightArrow[6]= arrowX-headOffset;
		heightArrow[7]= headOffset;
		
		heightArrow[8]= arrowX;
		heightArrow[9]= 0;
		heightArrow[10]= arrowX+headOffset;
		heightArrow[11]= headOffset;
		
		heightArrow[12]= arrowX;
		heightArrow[13]= height;
		heightArrow[14]= arrowX-headOffset;
		heightArrow[15]= height-headOffset;
		
		heightArrow[16]= arrowX;
		heightArrow[17]= height;
		heightArrow[18]= arrowX+headOffset;
		heightArrow[19]= height-headOffset;
		
		return heightArrow;
	}
}
