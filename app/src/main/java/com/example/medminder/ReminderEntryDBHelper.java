package com.example.medminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ReminderEntryDBHelper extends SQLiteOpenHelper {
    //db info (file name, version, table name)
    private static final String DATABASE_NAME = "reminder_entries.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME_ENTRIES = "entry";

    //keys for db columns
    public static final String COL_ROW = "_id";
    public static final String COL_REMINDER_TYPE = "reminder_type";
    public static final String COL_MEDICATION_TYPE = "medication_type";
    public static final String COL_DATE_TIME = "date_time";
    public static final String COL_NAME = "medication_name";
    public static final String COL_TYPE = "nedication_type";
    public static final String COL_NOTES = "notes";


    private SQLiteDatabase database;

    //db table schema
    public static final String CREATE_TABLE_ENTRIES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME_ENTRIES + " ("
            + COL_ROW + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_REMINDER_TYPE + " INTEGER NOT NULL, "
            + COL_MEDICATION_TYPE + " INTEGER NOT NULL, "
            + COL_DATE_TIME + " DATETIME NOT NULL, "
            + COL_NAME + " TEXT, "
            + COL_TYPE + " TEXT, "
            + COL_NOTES + " TEXT);";

    //Following method headers and comments are from course web page:
    //https://www.cs.dartmouth.edu/~xingdong/Teaching/CS65/myruns/database.html


    public ReminderEntryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Execute the db creation
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_ENTRIES);
    }

    /**
     * Handle table upgrades
     *
     * @param db         database
     * @param oldVersion old version number
     * @param newVersion new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ENTRIES);
        onCreate(db);
    }

    /**
     * Insert an ExerciseEntry into db
     */
    public long insertEntry(ReminderEntry entry) {
        //put all the column values into a new row
        ContentValues values = new ContentValues();
        values.put(COL_REMINDER_TYPE, entry.getmReminderType());
        values.put(COL_MEDICATION_TYPE, entry.getmMReminderMedicationType());
        values.put(COL_DATE_TIME, entry.getmDateTime());
        values.put(COL_NAME, entry.getmMedicationName());
        values.put(COL_TYPE, entry.getmReminderType());
        values.put(COL_NOTES, entry.getmMedicationNotes());
//        //change latlng list of positions to byte array
        Gson gson = new Gson();
        String json = gson.toJson(entry.getmLatLngs());
        Log.d("LPC", "insertEntry: saving lat lngs list of length "+entry.getmLatLngs().size());

        //insert new row into db
//        SQLiteDatabase database = getWritableDatabase();
        database = getWritableDatabase();
        long insertedId = database.insert(TABLE_NAME_ENTRIES, null, values);
        //close database
        database.close();
        return insertedId;
    }

    /**
     * Remove an entry by its id in the db
     */
    public void removeEntry(long id) {
//        SQLiteDatabase database = getWritableDatabase();
        database = getWritableDatabase();
        database.delete(TABLE_NAME_ENTRIES,
                COL_ROW + " = " + id, null);
        //close database
        database.close();

    }

    /**
     * Query a specific entry by its id.
     * @param rowId     id of the entry to fetch
     * @return          the ExerciseEntry object at rowId
     */
    public ReminderEntry fetchEntryByIndex(long rowId) {
//        SQLiteDatabase database = getWritableDatabase();
        database = getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME_ENTRIES, null,
                COL_ROW + " = " + rowId, null, null,
                null, null);
        cursor.moveToFirst();
        ReminderEntry entry = covertCursorToEntry(cursor);

        //close cursor and database
        cursor.close();
        database.close();

        return entry;
    }


    public ReminderEntry fetchEntryByTime() {
//        SQLiteDatabase database = getWritableDatabase();
        database = getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from entry order by date_time asc", null);
        cursor.moveToFirst();
        ReminderEntry entry = covertCursorToEntry(cursor);

        //close cursor and database
        cursor.close();
        database.close();

        return entry;
    }

    /**
     * Query the entire table, return all rows
     * @return  all ExerciseEntry objects in the db in a list
     */
    public ArrayList<ReminderEntry> fetchAllEntries() {
        Log.d("fetch all", "fetchAllEntries: here");
//                SQLiteDatabase database = getWritableDatabase();
        database = getWritableDatabase();
        ArrayList<ReminderEntry> entries = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME_ENTRIES,
                null, null, null, null, null, null);

        cursor.moveToFirst();
        //loop through all entries
        while (!cursor.isAfterLast()) {
            ReminderEntry comment = covertCursorToEntry(cursor);
            entries.add(comment);
            cursor.moveToNext();
        }
        //close cursor and database
        cursor.close();
        database.close();
        Log.d("=======",new Gson().toJson(entries));
        return entries;
    }

    /**
     * Get the ExerciseEntry at the Cursor
     * @param cursor    Cursor
     * @return          ExerciseEntry at the cursor
     */
    public ReminderEntry covertCursorToEntry(Cursor cursor) {
        //make an entry and fill in its values
        ReminderEntry entry = new ReminderEntry();
        entry.setId(cursor.getLong(0));
        entry.setmReminderType(cursor.getInt(1));
        entry.setmMReminderMedicationType(cursor.getInt(2));
        entry.setmDateTime(cursor.getLong(3));
        entry.setmMedicationName(cursor.getString(4));
        entry.setmMedicationType(cursor.getString(5));
        entry.setmMedicationNotes(cursor.getString(6));
        //convert byte array to ArrayList of LatLng
        Gson gson = new Gson();
//        try {
//        if(cursor.getBlob(6) != null) {
//            String json = new String(cursor.getBlob(6));
//            Type type = new TypeToken<ArrayList<LatLng>>() {
//            }.getType();
////        entry.setmLatLngs((ArrayList<LatLng>)gson.fromJson(json, type));
//            entry.setmLatLngs(gson.fromJson(json, type));
//            Log.d("LPC", "retrieved and turned lat lngs into list ");
////        } catch (NullPointerException e){
////            Log.d("LPC", "lat lngs null ");
////            e.printStackTrace();
////        }
//        } else {
//            Log.d("LPC", "lat lngs null ");
//        }

        return entry;
    }
}
