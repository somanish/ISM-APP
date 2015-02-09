package com.samsidx.ismappbeta.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.samsidx.ismappbeta.data.ScheduleData;

class DBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "ism";

	// Table Names
	static final String TABLE_SCHEDULE = "schedule";

	// Common column names
	static final String KEY_ID = "_id";
	static final String KEY_SUB = "sub";
	static final String KEY_SLOT = "slot";
	static final String KEY_DAY = "day";

	// Table Create Statements
	private static final String CREATE_TABLE_SCHEDULE = "CREATE TABLE "
			+ TABLE_SCHEDULE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SUB
			+ " TEXT,"+KEY_SLOT+" INTEGER,"+KEY_DAY+" INTEGER"+ ")";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_SCHEDULE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
		onCreate(db);
	}

	// ------------------------ "Schedules" table methods ----------------//
	// Creating a Schedule
	public long createSch(ScheduleData sch,String sub) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_SUB, sub);
		values.put(KEY_SLOT, sch.getSlot());
		values.put(KEY_DAY, sch.getDay());
		// insert row
		long sch_id = db.insert(TABLE_SCHEDULE, null, values);
		return sch_id;
	}

	//Get Schedule by Day
	public Cursor getSchByDay(int day) {
		SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_SCHEDULE + " WHERE "
                + KEY_DAY + " = " + day+" ORDER BY "+KEY_SLOT;
		Cursor c = db.rawQuery(selectQuery, null);
		return c;
	}

    //Get Max slot of the day
    public Cursor getMaxSlotOfDay(int day) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  MAX("+KEY_SLOT+") as "+KEY_SLOT+" FROM " + TABLE_SCHEDULE + " WHERE "
                + KEY_DAY + " = " + day;
        Cursor c = db.rawQuery(selectQuery, null);
        return c;
    }

	// getting Schedule count
	public int getSchCount() {
		String countQuery = "SELECT  * FROM " + TABLE_SCHEDULE;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int count = cursor.getCount();
		cursor.close();
		// return count
		return count;
	}

	// Updating a Schedule
	public int updateSch(ScheduleData sch,String sub) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, sch.getId());
		values.put(KEY_SUB, sub);
		values.put(KEY_SLOT, sch.getSlot());
		values.put(KEY_DAY, sch.getDay());
		// updating row
		return db.update(TABLE_SCHEDULE, values, KEY_ID + " = ?",
				new String[] { String.valueOf(sch.getId()) });
	}
	//Deleting a Schedule
	public void deleteSch(long sch_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SCHEDULE, KEY_ID + " = ?",
				new String[] { String.valueOf(sch_id) });
	}
    //Deleting all Schedule
    public void deleteAllSch() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCHEDULE, null,null);
    }

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}
}
