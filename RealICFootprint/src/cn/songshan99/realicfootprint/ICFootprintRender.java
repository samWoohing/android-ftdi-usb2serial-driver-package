package cn.songshan99.realicfootprint;

import java.util.ArrayList;

import cn.songshan99.realicfootprint.ICFootprint.ElementArc;
import cn.songshan99.realicfootprint.ICFootprint.ElementLine;
import cn.songshan99.realicfootprint.ICFootprint.Pad;
import cn.songshan99.realicfootprint.ICFootprint.Pin;
import cn.songshan99.realicfootprint.ICFootprint.PinOrPadOrDraftLine;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

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
		public static final int TYPE_COPPER = 1;
		public static final int TYPE_DRILL = 2;
		public static final int TYPE_CLEARANCE = 3;
		public static final int TYPE_DRAFTLINE= 4;
		
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
	
	//NOTE: Odd-even rule is preferred to fill the shapes!
	private Path PinToPath(Pin pin, int layer, float dpi){
		return null;
	}
	
	private Path PadToPath(Pad pad, int layer, float dpi){
		return null;
	}
	
	public ICDisplayLayer calculateLayer(ICFootprint footprint, int layer, float dpi){
		ArrayList<PinOrPadOrDraftLine> pinorpadlist = footprint.getmListPinOrPad();
		ArrayList<PinOrPadOrDraftLine> draftlinelist = footprint.getmListDraftLine();
		
		ICDisplayLayer displayLayer = new ICDisplayLayer();
		displayLayer.mType = layer;
		displayLayer.mListPathPaint= new ArrayList<PathPaint>();
		
		switch(layer){
		case ICDisplayLayer.TYPE_COPPER:
		case ICDisplayLayer.TYPE_DRILL:
		case ICDisplayLayer.TYPE_CLEARANCE:
			Path pinpath,icpath;
			if(pinorpadlist == null)return null;
			icpath = new Path();
			for(PinOrPadOrDraftLine pinpad:pinorpadlist){
				if(pinpad.getType()==PinOrPadOrDraftLine.TYPE_PAD){
					icpath.addPath(PadToPath((Pad) pinpad, layer, dpi));
				}else if(pinpad.getType()==PinOrPadOrDraftLine.TYPE_PIN){
					icpath.addPath(PinToPath((Pin) pinpad, layer, dpi));
				}
				else return null;
			}
			//add the new pathpaint to displayLayer, note that unlike draftline, we can only get one pathpaint...
			displayLayer.mListPathPaint.add(new PathPaint(icpath,null));
			return displayLayer;
			
		case ICDisplayLayer.TYPE_DRAFTLINE:
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
				//TODO: continue to finish this part.
			}
			break;
			
		default:
			return null;
		}
		
		return null;
	}
}
