package cn.songshan99.realicfootprint;

public class ICFootprint {

	
	
	public class PinOrPad{
		public static final int TYPE_PIN=1;
		public static final int TYPE_PAD=2;
		
		private int type;
		
		private Object pin_or_pad;

		public PinOrPad(Pin pin) {
			pin_or_pad = pin;
			type = TYPE_PIN;
		}
		
		public PinOrPad(Pad pad) {
			pin_or_pad = pad;
			type = TYPE_PAD;
		}

		public Object getPin_or_pad() {
			return pin_or_pad;
		}

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}
	}
	
	public class Pin{
		//TODO: include the draw path method to each pin and pad
	}
	
	public class Pad{
		//TODO: include the draw path method to each pin and pad
		
		String Name, Number;
	}
	
	public class ElementLine{
		public CentiMil X1,Y1,X2,Y2,Thickness;
	}
	
	public class ElementArc{
		public CentiMil X,Y,Width,Height,StartAngle,DeltaAngle,Thickness;
	}
	
	public class Mark{
		public CentiMil X,Y;
	}
	
	public class CentiMil{
		//a unified coordinate uses 1/100 mil as base unit.
		//and includes conversion functions
		
		static final int UNIT_CMIL	= 0;
		static final int UNIT_UMIL	= 1;
		static final int UNIT_MIL	= 2;
		static final int UNIT_INCH	= 3;
		static final int UNIT_NM	= 4;
		static final int UNIT_UM	= 5;
		static final int UNIT_MM	= 6;
		static final int UNIT_M		= 7;
		static final int UNIT_KM	= 8;
		static final int UNIT_PIXEL = 9;
		
		static final double INCH_TO_M = 0.0254;
		
		private double HowMuchCmilPerType(int type){
			switch(type){
			case UNIT_CMIL:
				return 1.0;
			case UNIT_UMIL:
				return 1/10000;
			case UNIT_MIL:
				return 100;
			case UNIT_INCH:
				return 100000;
			case UNIT_NM:
				return 1/10000/INCH_TO_M;
			case UNIT_UM:
				return 0.1/INCH_TO_M;
			case UNIT_MM:
				return 100/INCH_TO_M;
			case UNIT_M:
				return 100000/INCH_TO_M;
			case UNIT_KM:
				return 100000000/INCH_TO_M;
			default:
				return 1.0;
			}
		}
		
		double mValue;//this is in cmil (1/100mil)

		public CentiMil(float v, int type) {
			mValue = v*HowMuchCmilPerType(type);
		}
		
		public CentiMil(int v, int type){
			mValue = (double)v*HowMuchCmilPerType(type);
		}
		
		public double toPixel(double dpi){
			return mValue/HowMuchCmilPerType(UNIT_INCH)*dpi;
		}
	}
}
