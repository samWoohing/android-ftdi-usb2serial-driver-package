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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
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
    
	/**
     * Returns a Cursor over all words that match the given query
     *
     * @param query The string to search for
     * @param columns The columns to include, if null then all are included
     * @return Cursor over all words that match, or null if none found.
     */
    public Cursor getICFootprintMatches(String query, String[] columns) {
        String selection = KEY_DESCRIPTION + " MATCH ?";
        //TODO: maybe modify this query to ignore upper case and lower case
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);

        /* This builds a query that looks like:
         *     SELECT <columns> FROM <table> WHERE <KEY_WORD> MATCH 'query*'
         * which is an FTS3 search for the query text (plus a wildcard) inside the word column.
         *
         * - "rowid" is the unique id for all rows but we need this value for the "_id" column in
         *    order for the Adapters to work, so the columns need to make "_id" an alias for "rowid"
         * - "rowid" also needs to be used by the SUGGEST_COLUMN_INTENT_DATA alias in order
         *   for suggestions to carry the proper intent data.
         *   These aliases are defined in the DictionaryProvider when queries are made.
         * - This can be revised to also search the definition text with FTS3 by changing
         *   the selection clause to use FTS_VIRTUAL_TABLE instead of KEY_WORD (to search across
         *   the entire table, but sorting the relevance could be difficult.
         */
    }
	
	/**
     * Returns a Cursor positioned at the word specified by rowId
     *
     * @param rowId id of word to retrieve
     * @param columns The columns to include, if null then all are included
     * @return Cursor positioned to matching word, or null if not found.
     */
    public Cursor getICFootprintByRowId(String rowId, String[] columns) {
        String selection = "rowid = ?";
        String[] selectionArgs = new String[] {rowId};

        return query(selection, selectionArgs, columns);

        /* This builds a query that looks like:
         *     SELECT <columns> FROM <table> WHERE rowid = <rowId>
         */
    }
    
	/**
     * Performs a database query.
     * @param selection The selection clause
     * @param selectionArgs Selection arguments for "?" components in the selection
     * @param columns The columns to return
     * @return A Cursor over all rows matching the query
     */
    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        /* The SQLiteBuilder provides a map for all possible columns requested to
         * actual columns in the database, creating a simple column alias mechanism
         * by which the ContentProvider does not need to know the real column names
         */
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FPN_VIRTUAL_TABLE);
        builder.setProjectionMap(mColumnMap);

        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
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
            InputStream inputStream = resources.openRawResource(R.raw.footprintlist);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] strings = TextUtils.split(line, ";");
                    if (strings.length < 2) continue;
                    long id = addOneICFootprint(strings[1].trim(), strings[0].trim());
                    if (id < 0) {
                        Log.e(TAG, "unable to add footprint: " + strings[0].trim());
                    }
                }
            } finally {
                reader.close();
            }
            Log.d(TAG, "DONE loading footprint.");
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
