package cn.songshan99.realicfootprint;

import java.util.ArrayList;
import static java.lang.Math.*;

import cn.songshan99.realicfootprint.ICFootprint.ElementArc;
import cn.songshan99.realicfootprint.ICFootprint.ElementLine;
import cn.songshan99.realicfootprint.ICFootprint.Pad;
import cn.songshan99.realicfootprint.ICFootprint.Pin;
import cn.songshan99.realicfootprint.ICFootprint.PinOrPadOrDraftLine;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import static android.util.FloatMath.*;

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
		
		public Path mPath;
		public Paint mPaint;
		public PathPaint(Path pth, Paint pnt){
			mPath=pth;mPaint=pnt;
		}
	}
	
	public class ICDisplayLayer{
		
		public int mType;
		ArrayList<PathPaint> mListPathPaint;
		
		public ICDisplayLayer(){
			//mListPathPaint = new ArrayList<PathPaint>();
		}
		
		public void AddPathPaint(PathPaint pp){
			if(pp!=null && mListPathPaint!=null){
				mListPathPaint.add(pp);
			}
		}
	}
	
	private void drawOctal(float dpiX, float dpiY, float dpiR, Path.Direction dir, Path path){
		float delta=(45f/180f*(float)java.lang.Math.PI);
		if(dir == Path.Direction.CW) delta *=-1;
		float x=0.0f,y=0.0f,theta=0.0f;
		for(int i=0; i<=7; i++){
			theta= (i*delta+22.5f/180f*(float)java.lang.Math.PI);
			x=-cos(theta)*dpiR+dpiX;
			y=sin(theta)*dpiR+dpiY;
			if(i==0)path.moveTo(x, y);
			else path.lineTo(x, y);
		}
		path.close();
	}
	
	//NOTE: Odd-even rule is preferred to fill the shapes!
	/**
	 * Generate the lines for a pin in a given layer, and a given dpi. Add the lines to a given path
	 *
	 * @param pin the pin
	 * @param layer: the copper or drill or clearance or mask layer
	 * @param dpi: pixel per inch for the current display
	 * @param path: the path into which the lines are added to
	 */
	private void PinToPath(Pin pin, int layer, float dpi, Path path){
		//directly add the drawing into path
		float dpiX,dpiY,dpiR_outer,dpiR_inner;
		dpiX=ICFootprint.CentiMil.CentiMilToPixel(pin.aX, dpi);
		dpiY=ICFootprint.CentiMil.CentiMilToPixel(pin.aY, dpi);
		
		switch(layer){
		case LAYER_COPPER:
			dpiR_outer = ICFootprint.CentiMil.CentiMilToPixel(pin.Thickness,dpi)/2;
			dpiR_inner = ICFootprint.CentiMil.CentiMilToPixel(pin.Drill,dpi)/2;
			break;
			
		case LAYER_DRILL:
			dpiR_outer = ICFootprint.CentiMil.CentiMilToPixel(pin.Drill,dpi)/2;
			dpiR_inner = 0;
			break;
			
		case LAYER_CLEARANCE:
			dpiR_outer = ICFootprint.CentiMil.CentiMilToPixel(pin.Thickness+pin.Clearance,dpi)/2;
			dpiR_inner = ICFootprint.CentiMil.CentiMilToPixel(pin.Thickness,dpi)/2;
			break;
			
		case LAYER_MASK:
			dpiR_outer = ICFootprint.CentiMil.CentiMilToPixel(pin.Mask,dpi)/2;
			dpiR_inner = 0;//TODO: verify if mask's inner is drill or is 0...
			//dpiR_inner = ICFootprint.CentiMil.CentiMilToPixel(pin.Drill,dpi)/2;
			break;
			
		default:
			return;//no need to perform later drawing actions
		}
		
		switch(pin.getShape()){
		case ICFootprint.PinOrPadOrDraftLine.SHAPE_ROUND:
			if(dpiR_outer!=0)
				path.addCircle(dpiX, dpiY, dpiR_outer, Path.Direction.CCW);
			if(dpiR_inner!=0)
				path.addCircle(dpiX, dpiY, dpiR_inner, Path.Direction.CW);
			return;
			
		case ICFootprint.PinOrPadOrDraftLine.SHAPE_RECT:
			if(dpiR_outer!=0)
				path.addRect(dpiX-dpiR_outer, dpiY-dpiR_outer, dpiX+dpiR_outer, dpiY+dpiR_outer, Path.Direction.CCW);
			if(dpiR_inner!=0)
				path.addRect(dpiX-dpiR_inner, dpiY-dpiR_inner, dpiX+dpiR_inner, dpiY+dpiR_inner, Path.Direction.CW);
			return;

		case ICFootprint.PinOrPadOrDraftLine.SHAPE_OCT:
			if(dpiR_outer!=0){
				drawOctal(dpiX,dpiY,dpiR_outer,Path.Direction.CCW,path);
			}
			if(dpiR_inner!=0){
				drawOctal(dpiX,dpiY,dpiR_inner,Path.Direction.CW,path);
			}
			return;
			
		default:
			return;
		}
	}
	
	/**
	 * Generate the lines for a pad in a given layer, and a given dpi. Add the lines to a given path
	 *
	 * @param pad the pad
	 * @param layer: the copper or clearance or mask layer
	 * @param dpi: pixel per inch for the current display
	 * @param path: the path into which the lines are added to
	 */
	private void PadToPath(Pad pad, int layer, float dpi, Path path){
		float leftX,rightX,topY,bottomY,dpiR_outer,dpiR_inner;
		leftX	=min(pad.aX1,pad.aX2);
		rightX	=max(pad.aX1,pad.aX2);
		topY	=min(pad.aY1,pad.aY2);
		bottomY	=max(pad.aY1,pad.aY2);
		
		switch(layer){
		case LAYER_COPPER:
			dpiR_outer = ICFootprint.CentiMil.CentiMilToPixel(pad.Thickness,dpi)/2;
			dpiR_inner = 0;
			break;
			
		case LAYER_CLEARANCE:
			dpiR_outer = ICFootprint.CentiMil.CentiMilToPixel(pad.Thickness+pad.Clearance,dpi)/2;
			dpiR_inner = ICFootprint.CentiMil.CentiMilToPixel(pad.Thickness,dpi)/2;
			break;
			
		case LAYER_MASK:
			dpiR_outer = ICFootprint.CentiMil.CentiMilToPixel(pad.Mask,dpi)/2;
			dpiR_inner = 0;//TODO: verify if mask's inner is drill or is 0...
			//dpiR_inner = ICFootprint.CentiMil.CentiMilToPixel(pad.Drill,dpi)/2;
			break;
			
		default:
			return;//no need to perform later drawing actions
		}
		
		RectF outerRect=null,innerRect=null;
		if(dpiR_outer!=0)
			outerRect = new RectF(leftX-dpiR_outer, topY-dpiR_outer, rightX+dpiR_outer, bottomY+dpiR_outer);
		if(dpiR_inner!=0)
			innerRect = new RectF(leftX-dpiR_inner, topY-dpiR_inner, rightX+dpiR_inner, bottomY+dpiR_inner);
		
		switch(pad.getShape()){
		case ICFootprint.PinOrPadOrDraftLine.SHAPE_ROUND:
			if(dpiR_outer!=0)
				path.addRoundRect(outerRect, dpiR_outer, dpiR_outer, Path.Direction.CCW);
			if(dpiR_inner!=0)
				path.addRoundRect(innerRect, dpiR_inner, dpiR_inner, Path.Direction.CW);
			return;
		case ICFootprint.PinOrPadOrDraftLine.SHAPE_RECT:
			if(outerRect!=null)
				path.addRect(outerRect, Path.Direction.CCW);
			if(innerRect!=null)
				path.addRect(innerRect, Path.Direction.CW);
			return;
		default:
			return;
		}
	}
	
	private void lineToPath(ElementLine line, float dpi, Path path){
		
	}
	
	private void arcToPath(ElementArc arc, float dpi, Path path){
		
	}

	public ICDisplayLayer calculateLayer(ICFootprint footprint, int layer, float dpi){
		ArrayList<PinOrPadOrDraftLine> pinorpadlist = footprint.getmListPinOrPad();
		ArrayList<PinOrPadOrDraftLine> draftlinelist = footprint.getmListDraftLine();
		
		ICDisplayLayer displayLayer = new ICDisplayLayer();
		displayLayer.mType = layer;
		displayLayer.mListPathPaint= new ArrayList<PathPaint>();
		
		switch(layer){
		case LAYER_COPPER:
		case LAYER_DRILL:
		case LAYER_MASK:
		case LAYER_CLEARANCE:
			Path pinpath,icpath;
			if(pinorpadlist == null)return null;
			icpath = new Path();
			for(PinOrPadOrDraftLine pinpad:pinorpadlist){
				if(pinpad.getType()==PinOrPadOrDraftLine.TYPE_PAD)
					PadToPath((Pad) pinpad, layer, dpi, icpath);
				else if(pinpad.getType()==PinOrPadOrDraftLine.TYPE_PIN)
					PinToPath((Pin) pinpad, layer, dpi, icpath);
				else return null;
			}
			//add the new pathpaint to displayLayer, note that unlike draftline, we can only get one pathpaint...
			displayLayer.mListPathPaint.add(new PathPaint(icpath,null));//TODO: add the proper paint here...
			return displayLayer;
			
		case LAYER_DRAFT:
			if(draftlinelist == null)return null;
			
			for(PinOrPadOrDraftLine draftline:draftlinelist){
				float thickness;
				if(draftline.getType()== PinOrPadOrDraftLine.TYPE_ARC)
					thickness=((ElementArc)draftline).Thickness;
				else if(draftline.getType()== PinOrPadOrDraftLine.TYPE_LINE)
					thickness=((ElementLine)draftline).Thickness;
				else
					return null;
				
				//convert thickness from centimil to pixel
				thickness = ICFootprint.CentiMil.CentiMilToPixel(thickness, dpi);
				
				//find if a given width already exists,
				PathPaint pp = null;
				for(PathPaint onepp: displayLayer.mListPathPaint){
					if(onepp.mPaint.getStrokeWidth()==thickness)
						pp=onepp;
				}
				
				if(pp==null){
					//of no create new PathPaint and begin to add line
					pp = new PathPaint(new Path(), new Paint());//TODO: correct the paint
					displayLayer.mListPathPaint.add(pp);
				}
				
				//if yes,(found existing width) continue adding lines to path
				//pp is the PathPaint object for the given draftline.
				//convert the line, arc to dpi based paths
				if(draftline.getType()==PinOrPadOrDraftLine.TYPE_ARC)
					arcToPath((ElementArc)draftline,dpi,pp.mPath);
				else if(draftline.getType()==PinOrPadOrDraftLine.TYPE_LINE)
					lineToPath((ElementLine)draftline,dpi,pp.mPath);
				else
					return null;	
			}
			return displayLayer;
			
		default:
			return null;
		}
	}
}
