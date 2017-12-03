package com.example.maxibon.todolistapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxibon.todolistapp.model.TaskDTO;
import com.example.maxibon.todolistapp.R;
import com.example.maxibon.todolistapp.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.tmp_sda_1107.todolistapp.view.CONTROLLER_MESSAGE";
    public static final String PASSED_INFO = "com.example.tmp_sda_1107.todolistapp.view.TITLE_MESSAGE";
    public static final float TITLE_SIZE = 25;
    public static final float DESCRIPTION_SIZE = 15;

    Controller controller;
    ArrayAdapter<TaskDTO> taskAddapter;
    ArrayList<TaskDTO> tasklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new Controller();
        tasklist = controller.loadTasks(this.getApplication().getBaseContext());

        ListView sv = (ListView) findViewById(R.id.list_of_items);
        sv.setClickable(true);
        taskAddapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, tasklist);
        sv.setAdapter(taskAddapter);

        //Long click listener for deleting an element from the task list
         sv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                           @Override
                                           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                       controller.remove(tasklist.get(position));
                       tasklist.remove(position);
                       taskAddapter.notifyDataSetChanged();
                         return true;

                   }
               });
        }

    /**
     * The action to preform after the results have come back from another activity,
     * requestCode 1 being the code for creating a new task for the todolist
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        //The request for creating a new Task for the todolist
        if(requestCode == 1){
            if(resultCode == RESULT_OK){

                String newTitle = data.getStringExtra(AddingActivity.TITLE);
                String newDescription = data.getStringExtra(AddingActivity.DESCRIPT);

                TaskDTO newTask = new TaskDTO(newTitle, newDescription);

                controller.add(newTask);
                taskAddapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * The method handling the button for adding a new task to the todolist,
     * goes to the <Code>AddingActivity</Code> activity
     *
     * @param view, the button view Item.
     */
    public void add(View view){

        Intent myIntent = new Intent(this, AddingActivity.class);
        startActivityForResult(myIntent, 1);
    }

    /**
     * The overriden method that is called when application is shutdown,
     * Stores all the Tasks from the tasklist via the controller
     *
     */
    @Override
    protected void onStop() {
        super.onStop();
        controller.storeTaskList(this.getApplication().getBaseContext());
    }
}


