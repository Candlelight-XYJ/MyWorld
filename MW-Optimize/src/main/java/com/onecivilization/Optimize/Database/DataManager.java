package com.onecivilization.Optimize.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.onecivilization.Optimize.Database.Schema.CareItemTable;
import com.onecivilization.Optimize.Model.Care;
import com.onecivilization.Optimize.Model.Problem;
import com.onecivilization.Optimize.Model.TextCare;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CGZ on 2016/7/10.
 */
public class DataManager {

    private static DataManager dataManager;
    private SQLiteDatabase db;
    private List<Care> careList;
    private List<Care> careHistoryList;
    private List<Problem> problemList;
    private List<Problem> problemHistoryList;

    private DataManager(Context context) {
        db = new DatabaseOpenHelper(context).getWritableDatabase();
        loadCareList();
        loadProblemList();
    }

    public synchronized static DataManager getInstance(Context context) {
        if (dataManager == null) {
            dataManager = new DataManager(context);
        }
        return dataManager;
    }

    public void loadCareList() {
        careList = new ArrayList<>();
        Cursor cursor = db.rawQuery("select * from " + CareItemTable.NAME + " order by " + CareItemTable.Cols.ORDER, null);
        if (cursor.moveToFirst()) {
            do {
                long createTime = cursor.getLong(cursor.getColumnIndex(CareItemTable.Cols.CREATE_TIME));
                int order = cursor.getInt(cursor.getColumnIndex("order"));
                String title = cursor.getString(cursor.getColumnIndex(CareItemTable.Cols.TITLE));
                String descriptionTitle = cursor.getString(cursor.getColumnIndex(CareItemTable.Cols.DESCRIPTION_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(CareItemTable.Cols.DESCRIPTION));
                long achievedTime = cursor.getLong(cursor.getColumnIndex(CareItemTable.Cols.ACHIEVED_TIME));
                boolean isAchieved = (achievedTime != 0L);
                int type = cursor.getInt(cursor.getColumnIndex(CareItemTable.Cols.TYPE));
                switch (type) {
                    case Care.TEXT:
                        int color = cursor.getInt(cursor.getColumnIndex(CareItemTable.Cols.COLOR));
                        careList.add(new TextCare(title, descriptionTitle, description, order, null, isAchieved, false, createTime, achievedTime, 0L, color));
                        break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void loadProblemList() {
    }

    public boolean addCareItem(Care careItem) {
        try {
            ContentValues values = new ContentValues();
            values.put(CareItemTable.Cols.CREATE_TIME, careItem.getCreateTime());
            values.put(CareItemTable.Cols.TYPE, careItem.getType());
            values.put(CareItemTable.Cols.ORDER, careItem.getOrder());
            values.put(CareItemTable.Cols.TITLE, careItem.getTitle());
            values.put(CareItemTable.Cols.DESCRIPTION, careItem.getDescriptionTitle());
            values.put(CareItemTable.Cols.DESCRIPTION, careItem.getDescription());
            values.put(CareItemTable.Cols.ACHIEVED_TIME, careItem.getAchievedTime());
            switch (careItem.getType()) {
                case Care.TEXT:
                    values.put(CareItemTable.Cols.COLOR, ((TextCare) careItem).getColor());
                    break;
            }
            db.insert(CareItemTable.NAME, null, values);
            careList.add(careItem);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean deleteCareItem(Care careItem) {
        return false;
    }

    public boolean addProblemItem(Problem problemItem) {
        return false;
    }

    public boolean deleteProblemItem(Problem problemItem) {
        return false;
    }

    public List<Care> getCareList() {
        return careList;
    }

    public void setCareList(List<Care> CareList) {
        careList = CareList;
    }

    public List<Problem> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<Problem> ProblemList) {
        problemList = ProblemList;
    }
}