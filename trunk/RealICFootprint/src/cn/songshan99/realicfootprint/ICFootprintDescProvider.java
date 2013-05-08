package cn.songshan99.realicfootprint;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class ICFootprintDescProvider extends ContentProvider {
	
	private ICFootprintDescDatabase mICFootprintDescDatabase;
	private static final UriMatcher sURIMatcher = buildUriMatcher();
	
	/**
     * Builds up a UriMatcher for search suggestion and shortcut refresh queries.
     */
    private static UriMatcher buildUriMatcher() {
    	UriMatcher matcher =  new UriMatcher(UriMatcher.NO_MATCH);
    	//TODO: finish the content
    	return matcher;
    }
    
	@Override
	public String getType(Uri uri) {
		// TODO: implement
		return null;
	}

	@Override
	public boolean onCreate() {
		mICFootprintDescDatabase = new ICFootprintDescDatabase(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		throw new UnsupportedOperationException();
	}
}
