package cn.songshan99.realicfootprint;

import java.io.IOException;
import java.io.InputStream;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import cn.songshan99.FootprintParser.FootprintParserLexer;
import cn.songshan99.FootprintParser.FootprintParserParser;
import cn.songshan99.FootprintParser.FootprintParserParser.ElementContext;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ICFootprintView extends View {

	private ICFootprint mICFootprint;
	private ICFootprintRender mICFootprintRender;
	
	public void setmICFootprintRender(ICFootprintRender mICFootprintRender) {
		this.mICFootprintRender = mICFootprintRender;
	}

	public ICFootprintView(Context context) {
		super(context);
		if(isInEditMode()) return;
		// TODO Auto-generated constructor stub
	}

	public ICFootprintView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if(isInEditMode()) return;
		// TODO Auto-generated constructor stub
	}

	public ICFootprintView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(isInEditMode()) return;
		// TODO Auto-generated constructor stub
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
		mICFootprint = footprint;
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
	
	public static ICFootprintRender createFootprintRender(ICFootprint footprint, float dpi){
		ICFootprintRender render = new ICFootprintRender();
		render.calculateAllLayers(footprint, dpi);
		return render;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO: implement the slide and zoom movement.
		return super.onTouchEvent(event);
	}
}
