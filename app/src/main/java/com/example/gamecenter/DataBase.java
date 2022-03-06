package com.example.gamecenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {

    private static final String TAG = DataBase.class.getSimpleName();
    private static final int DATABASE_VERSION = 1;
    private static final String SCORE_LIST_TABLE = "scores_entries";
    private static final String DATABASE_NAME = "scores";

    private static final String KEY_ID = "_id";
    private static final String USER = "user";
    private static final String PASSWORD = "pass";
    private static final String SCORE_2048 = "score_2048";
    private static final String SCORE_PEG = "score_peg";

    private static final String[] COLUMNS = {KEY_ID, USER, PASSWORD, SCORE_2048, SCORE_PEG};

    private static final String SCORE_TABLE_CREATE =
            "CREATE TABLE " + SCORE_LIST_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    USER + " TEXT, " +
                    PASSWORD + " TEXT, " +
                    SCORE_2048 + " INTEGER, " +
                    SCORE_PEG + " INTEGER );";

    private SQLiteDatabase mWriteableDB;
    private SQLiteDatabase mRedeableDB;

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SCORE_TABLE_CREATE);
        fillDataBaseWithData(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(DataBase.class.getName(),
                "Upgrading database from version " + oldVersion + " to " +
                        newVersion + ", wich will destroy all old data");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SCORE_LIST_TABLE);
        onCreate(sqLiteDatabase);
    }

    private void fillDataBaseWithData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(USER, "admin");
        values.put(PASSWORD, "admin");
        values.put(SCORE_2048, 0);
        values.put(SCORE_PEG, -10);
        db.insert(SCORE_LIST_TABLE, null, values);
    }

    public long count() {
        if (mRedeableDB == null) {
            mRedeableDB = getReadableDatabase();
        }
        return DatabaseUtils.queryNumEntries(mRedeableDB, SCORE_LIST_TABLE);
    }

    public long insert(String user, String pass) {
        long newId = 0;
        int scores = 0;
        ContentValues values = new ContentValues();
        values.put(USER, user);
        values.put(PASSWORD, pass);
        values.put(SCORE_2048, scores);
        values.put(SCORE_PEG, -10);
        try {
            if (mWriteableDB == null) {
                mWriteableDB = getWritableDatabase();
            }
            newId = mWriteableDB.insert(SCORE_LIST_TABLE, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newId;
    }

    public String[] returnData(String user) {
        String data[] = new String[3];
        String query = "SELECT * FROM " + SCORE_LIST_TABLE + " WHERE " + USER + " = ?";
        Cursor cursor = null;
        try {
            if (mRedeableDB == null) {
                mRedeableDB = getReadableDatabase();
            }
            cursor = mRedeableDB.rawQuery(query, new String[]{user});
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                data[0] = cursor.getString(1);
                data[1] = cursor.getString(3);
                data[2] = cursor.getString(4);
            }
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e.getMessage());
        }
        return data;
    }

    public boolean checkUserPass(String user, String pass) {
        boolean found = false;
        String query = "SELECT " + USER + ", " + PASSWORD + " FROM " +
                SCORE_LIST_TABLE + " WHERE " + USER + " = ?" + " AND " + PASSWORD + " = ?";
        Cursor cursor = null;
        try {
            if (mRedeableDB == null) {
                mRedeableDB = getReadableDatabase();
            }
            cursor = mRedeableDB.rawQuery(query, new String[]{user, pass});
            if (cursor.getCount() > 0) {
                found = true;
            }
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e.getMessage());
        } finally {
            cursor.close();
        }
        return found;
    }

    public boolean checkUser(String user) {
        boolean found = false;
        String query = "SELECT " + USER + " FROM " +
                SCORE_LIST_TABLE + " WHERE " + USER + " = ?";
        Cursor cursor = null;
        try {
            if (mRedeableDB == null) {
                mRedeableDB = getReadableDatabase();
            }
            cursor = mRedeableDB.rawQuery(query, new String[]{user});
            if (cursor.getCount() > 0) {
                found = true;
            }
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e.getMessage());
        } finally {
            cursor.close();
        }
        return found;
    }

    public int returnHS2048(String user) {
        String query = "SELECT * FROM " + SCORE_LIST_TABLE + " WHERE " + USER + " = ?";
        Cursor cursor = null;
        try {
            if (mRedeableDB == null) {
                mRedeableDB = getReadableDatabase();
            }
            cursor = mRedeableDB.rawQuery(query, new String[]{user});
            cursor.moveToFirst();
            int hs = cursor.getInt(cursor.getColumnIndexOrThrow(SCORE_2048));
            return hs;
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e.getMessage());
        } finally {
            cursor.close();
        }
        return -1;
    }

    public int update2048(String user, int score) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWriteableDB == null) {
                mWriteableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(SCORE_2048, score);
            mNumberOfRowsUpdated = mWriteableDB.update(SCORE_LIST_TABLE, values,
                    USER + " = ?", new String[]{user});
        } catch (Exception e) {
            Log.d(TAG, "UPDATE EXCEPTION " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }

    public int returnHSPeg(String user) {
        String query = "SELECT * FROM " + SCORE_LIST_TABLE + " WHERE " + USER + " = ?";
        Cursor cursor = null;
        try {
            if (mRedeableDB == null) {
                mRedeableDB = getReadableDatabase();
            }
            cursor = mRedeableDB.rawQuery(query, new String[]{user});
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndexOrThrow(SCORE_PEG));
        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e.getMessage());
        } finally {
            cursor.close();
        }
        return -1;
    }

    public int updatePeg(String user, int score) {
        int mNumberOfRowsUpdated = -1;
        try {
            if (mWriteableDB == null) {
                mWriteableDB = getWritableDatabase();
            }
            ContentValues values = new ContentValues();
            values.put(SCORE_PEG, score);
            mNumberOfRowsUpdated = mWriteableDB.update(SCORE_LIST_TABLE, values,
                    USER + " = ?", new String[]{user});
        } catch (Exception e) {
            Log.d(TAG, "UPDATE EXCEPTION " + e.getMessage());
        }
        return mNumberOfRowsUpdated;
    }
}
