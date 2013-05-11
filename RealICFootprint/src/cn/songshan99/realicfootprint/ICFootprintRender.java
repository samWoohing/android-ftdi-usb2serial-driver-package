package cn.songshan99.realicfootprint;

import java.util.ArrayList;
import static java.lang.Math.*;

import cn.songshan99.FootprintParser.ICFootprint;
import cn.songshan99.FootprintParser.ICFootprint.ElementArc;
import cn.songshan99.FootprintParser.ICFootprint.ElementLine;
import cn.songshan99.FootprintParser.ICFootprint.Pad;
import cn.songshan99.FootprintParser.ICFootprint.Pin;
import cn.songshan99.FootprintParser.ICFootprint.PinOrPadOrDraftLine;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.DisplayMetrics;
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
	
	private ICDisplayLayer mLayerDraft, mLayerMask, mLayerDrill, mLayerCopper;
	//private PathPaint mCopper,mDrill, mClearance, mDraftLine;
	//private Color mCopperColor,mDrillColor,mClearanceColor,mDraftLineColor;
	private ICFootprint mICFootprint;
	
	public ICFootprint getmICFootprint() {
		return mICFootprint;
	}
	
	
	public ICFootprintRender(ICFootprint footprint, DisplayMetrics displayMetrics) {
		if(footprint != null) mICFootprint = footprint;
		else return;
		
		calculateAllLayers(mICFootprint, displayMetrics);
	}


	public static class PathPaint{
		
		public Path mPath;
		public Paint mPaint;
		public PathPaint(Path pth, Paint pnt){
			mPath=pth;mPaint=pnt;
		}
	}
	
	public static class ICDisplayLayer{
		
		public int mType;
		private int mColor;
		private boolean mVisible;
		
		ArrayList<PathPaint> mListPathPaint;
		
		public ICDisplayLayer(){
			//mListPathPaint = new ArrayList<PathPaint>();
		}
		
		public void AddPathPaint(PathPaint pp){
			if(pp!=null && mListPathPaint!=null){
				pp.mPaint.setColor(mColor);//overwrite whatever color setting
				mListPathPaint.add(pp);
			}
		}
		
		public void setColor(int color){
			mColor=color;
			if(mListPathPaint!=null){
				for(PathPaint pp: mListPathPaint){
					pp.mPaint.setColor(color);
				}
			}
		}
		
		public int getColor(){
			return mColor;
		}

		public boolean isVisible() {
			return mVisible;
		}

		public void setVisible(boolean Visible) {
			this.mVisible = Visible;
		}
	}
	
	/**
	 * Draw octal.
	 *
	 * @param dpiX the center x
	 * @param dpiY the center y
	 * @param dpiRX x direction radius
	 * @param dpiRY y direction radius
	 * @param dir the dir
	 * @param path the path
	 */
	private static void drawOctal(float dpiX, float dpiY, float dpiRX, float dpiRY, Path.Direction dir, Path path){
		float delta=(45f/180f*(float)java.lang.Math.PI);
		if(dir == Path.Direction.CW) delta *=-1;
		float x=0.0f,y=0.0f,theta=0.0f,dpiR,xcos,ysin;
		for(int i=0; i<=7; i++){
			theta= (i*delta+22.5f/180f*(float)java.lang.Math.PI);
			xcos = dpiRX * cos(theta);
			ysin = dpiRY * sin(theta);
			dpiR = dpiRX * dpiRY / sqrt(xcos*xcos+ysin*ysin);
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
	private static void PinToPath(Pin pin, int layer, float xdpi, float ydpi, Path path){
		//directly add the drawing into path
		float dpiX,dpiY,dpiR_outer_x,dpiR_inner_x,dpiR_outer_y,dpiR_inner_y;
		dpiX=ICFootprint.CentiMil.CentiMilToPixel(pin.aX, xdpi);
		dpiY=ICFootprint.CentiMil.CentiMilToPixel(pin.aY, ydpi);
		
		switch(layer){
		case LAYER_COPPER:
			dpiR_outer_x = ICFootprint.CentiMil.CentiMilToPixel(pin.Thickness,xdpi)/2;
			dpiR_inner_x = ICFootprint.CentiMil.CentiMilToPixel(pin.Drill,xdpi)/2;
			dpiR_outer_y = ICFootprint.CentiMil.CentiMilToPixel(pin.Thickness,ydpi)/2;
			dpiR_inner_y = ICFootprint.CentiMil.CentiMilToPixel(pin.Drill,ydpi)/2;
			break;
			
		case LAYER_DRILL:
			dpiR_outer_x = ICFootprint.CentiMil.CentiMilToPixel(pin.Drill,xdpi)/2;
			dpiR_inner_x = 0;
			dpiR_outer_y = ICFootprint.CentiMil.CentiMilToPixel(pin.Drill,ydpi)/2;
			dpiR_inner_y = 0;
			break;
			
		case LAYER_CLEARANCE:
			dpiR_outer_x = ICFootprint.CentiMil.CentiMilToPixel(pin.Thickness+pin.Clearance,xdpi)/2;
			dpiR_inner_x = ICFootprint.CentiMil.CentiMilToPixel(pin.Thickness,xdpi)/2;
			dpiR_outer_y = ICFootprint.CentiMil.CentiMilToPixel(pin.Thickness+pin.Clearance,ydpi)/2;
			dpiR_inner_y = ICFootprint.CentiMil.CentiMilToPixel(pin.Thickness,ydpi)/2;
			break;
			
		case LAYER_MASK:
			dpiR_outer_x = ICFootprint.CentiMil.CentiMilToPixel(pin.Mask,xdpi)/2;
			dpiR_inner_x = 0;//TODO: verify if mask's inner is drill or is 0...
			dpiR_outer_y = ICFootprint.CentiMil.CentiMilToPixel(pin.Mask,ydpi)/2;
			dpiR_inner_y = 0;//TODO: verify if mask's inner is drill or is 0...
			break;
			
		default:
			return;//no need to perform later drawing actions
		}
		
		RectF outerRect=null,innerRect=null;
		if(dpiR_outer_x!=0 && dpiR_outer_y!=0)
			outerRect = new RectF(dpiX-dpiR_outer_x, dpiY-dpiR_outer_y, dpiX+dpiR_outer_x, dpiY+dpiR_outer_y);
		if(dpiR_inner_x!=0 && dpiR_inner_y!=0)
			innerRect = new RectF(dpiX-dpiR_inner_x, dpiY-dpiR_inner_y, dpiX+dpiR_inner_x, dpiY+dpiR_inner_y);
		
		int outershape = (layer==LAYER_DRILL)?ICFootprint.PinOrPadOrDraftLine.SHAPE_ROUND:pin.getShape();//drill is always round shape.
		int innershape = (layer==LAYER_COPPER)?ICFootprint.PinOrPadOrDraftLine.SHAPE_ROUND:pin.getShape();
		if (outerRect != null) {
			switch (outershape) {
			case ICFootprint.PinOrPadOrDraftLine.SHAPE_ROUND:
				path.addOval(outerRect, Path.Direction.CCW);
				return;

			case ICFootprint.PinOrPadOrDraftLine.SHAPE_RECT:
				path.addRect(outerRect, Path.Direction.CCW);
				return;

			case ICFootprint.PinOrPadOrDraftLine.SHAPE_OCT:
				drawOctal(dpiX, dpiY, dpiR_outer_x, dpiR_outer_y,
						Path.Direction.CCW, path);
				return;

			default:
				return;
			}
		}
		
		if (innerRect != null) {
			switch (innershape) {
			case ICFootprint.PinOrPadOrDraftLine.SHAPE_ROUND:
				path.addOval(innerRect, Path.Direction.CW);
				return;

			case ICFootprint.PinOrPadOrDraftLine.SHAPE_RECT:
				path.addRect(innerRect, Path.Direction.CW);
				return;

			case ICFootprint.PinOrPadOrDraftLine.SHAPE_OCT:
				drawOctal(dpiX, dpiY, dpiR_inner_x, dpiR_inner_x,
						Path.Direction.CW, path);
				return;

			default:
				return;
			}
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
	private static void PadToPath(Pad pad, int layer, float xdpi,float ydpi, Path path){
		float leftX,rightX,topY,bottomY,dpiR_outer_x,dpiR_outer_y,dpiR_inner_x,dpiR_inner_y;
		leftX	=ICFootprint.CentiMil.CentiMilToPixel(min(pad.aX1,pad.aX2),xdpi);
		rightX	=ICFootprint.CentiMil.CentiMilToPixel(max(pad.aX1,pad.aX2),xdpi);
		topY	=ICFootprint.CentiMil.CentiMilToPixel(min(pad.aY1,pad.aY2),ydpi);
		bottomY	=ICFootprint.CentiMil.CentiMilToPixel(max(pad.aY1,pad.aY2),ydpi);
		
		switch(layer){
		case LAYER_COPPER:
			dpiR_outer_x = ICFootprint.CentiMil.CentiMilToPixel(pad.Thickness,xdpi)/2;
			dpiR_outer_y = ICFootprint.CentiMil.CentiMilToPixel(pad.Thickness,ydpi)/2;
			dpiR_inner_x = 0;dpiR_inner_y = 0;
			break;
			
		case LAYER_CLEARANCE:
			dpiR_outer_x = ICFootprint.CentiMil.CentiMilToPixel(pad.Thickness+pad.Clearance,xdpi)/2;
			dpiR_outer_y = ICFootprint.CentiMil.CentiMilToPixel(pad.Thickness+pad.Clearance,ydpi)/2;
			dpiR_inner_x = ICFootprint.CentiMil.CentiMilToPixel(pad.Thickness,xdpi)/2;
			dpiR_inner_y = ICFootprint.CentiMil.CentiMilToPixel(pad.Thickness,ydpi)/2;
			break;
			
		case LAYER_MASK:
			dpiR_outer_x = ICFootprint.CentiMil.CentiMilToPixel(pad.Mask,xdpi)/2;
			dpiR_outer_y = ICFootprint.CentiMil.CentiMilToPixel(pad.Mask,ydpi)/2;
			dpiR_inner_x = 0;dpiR_inner_y = 0;//TODO: verify if mask's inner is drill or is 0...
			//dpiR_inner = ICFootprint.CentiMil.CentiMilToPixel(pad.Drill,dpi)/2;
			break;
			
		default:
			return;//no need to perform later drawing actions
		}
		
		RectF outerRect=null,innerRect=null;
		if(dpiR_outer_x!=0 && dpiR_outer_y!=0)
			outerRect = new RectF(leftX-dpiR_outer_x, topY-dpiR_outer_y, rightX+dpiR_outer_x, bottomY+dpiR_outer_y);
		if(dpiR_inner_x!=0 && dpiR_inner_y!=0)
			innerRect = new RectF(leftX-dpiR_inner_x, topY-dpiR_inner_y, rightX+dpiR_inner_x, bottomY+dpiR_inner_y);
		
		switch(pad.getShape()){
		case ICFootprint.PinOrPadOrDraftLine.SHAPE_ROUND:
			if(outerRect!=null)
				path.addRoundRect(outerRect, dpiR_outer_x, dpiR_outer_y, Path.Direction.CCW);
			if(innerRect!=null)
				path.addRoundRect(innerRect, dpiR_inner_x, dpiR_inner_y, Path.Direction.CW);
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
	
	private static void lineToPath(ElementLine line, float xdpi,float ydpi, Path path){
		float dpiX1,dpiY1,dpiX2,dpiY2;
		dpiX1=ICFootprint.CentiMil.CentiMilToPixel(line.aX1, xdpi);
		dpiY1=ICFootprint.CentiMil.CentiMilToPixel(line.aY1, ydpi);
		dpiX2=ICFootprint.CentiMil.CentiMilToPixel(line.aX2, xdpi);
		dpiY2=ICFootprint.CentiMil.CentiMilToPixel(line.aY2, ydpi);
		path.moveTo(dpiX1, dpiY1);
		path.lineTo(dpiX2, dpiY2);
	}
	
	private static void arcToPath(ElementArc arc, float xdpi,float ydpi, Path path){
		//convert gEda convention to Android convention
		//note that gEDA use CCW as positive, while angle zero points left
		//and Android uses CW as positive, while angle zero points right.
		RectF rect = new RectF();
		rect.left 	= ICFootprint.CentiMil.CentiMilToPixel(arc.aX-arc.Width, xdpi);
		rect.right 	= ICFootprint.CentiMil.CentiMilToPixel(arc.aX+arc.Width, xdpi);
		rect.top 	= ICFootprint.CentiMil.CentiMilToPixel(arc.aY-arc.Height, ydpi);
		rect.bottom = ICFootprint.CentiMil.CentiMilToPixel(arc.aY+arc.Height, ydpi);
		path.addArc(rect, 180.0f-arc.StartAngle, -arc.DeltaAngle);
	}

	/**
	 * Calculate a given layer from a given footprint, according to given dpi.
	 *
	 * @param footprint: the ICFootprint recognized from the footprint file.
	 * @param layer: the layer, copper, drill, mask, draft..etc
	 * @param dpi: the dpi of current display
	 * @return the ICDisplayLayer instance for the given layer
	 */
	public static ICDisplayLayer calculateLayer(ICFootprint footprint, int layer, DisplayMetrics displayMetrics){
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
			Path icpath;
			if(pinorpadlist == null)return null;
			icpath = new Path();
			for(PinOrPadOrDraftLine pinpad:pinorpadlist){
				if(pinpad.getType()==PinOrPadOrDraftLine.TYPE_PAD)
					PadToPath((Pad) pinpad, layer, displayMetrics.xdpi,displayMetrics.ydpi, icpath);
				else if(pinpad.getType()==PinOrPadOrDraftLine.TYPE_PIN)
					PinToPath((Pin) pinpad, layer, displayMetrics.xdpi,displayMetrics.ydpi, icpath);
				else return null;
			}
			//add the new pathpaint to displayLayer, note that unlike draftline, we can only get one pathpaint...
			displayLayer.mListPathPaint.add(new PathPaint(icpath,createFilledPaint()));
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
				thickness = ICFootprint.CentiMil.CentiMilToPixel(thickness, displayMetrics.densityDpi);
				
				//find if a given width already exists,
				PathPaint pp = null;
				for(PathPaint onepp: displayLayer.mListPathPaint){
					if(onepp.mPaint.getStrokeWidth()==thickness)
						pp=onepp;
				}
				
				if(pp==null)
					//of no create new PathPaint and begin to add line
					pp = new PathPaint(new Path(), createPaintFromThickness(thickness));		

				//if yes,(found existing width) continue adding lines to path
				//pp is the PathPaint object for the given draftline.
				//convert the line, arc to dpi based paths
				if(draftline.getType()==PinOrPadOrDraftLine.TYPE_ARC)
					arcToPath((ElementArc)draftline, displayMetrics.xdpi, displayMetrics.ydpi, pp.mPath);
				else if(draftline.getType()==PinOrPadOrDraftLine.TYPE_LINE)
					lineToPath((ElementLine)draftline, displayMetrics.xdpi,displayMetrics.ydpi, pp.mPath);
				else
					return null;
				
				displayLayer.mListPathPaint.add(pp);
			}
			return displayLayer;
			
		default:
			return null;
		}
	}
	
	/**
	 * Calculate all layers from a given footprint and dpi.
	 *
	 * @param footprint the footprint
	 * @param dpi the dpi
	 */
	private void calculateAllLayers(ICFootprint footprint, DisplayMetrics displayMetrics){
		mLayerCopper = calculateLayer(footprint, LAYER_COPPER,displayMetrics);
		mLayerDrill = calculateLayer(footprint, LAYER_DRILL,displayMetrics);
		mLayerMask = calculateLayer(footprint, LAYER_MASK,displayMetrics);
		mLayerDraft = calculateLayer(footprint, LAYER_DRAFT,displayMetrics);
	}
	
	protected void recalculateAllLayers(DisplayMetrics displayMetrics){
		//calculate all layers without 'new' operations
		//TODO: implementation
		if(this.mICFootprint==null)return;
		recalculateLayer(LAYER_COPPER, displayMetrics);
		recalculateLayer(LAYER_DRILL, displayMetrics);
		recalculateLayer(LAYER_MASK, displayMetrics);
		recalculateLayer(LAYER_DRAFT, displayMetrics);
	}
	
	private void cleanLayerPath(ICDisplayLayer layer){
		if(layer == null) return;
		if(layer.mListPathPaint==null) return;
		for(PathPaint pp:layer.mListPathPaint){
			pp.mPath.reset();
		}
	}
	
	private void recalculateLayer(int layer, DisplayMetrics displayMetrics){
		ArrayList<PinOrPadOrDraftLine> pinorpadlist = mICFootprint.getmListPinOrPad();
		ArrayList<PinOrPadOrDraftLine> draftlinelist = mICFootprint.getmListDraftLine();
		ArrayList<PathPaint> pinpadListPathPaint=null, draftListPathPaint=null;
		
		switch(layer){
		case LAYER_COPPER:
			cleanLayerPath(mLayerCopper);
			pinpadListPathPaint = mLayerCopper.mListPathPaint;
			break;
			
		case LAYER_DRILL:
			cleanLayerPath(mLayerDrill);
			pinpadListPathPaint = mLayerDrill.mListPathPaint;
			break;
			
		case LAYER_MASK:
			cleanLayerPath(mLayerMask);
			pinpadListPathPaint = mLayerMask.mListPathPaint;
			break;
			
		case LAYER_CLEARANCE:
			return;//do nothing for clearance layer
			
		case LAYER_DRAFT:
			cleanLayerPath(mLayerDraft);
			draftListPathPaint = mLayerDraft.mListPathPaint;
			break;
			
		default:
			break;
		}
		
		if(pinpadListPathPaint!=null){
			if(pinorpadlist == null)return;
			Path icpath = pinpadListPathPaint.get(0).mPath;
			for(PinOrPadOrDraftLine pinpad:pinorpadlist){
				if(pinpad.getType()==PinOrPadOrDraftLine.TYPE_PAD)
					PadToPath((Pad) pinpad, layer, displayMetrics.xdpi,displayMetrics.ydpi, icpath);
				else if(pinpad.getType()==PinOrPadOrDraftLine.TYPE_PIN)
					PinToPath((Pin) pinpad, layer, displayMetrics.xdpi,displayMetrics.ydpi, icpath);
				else return;
			}
		}
		if(draftListPathPaint!=null){
			if(draftlinelist == null)return;
			
			for(PinOrPadOrDraftLine draftline:draftlinelist){
				float thickness;
				if(draftline.getType()== PinOrPadOrDraftLine.TYPE_ARC)
					thickness=((ElementArc)draftline).Thickness;
				else if(draftline.getType()== PinOrPadOrDraftLine.TYPE_LINE)
					thickness=((ElementLine)draftline).Thickness;
				else
					return;
				
				//convert thickness from centimil to pixel
				thickness = ICFootprint.CentiMil.CentiMilToPixel(thickness, displayMetrics.densityDpi);
				
				//find if a given width already exists,
				PathPaint pp = null;
				for(PathPaint onepp: draftListPathPaint){
					if(onepp.mPaint.getStrokeWidth()==thickness)
						pp=onepp;
				}
				
				if(pp==null)
					//of no create new PathPaint and begin to add line
					pp = new PathPaint(new Path(), createPaintFromThickness(thickness));		

				//if yes,(found existing width) continue adding lines to path
				//pp is the PathPaint object for the given draftline.
				//convert the line, arc to dpi based paths
				if(draftline.getType()==PinOrPadOrDraftLine.TYPE_ARC)
					arcToPath((ElementArc)draftline, displayMetrics.xdpi, displayMetrics.ydpi, pp.mPath);
				else if(draftline.getType()==PinOrPadOrDraftLine.TYPE_LINE)
					lineToPath((ElementLine)draftline, displayMetrics.xdpi,displayMetrics.ydpi, pp.mPath);
				else
					return;
			}
		}
	}
	
	private static Paint createPaintFromThickness(float thickness){
		Paint pt = new Paint();
		pt.setStyle(Paint.Style.STROKE);
		pt.setStrokeWidth(thickness);
		return pt;
	}
	
	private static Paint createFilledPaint(){
		Paint pt = new Paint();
		pt.setStyle(Paint.Style.FILL);
		return pt;
	}
	
	/**
	 * Gets the color of a specified layer.
	 *
	 * @param layer the layer
	 * @return the layer color
	 */
	public int getLayerColor(int layer){
		switch(layer){
		case LAYER_DRAFT:
			if(mLayerDraft!=null)
				return mLayerDraft.getColor();
			else
				return 0;

		case LAYER_MASK:
			if(mLayerMask!=null)
				return mLayerMask.getColor();
			else
				return 0;
			
		case LAYER_CLEARANCE:
			return 0;
			
		case LAYER_DRILL:
			if(mLayerDrill!=null)
				return mLayerDrill.getColor();
			else
				return 0;
			
		case LAYER_COPPER:
			if(mLayerCopper!=null)
				return mLayerCopper.getColor();
			else
				return 0;
			
		default:
			return 0;
		}
	}
	
	/**
	 * Sets the color of a specified layer.
	 *
	 * @param layer the layer
	 * @param color the color
	 */
	public void setLayerColor(int layer, int color){
		switch(layer){
		case LAYER_DRAFT:
			if(mLayerDraft!=null)
				mLayerDraft.setColor(color);
			else
				return;
			break;
			
		case LAYER_MASK:
			if(mLayerMask!=null)
				mLayerMask.setColor(color);
			else
				return;
			break;
			
		case LAYER_CLEARANCE:
				return;
			
		case LAYER_DRILL:
			if(mLayerDrill!=null)
				mLayerDrill.setColor(color);
			else
				return;
			break;
			
		case LAYER_COPPER:
			if(mLayerCopper!=null)
				mLayerCopper.setColor(color);
			else
				return;
			break;
			
		default:
			break;
		}
	}
	
	public boolean isLayerVisible(int layer){
		switch(layer){
		case LAYER_DRAFT:
			return mLayerDraft.isVisible();
			
		case LAYER_MASK:
			return mLayerMask.isVisible();
			
		case LAYER_CLEARANCE:
				return false;
			
		case LAYER_DRILL:
			return mLayerDrill.isVisible();
			
		case LAYER_COPPER:
			return mLayerCopper.isVisible();
			
		default:
			return false;
		}
	}
	
	public void setLayerVisible(int layer, boolean isVisible){
		switch(layer){
		case LAYER_DRAFT:
			mLayerDraft.setVisible(isVisible);
			return;
		case LAYER_MASK:
			mLayerMask.setVisible(isVisible);
			return;
		case LAYER_CLEARANCE:
				return;
			
		case LAYER_DRILL:
			mLayerDrill.setVisible(isVisible);
			return;
		case LAYER_COPPER:
			mLayerCopper.setVisible(isVisible);
			return;
		default:
			return;
		}
	}
	
	private static void offsetOneLayer(ICDisplayLayer layer, float dx, float dy){
		if(layer==null)return;
		if(layer.mListPathPaint== null)return;
		for(PathPaint pp: layer.mListPathPaint){
			pp.mPath.offset(dx, dy);
		}
	}
	
	private static void offsetFootprintRender(ICFootprintRender icfprd, float dx, float dy){
		offsetOneLayer(icfprd.mLayerCopper,dx,dy);
		offsetOneLayer(icfprd.mLayerDrill,dx,dy);
		offsetOneLayer(icfprd.mLayerMask,dx,dy);
		offsetOneLayer(icfprd.mLayerDraft,dx,dy);
	}
	
	/**
	 * Offset footprint and recalculate render.
	 *
	 * @param dx_in_px the desired x direction move in px
	 * @param dy_in_px the desired y direction move in px
	 * @param w_in_px the width of the view in px
	 * @param h_in_px the height of the view in px
	 * @param borderMargin  the border margin that at least this px of part is shown.
	 * @param displayMetrics the display metrics
	 */
	public void offsetFootprintAndRecalculateRender(float dx_in_px, float dy_in_px, float w_in_px, float h_in_px, float borderMargin, DisplayMetrics displayMetrics){
		//dx_in_px and dy_in_px are in pixel, and should be converted to centi_mil
//		float dx,dy;
//		dx=ICFootprint.CentiMil.PixelToCentiMil(dx_in_px, displayMetrics.xdpi);
//		dy=ICFootprint.CentiMil.PixelToCentiMil(dy_in_px, displayMetrics.ydpi);
		
		//calculate the current footprint boundbox in px
		RectF rect = mICFootprint.calculateFootprintOverallBoundRectangle();
		RectF rect_px = new RectF();
		rect_px.left =  ICFootprint.CentiMil.CentiMilToPixel(rect.left, displayMetrics.xdpi);
		rect_px.right =  ICFootprint.CentiMil.CentiMilToPixel(rect.right, displayMetrics.xdpi);
		rect_px.top =  ICFootprint.CentiMil.CentiMilToPixel(rect.top, displayMetrics.ydpi);
		rect_px.bottom =  ICFootprint.CentiMil.CentiMilToPixel(rect.bottom, displayMetrics.ydpi);
		float old_centerX_in_px = rect_px.centerX();
		float old_centerY_in_px = rect_px.centerY();
		//try move the rect_px by given offset
		rect_px.offset(dx_in_px, dy_in_px);
		float x_in_px=rect_px.centerX();
		float y_in_px=rect_px.centerY();
		
		//calculate the border limit for center point to move
		float center_left_most 		= 0 - rect_px.width()/2+borderMargin;
		float center_right_most 	= w_in_px + rect_px.width()/2-borderMargin;
		float center_top_most 		= 0 - rect_px.height()/2+borderMargin;
		float center_bottom_most 	= h_in_px + rect_px.height()/2-borderMargin;
		
		//saturate the movement, if we are about to move out of the view's extent
		x_in_px = (x_in_px < center_left_most)?center_left_most:x_in_px;
		x_in_px = (x_in_px > center_right_most)?center_right_most:x_in_px;
		y_in_px = (y_in_px < center_top_most)?center_top_most:y_in_px;
		y_in_px = (y_in_px > center_bottom_most)?center_bottom_most:y_in_px;
		//now x_in_px and y_in_px are the saturated center point.
		
		float dx = ICFootprint.CentiMil.PixelToCentiMil(x_in_px - old_centerX_in_px, displayMetrics.xdpi);
		float dy = ICFootprint.CentiMil.PixelToCentiMil(y_in_px - old_centerY_in_px, displayMetrics.ydpi);
		//offset the footprint
		mICFootprint.offsetTheFootprint(dx, dy);
		//recalculate the render
		recalculateAllLayers(displayMetrics);
	}
	
	/**
	 * Sets the footprint position and recalculate render.
	 *
	 * @param x_in_px the x_in_px
	 * @param y_in_px the y_in_px
	 * @param w_in_px the w_in_px
	 * @param h_in_px the h_in_px
	 * @param borderMargin the border margin that at least this px of part is shown.
	 * @param displayMetrics the display metrics
	 */
//	public void setFootprintPositionAndRecalculateRender(float x_in_px, float y_in_px, float w_in_px, float h_in_px, float borderMargin, DisplayMetrics displayMetrics){
//		RectF rect = mICFootprint.calculateFootprintOverallBoundRectangle();
//		
//		//float old_x_px = ICFootprint.CentiMil.CentiMilToPixel(rect.centerX(), displayMetrics.xdpi);
//		//float old_y_px = ICFootprint.CentiMil.CentiMilToPixel(rect.centerY(), displayMetrics.ydpi);
//		
//		//calculate the current footprint boundbox in px
//		RectF rect_px = new RectF();
//		rect_px.left =  ICFootprint.CentiMil.CentiMilToPixel(rect.left, displayMetrics.xdpi);
//		rect_px.right =  ICFootprint.CentiMil.CentiMilToPixel(rect.right, displayMetrics.xdpi);
//		rect_px.top =  ICFootprint.CentiMil.CentiMilToPixel(rect.top, displayMetrics.ydpi);
//		rect_px.bottom =  ICFootprint.CentiMil.CentiMilToPixel(rect.bottom, displayMetrics.ydpi);
//		//calculate the border limit for center point to move
//		float center_left_most 		= 0 - rect_px.width()/2+borderMargin;
//		float center_right_most 	= w_in_px + rect_px.width()/2-borderMargin;
//		float center_top_most 		= 0 - rect_px.height()/2+borderMargin;
//		float center_bottom_most 	= h_in_px - rect_px.height()/2-borderMargin;
//		//saturate the movement, if we are about to move out of the view's extent
//		x_in_px = (x_in_px < center_left_most)?center_left_most:x_in_px;
//		x_in_px = (x_in_px > center_right_most)?center_right_most:x_in_px;
//		y_in_px = (y_in_px < center_top_most)?center_top_most:y_in_px;
//		y_in_px = (y_in_px > center_bottom_most)?center_top_most:y_in_px;
//		
//		offsetFootprintAndRecalculateRender(x_in_px-rect_px.centerX(),y_in_px-rect_px.centerX(),displayMetrics);
//	}
	
	private static void drawOneLayer(ICDisplayLayer layer, Canvas canvas){
		if(layer==null)return;
		if(!layer.isVisible())return;
		for(PathPaint pp:layer.mListPathPaint)
			canvas.drawPath(pp.mPath, pp.mPaint);
	}
	
	public static final void drawFootprintRender(ICFootprintRender icfprd, Canvas canvas){
		//draw the given icfprd on the given canvas
		drawOneLayer(icfprd.mLayerCopper,canvas);
		drawOneLayer(icfprd.mLayerDrill,canvas);
		drawOneLayer(icfprd.mLayerMask,canvas);
		drawOneLayer(icfprd.mLayerDraft,canvas);
	}
}
