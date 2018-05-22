package com.myapplicationdev.android.taskmanager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button btnAdd;
    ArrayList<String> alTask = new ArrayList<String>();
    ArrayAdapter aaTask;
    int reqCode =12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView)findViewById(R.id. lv);
        btnAdd = (Button) findViewById(R.id. btnAdd);

        DBHelper db = new DBHelper(MainActivity.this);

        // Insert a task
        ArrayList<String> data = db.getTasks();
        alTask.clear();
        alTask.addAll(data);

        aaTask = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, alTask);
        lv.setAdapter(aaTask);
        db.close();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(i, 9);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 9) {
            DBHelper db = new DBHelper(MainActivity.this);
            alTask.clear();
            alTask.addAll(db.getTasks());
            aaTask = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, alTask);
            lv.setAdapter(aaTask);
            aaTask.notifyDataSetChanged();

            //Intent i = getIntent();
            String title = data.getStringExtra("task");
            int time = data.getIntExtra("time", 0);

            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, time);

            Log.d("title", "sending: " + title);
            Intent intent = new Intent(MainActivity.this, ScheduledNotificationReceiver.class);
            intent.putExtra("taskName", title);

            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(MainActivity.this, reqCode, intent,
                            PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager am = (AlarmManager)
                    getSystemService(Activity.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
    }
}
