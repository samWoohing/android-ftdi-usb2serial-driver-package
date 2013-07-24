package cn.songshan99.AWGReference;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class AWGWire {
	
	private String AWGSize;
	private int strand_num;
	private float strand_dia_mm;
	private float strand_dia_in;
	private float diameter_mm;
	private float area_mm2;
	private float resistance_mOhm_per_m;
	private float diameter_in;
	private float area_kcmil;
	private float resistance_mOhm_per_ft;
	private float fusingCurrentPreece10s;
	private float fusingCurrentOnderdonk1s;
	private float fusingCurrentOnderdonk30ms;
	
	private String DBPATHNAME ;//= "/data/data/cn.songshan99.AWGReference/databases/";
	private static String DBNAME = "AWGDB.db3";
	private static String TABLENAME = "AWGDATA";
	
	private static String COLUMNS_TO_SELECT[] = {"STRAND_NUM",
												"STRAND_DIA_IN",
												"STRAND_DIA_MM",
												"DIAMETER_INCH",
												"DIAMETER_MM",
												"AREA_KCMIL",
												"AREA_MM2",
												"RES_MOHM_M",
												"RES_MOHM_FT",
												"FC_PREECE_10S",
												"FC_ONDERDONK_1S_A",
												"FC_ONDERDONK_32MS_A"};
	
	private static String VERTABLENAME = "VERSION";
	private static String VER_COLUMS[] = {"_id"};
	private static int CURRENT_DB_VERSION = 1;
	
	private AWGDBOpenHelper mAWGDBOpenHelper;
	
	public AWGWire(Context context) throws SQLException, IOException {
		DBPATHNAME=context.getDatabasePath(DBNAME).getPath();
		//AWGSize = aWGSize;
		//Initialize and open database.
		mAWGDBOpenHelper = new AWGDBOpenHelper(context);
		mAWGDBOpenHelper.createDataBase();
		mAWGDBOpenHelper.openDataBase();
		//DBPATH="/data/data/"+context.getPackageName()+"/databases/";
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		mAWGDBOpenHelper.close();
		super.finalize();
	}


	public void setAWGSize(String awgsize){
		//TODO: implement database lookup and number calculating
		AWGSize = awgsize;
		mAWGDBOpenHelper.queryAWGandRefresh();
	}
	
	public String getAWGSize() {
		return AWGSize;
	}
	
	public int getStrand_num() {
		return strand_num;
	}

	public float getStrand_dia_mm() {
		return strand_dia_mm;
	}

	public float getStrand_dia_in() {
		return strand_dia_in;
	}

	public float getDiameter_mm() {
		return diameter_mm;
	}
	public float getArea_mm2() {
		return area_mm2;
	}
	public float getResistance_mOhm_per_m() {
		return resistance_mOhm_per_m;
	}
	public float getDiameter_in() {
		return diameter_in;
	}
	public float getArea_kcmil() {
		return area_kcmil;
	}
	public float getResistance_mOhm_per_ft() {
		return resistance_mOhm_per_ft;
	}

	public float getFusingCurrentPreece10s() {
		return fusingCurrentPreece10s;
	}

	public float getFusingCurrentOnderdonk1s() {
		return fusingCurrentOnderdonk1s;
	}

	public float getFusingCurrentOnderdonk30ms() {
		return fusingCurrentOnderdonk30ms;
	}
	
	public String[] queryAWGSizes(){
		return mAWGDBOpenHelper.queryAWGSizes();
	}
	private class AWGDBOpenHelper extends SQLiteOpenHelper {
		
		
		private Context mContext;
		private SQLiteDatabase mDatabase;
		
		public AWGDBOpenHelper(Context context) {
			super(context, DBNAME, null, 1);
			mContext = context;
		}
		
		public void openDataBase() throws SQLException{
	    	//Open the database
			mDatabase = SQLiteDatabase.openDatabase(DBPATHNAME, null, SQLiteDatabase.OPEN_READONLY);
	    }

		@Override
		public synchronized void close() {
			if(mDatabase != null) mDatabase.close();
			super.close();
		}
		
		public void queryAWGandRefresh(){
			Cursor cursor = mDatabase.query(TABLENAME, COLUMNS_TO_SELECT, "_id=\""+ AWGSize +"\"", null, null, null, null);
			cursor.moveToFirst();
			//TODO: check if we get multiple result, and check if result is null,
			strand_num = cursor.getInt(0);
			strand_dia_in = cursor.isNull(1)?0:cursor.getFloat(1);
			strand_dia_mm = cursor.isNull(2)?0:cursor.getFloat(2);
			
			diameter_in = cursor.getFloat(3);
			diameter_mm = cursor.getFloat(4);
			
			area_kcmil = cursor.getFloat(5);
			area_mm2 = cursor.getFloat(6);
			resistance_mOhm_per_m = cursor.getFloat(7);
			resistance_mOhm_per_ft = cursor.getFloat(8);
			
			fusingCurrentPreece10s = cursor.getFloat(9);
			fusingCurrentOnderdonk1s = cursor.getFloat(10);
			fusingCurrentOnderdonk30ms = cursor.getFloat(11);
			
			cursor.close();
		}
		
		public String[] queryAWGSizes(){
			//get all the available sizes from database. This will be used to initialize the spinner
			String[] awgcol = {"_id"};
			Cursor cursor;
			try{
				cursor = mDatabase.query(TABLENAME, awgcol , null, null, null, null, null);
			}catch(SQLiteException e){
				e.printStackTrace();
				return null;
			}
			int cnt = cursor.getCount();
			//TODO: make sure result is NOT null
			cursor.moveToFirst();
			//write it to string array
			String[] result = new String[cnt];
			for(int i=0;i<cnt;i++){
				result[i] = new String(cursor.getString(0));
				cursor.moveToNext();
			}
			cursor.close();
			return result;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
		}
		
		  /**
	     * Creates a empty database on the system and rewrites it with your own database.
	     * */
	    public void createDataBase() throws IOException{
	 
	    	boolean dbExist = checkDataBase();
	 
	    	if(dbExist){
	    		//do nothing - database already exist
	    		if(checkDataBaseNeedsUpdate()){
	    			//remove old db;
	    			mContext.deleteDatabase(DBNAME);
	    			Toast.makeText(mContext, "Old local database deleted", Toast.LENGTH_SHORT).show();
	    			this.getReadableDatabase();
	    			 
		        	try {
		 
		    			copyDataBase();
		 
		    		} catch (IOException e) {
		 
		        		throw new Error("Error copying database");
		 
		        	}
		        	
		        	Toast.makeText(mContext, "Local new database updated!", Toast.LENGTH_SHORT).show();
	    		}
	    		else{
	    			Toast.makeText(mContext, "Local database is newest.", Toast.LENGTH_SHORT).show();
	    		}
	    		
	    	}else{
	 
	    		//By calling this method and empty database will be created into the default system path
	               //of your application so we are gonna be able to overwrite that database with our database.
	        	this.getReadableDatabase();
	 
	        	try {
	 
	    			copyDataBase();
	 
	    		} catch (IOException e) {
	 
	        		throw new Error("Error copying database");
	 
	        	}
	        	Toast.makeText(mContext, "Local new database updated!", Toast.LENGTH_SHORT).show();
	    	}
	    	
	 
	    }
	    
	    private boolean checkDataBaseNeedsUpdate(){
	    	SQLiteDatabase checkDB = null;
	    	try{
	    		//String myPath = DBPATHNAME;
	    		checkDB = SQLiteDatabase.openDatabase(DBPATHNAME, null, SQLiteDatabase.OPEN_READONLY);
	 
	    	}catch(SQLiteException e){
	    		checkDB.close();
	    		return false;//should not happen
	    	}
	    	
	    	try{
	    		Cursor cursor = checkDB.query(VERTABLENAME, VER_COLUMS, null, null, null, null, null);
	    		cursor.moveToFirst();
		    	if(cursor.isNull(0)){
		    		checkDB.close();
		    		return true;
		    	}
		    	if(cursor.getInt(0)!=CURRENT_DB_VERSION){ 
		    		checkDB.close();
		    		return true;
		    	}
	    	}catch(SQLiteException e){
	    		return true;
	    	}
	    	checkDB.close();
			return false;
	    }
	    /**
	     * Check if the database already exist to avoid re-copying the file each time you open the application.
	     * @return true if it exists, false if it doesn't
	     */
	    private boolean checkDataBase(){
	 
	    	SQLiteDatabase checkDB = null;
	 
	    	try{
	    		//String myPath = DBPATHNAME;
	    		checkDB = SQLiteDatabase.openDatabase(DBPATHNAME, null, SQLiteDatabase.OPEN_READONLY);
	 
	    	}catch(SQLiteException e){
	 
	    		//database does't exist yet.
	 
	    	}
	 
	    	if(checkDB != null){
	 
	    		checkDB.close();
	 
	    	}
	 
	    	return checkDB != null ? true : false;
	    }
	 
	    /**
	     * Copies your database from your local assets-folder to the just created empty database in the
	     * system folder, from where it can be accessed and handled.
	     * This is done by transfering bytestream.
	     * */
	    private void copyDataBase() throws IOException{
	 
	    	//Open your local db as the input stream
	    	InputStream myInput = mContext.getAssets().open(DBNAME);
	 
	    	// Path to the just created empty db
	    	//String outFileName = DBPATHNAME;
	 
	    	//Open the empty db as the output stream
	    	OutputStream myOutput = new FileOutputStream(DBPATHNAME);
	 
	    	//transfer bytes from the inputfile to the outputfile
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	 
	    	//Close the streams
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	 
	    }
	}
}
