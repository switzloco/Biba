package n.switzloco.beerbatterycharger;

import android.provider.BaseColumns;

public class workoutDBcontract {


	
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public void workoutDBContract() {}

    /* Inner class that defines the table contents */
    public static abstract class workoutEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String COLUMN_NAME_WORKOUT = "title";
        public static final String COLUMN_NAME_DRINKS_EARNED = "drinks earned";
        public static final String COLUMN_NAME_WORKOUT_VALUE = "workout value";
        public static final String COLUMN_NAME_DATE = "date";
    	private static final String TEXT_TYPE = " TEXT";
    	private static final String COMMA_SEP = ",";
    	
    	public static final String SQL_CREATE_ENTRIES =
    		    "CREATE TABLE " + workoutEntry.TABLE_NAME + " (" +
    		    workoutEntry._ID + " INTEGER PRIMARY KEY," +
    		    workoutEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
    		    workoutEntry.COLUMN_NAME_WORKOUT + TEXT_TYPE + COMMA_SEP +
    		    workoutEntry.COLUMN_NAME_DRINKS_EARNED + TEXT_TYPE + COMMA_SEP +
    		    workoutEntry.COLUMN_NAME_WORKOUT_VALUE + TEXT_TYPE + COMMA_SEP +
    		    workoutEntry.COLUMN_NAME_DATE + TEXT_TYPE + " )";
    	

    		public static final String SQL_DELETE_ENTRIES =
    		    "DROP TABLE IF EXISTS " + workoutEntry.TABLE_NAME;
			public static final String COLUMN_NAME_NULLABLE = null;
    }

    


}
