package com.myapplicationdev.android.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText etName, etDesc, etSec;
    Button btnAddTask, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        etName = (EditText)findViewById(R.id. etName);
        etDesc = (EditText)findViewById(R.id. etDesc);
        etSec = (EditText)findViewById(R.id. etSeconds);
        btnAddTask = (Button)findViewById(R.id. btnAddTask);
        btnCancel = (Button)findViewById(R.id. btnCancel);
        final Intent i = getIntent();

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String desc = etDesc.getText().toString();
                int seconds = Integer.parseInt(etSec.getText().toString());

                DBHelper db = new DBHelper(AddActivity.this);
                db.getWritableDatabase();

                db.insertTask(name,desc);
                db.close();
                i.putExtra("requestCode",9);
                i.putExtra("task", etName.getText().toString());
                i.putExtra("time", seconds);
                Log.d("title", "Intent: " + etName.getText().toString() +" "+seconds);
                setResult(RESULT_OK,i);
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
