package n.switzloco.beerbatterycharger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class workoutDBHelper extends SQLiteOpenHelper {

	// If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public workoutDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(workoutDBcontract.workoutEntry.SQL_CREATE_ENTRIES);
    }
    @Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(workoutDBcontract.workoutEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
} 
