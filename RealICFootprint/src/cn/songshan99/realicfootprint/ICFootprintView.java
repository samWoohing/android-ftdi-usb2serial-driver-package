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

	private ICFootprint mICFootprint;
	private ICFootprintRender mICFootprintRender;
	
	public DisplayMetrics mDisplayMetrics;//TODO: change to private later
	
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
		dx = ICFootprint.CentiMil.PixelToCentiMil(getWidth(), mDisplayMetrics.densityDpi);
		dy = ICFootprint.CentiMil.PixelToCentiMil(getHeight(), mDisplayMetrics.densityDpi);
		footprint.offsetTheFootprint(dx, dy);
		mICFootprint = footprint;
		mICFootprintRender = ICFootprintView.createFootprintRender(mICFootprint, mDisplayMetrics);
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
	
	private static ICFootprintRender createFootprintRender(ICFootprint footprint, DisplayMetrics displayMetrics){
		ICFootprintRender render = new ICFootprintRender();
		render.calculateAllLayers(footprint, displayMetrics);
		return render;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event){     // Let the ScaleGestureDetector inspect all events.
             
    final int action = MotionEventCompat.getActionMasked(event); 
        
    switch (action) { 
    case MotionEvent.ACTION_DOWN:
    	break;
            
    case MotionEvent.ACTION_MOVE:{
    	// Find the index of the active pointer and fetch its position
        final int pointerIndex = 
                MotionEventCompat.findPointerIndex(event, 0);  
        RectF rect = mICFootprint.calculateFootprintOverallBoundRectangle();
        float dx = ICFootprint.CentiMil.PixelToCentiMil(MotionEventCompat.getX(event, pointerIndex),mDisplayMetrics.xdpi)-rect.centerX();
        float dy = ICFootprint.CentiMil.PixelToCentiMil(MotionEventCompat.getY(event, pointerIndex),mDisplayMetrics.ydpi)-rect.centerY();
        
        mICFootprint.offsetTheFootprint(dx, dy);
        mICFootprintRender = createFootprintRender(mICFootprint, mDisplayMetrics);
        mICFootprintRender.setLayerVisible(ICFootprintRender.LAYER_COPPER, true);
        mICFootprintRender.setLayerVisible(ICFootprintRender.LAYER_DRAFT, true);
        mICFootprintRender.setLayerVisible(ICFootprintRender.LAYER_DRILL, true);
        mICFootprintRender.setLayerVisible(ICFootprintRender.LAYER_MASK, false);
		//render.setLayerVisible(ICFootprintRender.LAYER_CLEARANCE, true);
        mICFootprintRender.setLayerColor(ICFootprintRender.LAYER_COPPER, getResources().getColor(R.color.Black));
        mICFootprintRender.setLayerColor(ICFootprintRender.LAYER_DRAFT,  getResources().getColor(R.color.Green));
        mICFootprintRender.setLayerColor(ICFootprintRender.LAYER_DRILL,  getResources().getColor(R.color.Red));
        mICFootprintRender.setLayerColor(ICFootprintRender.LAYER_MASK,  getResources().getColor(R.color.Red));
        this.invalidate();
    }
    	
            
    case MotionEvent.ACTION_UP: 
            
    case MotionEvent.ACTION_CANCEL:
        
    case MotionEvent.ACTION_POINTER_UP:

    }       
    return true;
}
}
