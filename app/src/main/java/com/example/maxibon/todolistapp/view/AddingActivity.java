package com.example.maxibon.todolistapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.maxibon.todolistapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tmp-sda-1107 on 11/3/17.
 */

public class AddingActivity extends AppCompatActivity {

    public final static String TITLE = "com.example.maxibon.todolistapp.view.TITLE_MESSAGE";
    public final static String DESCRIPT = "com.example.maxibon.todolistapp.view.DESCRIPTION_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_task);

    }

    /**
     *
     * The button handler for saving a task, will get both the text fields from
     * the current activity page and pass them to the main activity on press
     *
     * @param view, the button that has been pressed.
     */
    public void createTask(View view){

        String title = ((EditText) findViewById(R.id.title)).getText().toString();
        String description = ((EditText) findViewById(R.id.description)).getText().toString();

        if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            if (TextUtils.isEmpty(title))
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();

            if (TextUtils.isEmpty(description))
                Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent();
            intent.putExtra(TITLE, title);
            intent.putExtra(DESCRIPT, description);

            setResult(RESULT_OK, intent);
            this.finish();
        }
    }
}
