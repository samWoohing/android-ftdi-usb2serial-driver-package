package cn.songshan99.AWGReference;

public class AWGWire {
	
	private String AWGSize;
	private double diameter_mm;
	private double area_mm2;
	private double resistance_mOhm_per_m;
	private double diameter_in;
	private double area_kcmil;
	private double resistance_mOhm_per_ft;
	private double fusingCurrentPreece10s;
	private double fusingCurrentOnderdonk1s;
	private double fusingCurrentOnderdonk30ms;
	
	
	
	public AWGWire(String aWGSize) {
		AWGSize = aWGSize;
		//TODO: implement database lookup and number calculating
	}

	public void setAWGSize(String awgsize){
		
	}
	
	public String getAWGSize() {
		return AWGSize;
	}
	public double getDiameter_mm() {
		return diameter_mm;
	}
	public double getArea_mm2() {
		return area_mm2;
	}
	public double getResistance_mOhm_per_m() {
		return resistance_mOhm_per_m;
	}
	public double getDiameter_in() {
		return diameter_in;
	}
	public double getArea_kcmil() {
		return area_kcmil;
	}
	public double getResistance_mOhm_per_ft() {
		return resistance_mOhm_per_ft;
	}

	public double getFusingCurrentPreece10s() {
		return fusingCurrentPreece10s;
	}

	public double getFusingCurrentOnderdonk1s() {
		return fusingCurrentOnderdonk1s;
	}

	public double getFusingCurrentOnderdonk30ms() {
		return fusingCurrentOnderdonk30ms;
	}
	
}
