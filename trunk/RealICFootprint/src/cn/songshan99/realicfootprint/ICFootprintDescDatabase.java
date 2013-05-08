package cn.songshan99.realicfootprint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;


import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

public class ICFootprintDescDatabase {
	//TODO: implement a bunch of search method in this class. See whatever the Provider class needs.
	
	private static final String TAG = "ICFootprintDescDatabase";
	//The columns we'll include in the dictionary table
	//Purpose here is to make the database similar to the format of a suggestion table,
	//so in the provider, we can fetch data directly from it,
	//refer to: http://developer.android.com/guide/topics/search/adding-custom-suggestions.html#SuggestionTable
    public static final String KEY_FILENAME = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public static final String KEY_DESCRIPTION = SearchManager.SUGGEST_COLUMN_TEXT_2;

    private static final String DATABASE_NAME = "footprint";
    private static final String FPN_VIRTUAL_TABLE = "footprint_fname_desc";
    private static final int DATABASE_VERSION = 1;

    private final ICFootprintOpenHelper mDatabaseOpenHelper;
    private static final HashMap<String,String> mColumnMap = buildColumnMap();
    
    
    public ICFootprintDescDatabase(Context context) {
		mDatabaseOpenHelper = new ICFootprintOpenHelper(context);
	}

	private static HashMap<String,String> buildColumnMap() {
    	//hashmap will be used by query builder.
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(KEY_FILENAME, KEY_FILENAME);
        map.put(KEY_DESCRIPTION, KEY_DESCRIPTION);
        map.put(BaseColumns._ID, "rowid AS " +
                BaseColumns._ID);
        map.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, "rowid AS " +
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
        map.put(SearchManager.SUGGEST_COLUMN_SHORTCUT_ID, "rowid AS " +
                SearchManager.SUGGEST_COLUMN_SHORTCUT_ID);
        return map;
    }
    
    private static class ICFootprintOpenHelper extends SQLiteOpenHelper {
    	private final Context mHelperContext;
        private SQLiteDatabase mDatabase;
        
        /* Note that FTS3 does not support column constraints and thus, you cannot
         * declare a primary key. However, "rowid" is automatically used as a unique
         * identifier, so when making requests, we will use "_id" as an alias for "rowid"
         */
        private static final String FPN_TABLE_CREATE =
                    "CREATE VIRTUAL TABLE " + FPN_VIRTUAL_TABLE +
                    " USING fts3 (" +
                    KEY_FILENAME + ", " +
                    KEY_DESCRIPTION + ");";
        
		public ICFootprintOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			mDatabase = db;
			mDatabase.execSQL(FPN_TABLE_CREATE);//TODO: run the create table query
			loadICFootprintTable();
		}

		private void loadICFootprintTable(){
			new Thread(new Runnable() {
                public void run() {
                    try {
                    	loadICFootprintLines();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
		}
		
		private void loadICFootprintLines() throws IOException {
            Log.d(TAG, "Loading words...");
            final Resources resources = mHelperContext.getResources();
            InputStream inputStream = resources.openRawResource(R.id.abs__action_bar);//TODO: fix the resource
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            //TODO: modify following code to represent the actual file format.
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] strings = TextUtils.split(line, "-");
                    if (strings.length < 2) continue;
                    long id = addOneICFootprint(strings[0].trim(), strings[1].trim());
                    if (id < 0) {
                        Log.e(TAG, "unable to add word: " + strings[0].trim());
                    }
                }
            } finally {
                reader.close();
            }
            Log.d(TAG, "DONE loading words.");
        }
		
		public long addOneICFootprint(String fname, String desc) {
			//add one ICFootprint info into database
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_FILENAME, fname);
            initialValues.put(KEY_DESCRIPTION, desc);

            return mDatabase.insert(FPN_VIRTUAL_TABLE, null, initialValues);
        }
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FPN_VIRTUAL_TABLE);
            onCreate(db);
		}
    
    }
}
