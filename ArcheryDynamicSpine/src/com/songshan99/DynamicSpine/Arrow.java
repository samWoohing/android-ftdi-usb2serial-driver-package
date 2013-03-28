package com.songshan99.DynamicSpine;

/**
 * @author 307004396
 *
 */
public class Arrow {
	private String ShaftType;
	private double AMOSpine;
	private double ShaftGPI;
	private double ShaftDiameterInch;
	private double BOPLengthInch;
	private double PointWeightGr;
	private double InsertWeightGr;
	private boolean HaveFooting;
	private double FootingLengthInch;
	private double FootingWeightGr;
	private double NockEndWeightGr;
	private String FletchingType;
	private double FletchingWeightGr;
	
	private double TotalWeightGr;
	private double SpecificWeight;
	private double FOC;
	private double SpeedFPS;
	private double EnergyFtLbs;
	
	private double DistanceFromBOPtoBalancePoint;
	private double StrikePlatePosition;
	private double DynamicSpineWithoutMassDiameterCompensation;
	
	public String getShaftType() {
		return ShaftType;
	}
	public void setShaftType(String shaftType) {
		ShaftType = shaftType;
	}
	
	public double getAMOSpine() {
		return AMOSpine;
	}
	public void setAMOSpine(double aMOSpine) {
		AMOSpine = aMOSpine;
	}
	
	public double getShaftGPI() {
		return ShaftGPI;
	}
	public void setShaftGPI(double shaftGPI) {
		ShaftGPI = shaftGPI;
	}
	
	public double getShaftDiameterInch() {
		return ShaftDiameterInch;
	}
	public void setShaftDiameterInch(double shaftDiameterInch) {
		ShaftDiameterInch = shaftDiameterInch;
	}
	
	public double getBOPLengthInch() {
		return BOPLengthInch;
	}
	public void setBOPLengthInch(double bOPLengthInch) {
		BOPLengthInch = bOPLengthInch;
	}
	
	public double getPointWeightGr() {
		return PointWeightGr;
	}
	public void setPointWeightGr(double pointWeightGr) {
		PointWeightGr = pointWeightGr;
	}
	
	public double getInsertWeightGr() {
		return InsertWeightGr;
	}
	public void setInsertWeightGr(double insertWeightGr) {
		InsertWeightGr = insertWeightGr;
	}
	
	public boolean isHaveFooting() {
		return HaveFooting;
	}
	public void setHaveFooting(boolean haveFooting) {
		HaveFooting = haveFooting;
	}
	
	public double getFootingLengthInch() {
		return FootingLengthInch;
	}
	public void setFootingLengthInch(double footingLengthInch) {
		FootingLengthInch = footingLengthInch;
	}
	
	public double getFootingWeightGr() {
		return FootingWeightGr;
	}
	public void setFootingWeightGr(double footingWeightGr) {
		FootingWeightGr = footingWeightGr;
	}
	
	public double getNockEndWeightGr() {
		return NockEndWeightGr;
	}
	public void setNockEndWeightGr(double nockEndWeightGr) {
		NockEndWeightGr = nockEndWeightGr;
	}
	
	public String getFletchingType() {
		return FletchingType;
	}
	public void setFletchingType(String fletchingType) {
		FletchingType = fletchingType;
	}
	
	public double getFletchingWeightGr() {
		return FletchingWeightGr;
	}
	public void setFletchingWeightGr(double fletchingWeightGr) {
		FletchingWeightGr = fletchingWeightGr;
	}
	
	private void calculateTotalWeightGr(){
		TotalWeightGr=0;//TODO: implement
	}
	
	public double getTotalWeightGr() {
		calculateTotalWeightGr();
		return TotalWeightGr;
	}
	
	private void calculateSpecificWeight(){
		SpecificWeight=0;//TODO: implement
	}
	
	public double getSpecificWeight() {
		calculateSpecificWeight();
		return SpecificWeight;
	}
	
	private void calculateFOC(){
		FOC=0;//TODO: implement
	}
	
	public double getFOC() {
		calculateFOC();
		return FOC;
	}
	
	private void calculateSpeedFPS(){
		SpeedFPS=0;//TODO: implement
	}
	
	public double getSpeedFPS() {
		calculateSpeedFPS();
		return SpeedFPS;
	}
	
	private void calculateEnergyFtLbs(){
		EnergyFtLbs=0;//TODO: implement
	}
	
	public double getEnergyFtLbs() {
		calculateEnergyFtLbs();
		return EnergyFtLbs;
	}
	
	private void calculateDistanceFromBOPtoBalancePoint(){
	
	}
	private double getDistanceFromBOPtoBalancePoint(){
		calculateDistanceFromBOPtoBalancePoint();
		return DistanceFromBOPtoBalancePoint;
	}
	
	private void calculateStrikePlatePosition(){
		
	}
	private double getStrikePlatePosition(){
		calculateStrikePlatePosition();
		return StrikePlatePosition;
	}
	
	private void calculateDynamicSpineWithoutMassDiameterCompensation(){
		
	}
	private double getDynamicSpineWithoutMassDiameterCompensation(){
		calculateDynamicSpineWithoutMassDiameterCompensation();
		return DynamicSpineWithoutMassDiameterCompensation;
	}
	
	/**
	 * Lookup arrow details from the database
	 */
	public void lookupArrowDetails(){
		
	}
	
	/**
	 * Lookup Fletching details from the database
	 */
	public void lookupFletchingDetails(){
		
	}
	

	
}
