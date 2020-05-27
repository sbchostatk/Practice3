package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class MyPagerAdapter extends CursorRecyclerAdapter<PagerVH> {
    DBHelper dbHelper;
    SQLiteDatabase db;

    public MyPagerAdapter(DBHelper dbHelper) {
        super(null);
        this.dbHelper = dbHelper;
        db = dbHelper.getReadableDatabase();
        updateCursor();
    }

    public void updateCursor() {
        if (!db.isOpen()) {
            db = dbHelper.getReadableDatabase();
        }
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        swapCursor(c);
    }

    public PagerVH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (!db.isOpen()) {
            db = dbHelper.getReadableDatabase();
        }
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page, parent, false);
        return new PagerVH(itemView);
    }

    public void onBindViewHolder(final PagerVH holder, final Cursor c) {
        holder.textView.setText(c.getString(1) + "\nСтоимость: " + c.getInt(2) + "\nКоличество: " + c.getInt(3));

        final long id = c.getLong(0);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsyncTask task = new MyAsyncTask(dbHelper, MyPagerAdapter.this);
                task.execute(id);
                holder.textView.setText(c.getString(1) + "\nСтоимость: " + c.getInt(2) + "\nКоличество: " + c.getInt(3));

                Cursor c = db.query("mytable", null, null, null, null, null, null);
                swapCursor(c);
            }
        });
    }
}

class PagerVH extends RecyclerView.ViewHolder {
    TextView textView;
    Button button;

    public PagerVH(View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView);
        button = itemView.findViewById(R.id.button);
    }
}
