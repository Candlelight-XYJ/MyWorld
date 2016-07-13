package com.onecivilization.Optimize.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.onecivilization.Optimize.Database.Schema.CareItemTable;
import com.onecivilization.Optimize.Database.Schema.ProblemItemTable;
import com.onecivilization.Optimize.Database.Schema.RecordTable;
import com.onecivilization.Optimize.Database.Schema.TimePairTable;

/**
 * Created by CGZ on 2016/7/10.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "MyOptimize.db";

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RecordTable.CREATE_STATEMENT);
        db.execSQL(RecordTable.HISTORY_CREATE_STATEMENT);
        db.execSQL(TimePairTable.CREATE_STATEMENT);
        db.execSQL(TimePairTable.HISTORY_CREATE_STATEMENT);
        db.execSQL(CareItemTable.CREATE_STATEMENT);
        db.execSQL(CareItemTable.HISTORY_CREATE_STATEMENT);
        db.execSQL(ProblemItemTable.CREATE_STATEMENT);
        db.execSQL(ProblemItemTable.HISTORY_CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL("alter table " + CareItemTable.NAME + " add column " + CareItemTable.Cols.DESCRIPTION_TITLE + Schema.TEXT);
                db.execSQL("alter table " + CareItemTable.HISTORY_NAME + " add column " + CareItemTable.Cols.DESCRIPTION_TITLE + Schema.TEXT);
        }
    }

}