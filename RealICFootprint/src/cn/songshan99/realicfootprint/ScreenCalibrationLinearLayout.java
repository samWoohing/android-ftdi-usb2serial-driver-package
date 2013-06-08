package cn.songshan99.realicfootprint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.LinearLayout;


public class ScreenCalibrationLinearLayout extends LinearLayout {
	
	private Paint mStrokePaint = new Paint(){
		{
		setColor(android.graphics.Color.BLACK);//TODO: choose better color?
		setStyle(Paint.Style.STROKE);
		setStrokeWidth(0);
		setAlpha(255);
		setAntiAlias(true);
		}
	};
	
	private static float mWidthArrowLocation = 0.1f;
	private static float mHeightArrowLocation = 0.9f;
	private static float mArrowSize = 0.05f;

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
	}
	
	private float[] getWidthArrow(Canvas canvas){
		float width, height, widthArrow[], arrowY, headOffset;
		widthArrow = new float[20];
		width = canvas.getWidth();
		height = canvas.getHeight();
		
		arrowY = this.mWidthArrowLocation*height;
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
		width = canvas.getWidth();
		height = canvas.getHeight();
		
		arrowX = this.mWidthArrowLocation*width;
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
		heightArrow[13]= height-headOffset;
		heightArrow[14]= arrowX-headOffset;
		heightArrow[15]= height-headOffset;
		
		heightArrow[16]= arrowX;
		heightArrow[17]= height-headOffset;
		heightArrow[18]= arrowX+headOffset;
		heightArrow[19]= height-headOffset;
		
		return heightArrow;
	}
}
