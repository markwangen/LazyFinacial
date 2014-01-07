package free.mark.lazyfinacial.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import free.mark.lazyfinacial.beans.Asset;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
	// If you change the database schema, you must increment the database version.
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "LazyFinacial.db";

	private static final String TEXT_TYPE = " TEXT";
	private static final String REAL_TYPE = " REAL";
	private static final String DATE_DEFAULT = " DEFAULT CURRENT_DATE";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
			+ FeedReaderContract.AssetEntry.TABLE_NAME + " ("
			+ FeedReaderContract.AssetEntry._ID + " INTEGER PRIMARY KEY" + COMMA_SEP
			+ FeedReaderContract.AssetEntry.COLUMN_NAME_ITEM + TEXT_TYPE + COMMA_SEP
			+ FeedReaderContract.AssetEntry.COLUMN_NAME_AMOUNT + REAL_TYPE + COMMA_SEP
			+ FeedReaderContract.AssetEntry.COLUMN_NAME_REMARKS + TEXT_TYPE + COMMA_SEP
			+ FeedReaderContract.AssetEntry.COLUMN_NAME_DATE + TEXT_TYPE + DATE_DEFAULT +
			// Any other options for the CREATE command
			" )";

	private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
			+ FeedReaderContract.AssetEntry.TABLE_NAME;

	public FeedReaderDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void insert(Asset asset) {
		// Gets the data repository in write mode
		SQLiteDatabase db = this.getWritableDatabase();

		// Create a new map of values, where column names are the keys
		ContentValues values = new ContentValues();
		values.put(FeedReaderContract.AssetEntry.COLUMN_NAME_ITEM, asset.getItem());
		values.put(FeedReaderContract.AssetEntry.COLUMN_NAME_AMOUNT, asset.getAmount());
		values.put(FeedReaderContract.AssetEntry.COLUMN_NAME_REMARKS, asset.getRemarks());
		values.put(FeedReaderContract.AssetEntry.COLUMN_NAME_DATE, asset.getDate().toString());

		// Insert the new row, returning the primary key value of the new row
		long newRowId = db.insert(
				FeedReaderContract.AssetEntry.TABLE_NAME,
				null,
		         values);
		System.out.println(newRowId + " row are inserted!");
	}
	
	public List<Asset> queryAll() {
		SQLiteDatabase db = this.getReadableDatabase();

		// Define a projection that specifies which columns from the database
		// you will actually use after this query.
		String[] projection = {
		    FeedReaderContract.AssetEntry._ID,
		    FeedReaderContract.AssetEntry.COLUMN_NAME_ITEM,
		    FeedReaderContract.AssetEntry.COLUMN_NAME_AMOUNT,
		    FeedReaderContract.AssetEntry.COLUMN_NAME_REMARKS,
		    FeedReaderContract.AssetEntry.COLUMN_NAME_DATE,
		    };

		// How you want the results sorted in the resulting Cursor
		String sortOrder =
		    FeedReaderContract.AssetEntry._ID + " DESC";

		Cursor c = db.query(
		    FeedReaderContract.AssetEntry.TABLE_NAME,  // The table to query
		    projection,                               // The columns to return
		    null,                                // The columns for the WHERE clause
		    null,                            // The values for the WHERE clause
		    null,                                     // don't group the rows
		    null,                                     // don't filter by row groups
		    sortOrder                                 // The sort order
		    );
		
		c.moveToFirst();
		List<Asset> list = new ArrayList<Asset> ();
		while(c.moveToNext()) {
			Asset asset = new Asset();
			asset.setItem(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.AssetEntry.COLUMN_NAME_ITEM)));
			asset.setAmount(c.getDouble(c.getColumnIndexOrThrow(FeedReaderContract.AssetEntry.COLUMN_NAME_AMOUNT)));
			asset.setItem(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.AssetEntry.COLUMN_NAME_REMARKS)));
			asset.setItem(c.getString(c.getColumnIndexOrThrow(FeedReaderContract.AssetEntry.COLUMN_NAME_DATE)));
			list.add(asset);
		}
		
		return list;
	}
	
	public void update(Asset asset) {
		SQLiteDatabase db = this.getReadableDatabase();

		// New value for one column
		ContentValues values = new ContentValues();
		values.put(FeedReaderContract.AssetEntry.COLUMN_NAME_ITEM, asset.getItem());
		values.put(FeedReaderContract.AssetEntry.COLUMN_NAME_AMOUNT, asset.getAmount());
		values.put(FeedReaderContract.AssetEntry.COLUMN_NAME_REMARKS, asset.getRemarks());
		values.put(FeedReaderContract.AssetEntry.COLUMN_NAME_DATE, asset.getDate().toString());

		// Which row to update, based on the ID
		String selection = FeedReaderContract.AssetEntry._ID + " LIKE ?";
		String[] selectionArgs = { String.valueOf(asset.getId()) };

		int count = db.update(
			FeedReaderContract.AssetEntry.TABLE_NAME,
		    values,
		    selection,
		    selectionArgs);
		System.out.println(count + " row are updated!");
	}
	
	public void delete(Asset asset) {
		SQLiteDatabase db = this.getReadableDatabase();

		// Define 'where' part of query.
		String selection = FeedReaderContract.AssetEntry._ID + " LIKE ?";
		// Specify arguments in placeholder order.
		String[] selectionArgs = { String.valueOf(asset.getId()) };
		// Issue SQL statement.
		db.delete(FeedReaderContract.AssetEntry.TABLE_NAME, selection, selectionArgs);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
}