package com.example.maxibon.todolistapp.view;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.maxibon.todolistapp.R;

/**
 * Created by tmp-sda-1107 on 11/10/17.
 */

public class Pop extends Activity {

    public final static String TITLE = "com.example.tmp_sda_1107.todolistapp.view.TITLE_MESSAGE";
    public final static String DESCRIPT = "com.example.tmp_sda_1107.todolistapp.view.DESCRIPTION_MESSAGE";
    public final static int EDIT = 1;
    public final static int DELETE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_window);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)( width*0.7), (int)(height*0.5));

        String passedInfo = getIntent().getStringExtra(MainActivity.PASSED_INFO);
        String[] parts = passedInfo.trim().split("\\s*-\\s*");


        TextView modifiedTitle = (TextView) findViewById(R.id.edit_title);
        modifiedTitle.setText(parts[0]);

        TextView modifiedDescription = (TextView) findViewById(R.id.edit_description);
        modifiedDescription.setText(parts[1]);
    }

    public void delete(View view){

        String editedTitle = ((EditText) findViewById(R.id.edit_title)).getText().toString();
        String editedDescription = ((EditText) findViewById(R.id.edit_description)).getText().toString();

        Intent intent = new Intent();
        intent.putExtra(TITLE, editedTitle);
        intent.putExtra(DESCRIPT, editedDescription);

        setResult(DELETE, intent);
        this.finish();
    }
}
