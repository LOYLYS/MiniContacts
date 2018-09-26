package vn.framgia.vhlee.minicontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

public class ContactDataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "ContactDatabase.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "contactable";
    private static final String SQL_CREATE = StringUtils.append("CREATE TABLE ", TABLE_NAME,
            "(_ID TEXT PRIMARY KEY, NAME TEXT, PHONE TEXT)");
    private static final String SQL_DROP = StringUtils.append("DROP TABLE IF EXISTS ", TABLE_NAME);
    private static final String ID_WHERE = "_ID = ";

    ContactDataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        onCreate(db);
    }

    Cursor getContacts(String id, String[] projection, String selection,
                       String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        if (id != null) {
            queryBuilder.appendWhere(ID_WHERE + id);
        }
        if (sortOrder == null || sortOrder == "") {
            sortOrder = Constants.DEFAULT_SORT;
        }
        Cursor cursor = queryBuilder.query(getReadableDatabase(),
                projection, selection, selectionArgs,
                null, null, sortOrder);
        return cursor;
    }

    public long insert(ContentValues values) {
        return getWritableDatabase().insert(TABLE_NAME, "", values);
    }

    public int delete(String id) {
        return getWritableDatabase().delete(TABLE_NAME, Constants.SQL_WHERE, new String[]{id});
    }
}
