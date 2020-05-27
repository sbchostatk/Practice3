package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

public class BackAsyncTask extends AsyncTask<String, Void, Void> {
    DBHelper dbHelper;
    BackPagerAdapter adapter;

    public BackAsyncTask(DBHelper dbHelper, BackPagerAdapter adapter) {
        super();
        this.dbHelper = dbHelper;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... params) {
        String id = params[0];
        String name = params[1];
        String price = params[2];
        String count = params[3];

        try {
            int time = (int) (Math.random()*(2000+1)) + 3000;
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.execSQL("UPDATE mytable SET name = ?, price = ?, count = ? WHERE id = ?", new String[]{name, price, count, id});
        db.close();


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        this.adapter.updateCursor();
    }
}
