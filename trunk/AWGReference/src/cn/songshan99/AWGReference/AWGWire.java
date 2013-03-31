package cn.songshan99.AWGReference;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

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
	
	private static String DBPATH = "/data/data/cn.songshan99.AWGReference/databases/;";
	private static String DBNAME = "AWGDB.db3";
	private static String TABLENAME = "AWGDATA";
	
	private static String COLUMNS_TO_SELECT[] = {"DIAMETER_INCH",
												"DIAMETER_MM",
												"AREA_KCMIL",
												"AREA_MM2",
												"RES_MOHM_M",
												"RES_MOHM_FT",
												"FC_PREECE_10S",
												"FC_ONDERDONK_1S_A",
												"FC_ONDERDONK_32MS_A"};
	
	private AWGDBOpenHelper mAWGDBOpenHelper;
	
	public AWGWire(String aWGSize, Context context) throws SQLException {
		AWGSize = aWGSize;
		//TODO: implement database lookup and number calculating
		AWGDBOpenHelper mAWGDBOpenHelper = new AWGDBOpenHelper(context);
		mAWGDBOpenHelper.openDataBase();
	}

	public void setAWGSize(String awgsize){
		//TODO: implement database lookup and number calculating
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
	
	private class AWGDBOpenHelper extends SQLiteOpenHelper {
		
		
		private Context mContext;
		private SQLiteDatabase mDatabase;
		
		public AWGDBOpenHelper(Context context) {
			super(context, DBNAME, null, 1);
		}
		
		public void openDataBase() throws SQLException{
	    	//Open the database
			mDatabase = SQLiteDatabase.openDatabase(DBPATH + DBNAME, null, SQLiteDatabase.OPEN_READONLY);
	    }

		@Override
		public synchronized void close() {
			if(mDatabase != null) mDatabase.close();
			super.close();
		}
		
		public void queryAWGandRefresh(){
			Cursor cursor = mDatabase.query(TABLENAME, COLUMNS_TO_SELECT, "AWG=\""+ AWGSize +"\"", null, null, null, null);
			
			//TODO: check if we get multiple result, and check if result is null,
			diameter_in = cursor.getFloat(0);
			diameter_mm = cursor.getFloat(1);
			
			area_kcmil = cursor.getFloat(2);
			area_mm2 = cursor.getFloat(3);
			resistance_mOhm_per_m = cursor.getFloat(4);
			resistance_mOhm_per_ft = cursor.getFloat(5);
			
			fusingCurrentPreece10s = cursor.getFloat(6);
			fusingCurrentOnderdonk1s = cursor.getFloat(7);
			fusingCurrentOnderdonk30ms = cursor.getFloat(8);
		}
		
		public String[] queryAWGSizes(){
			//get all the available sizes from database. This will be used to initialize the spinner
			String[] awgcol = {"AWG"};
			Cursor cursor = mDatabase.query(TABLENAME, awgcol , null, null, null, null, null);
			
			int cnt = cursor.getCount();
			//write it to string array
			String[] result = new String[cnt];
			for(int i=0;i<cnt;i++){
				result[i] = new String(cursor.getString(0));
				cursor.moveToNext();
			}
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
	}
}
