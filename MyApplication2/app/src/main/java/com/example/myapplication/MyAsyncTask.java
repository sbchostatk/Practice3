package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import java.util.Random;

public class MyAsyncTask extends AsyncTask<Long, Void, Void> {
    DBHelper dbHelper;
    MyPagerAdapter adapter;

    public MyAsyncTask(DBHelper dbHelper, MyPagerAdapter adapter) {
        super();
        this.dbHelper = dbHelper;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(Long... params) {
        long id = params[0];

        try {
            int time = (int) (Math.random()*(2000+1)) + 3000;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("UPDATE mytable SET count = count - 1 WHERE id = ? AND count > 0", new String[]{ String.valueOf(id)});
        db.close();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        this.adapter.updateCursor();
    }
}
