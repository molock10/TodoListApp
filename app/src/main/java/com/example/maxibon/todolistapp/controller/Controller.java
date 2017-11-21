package com.example.maxibon.todolistapp.controller;

import android.app.ActivityManager;
import android.content.Context;

//import com.example.maxibon.todolistapp.model.JSONReader;
//import com.example.maxibon.todolistapp.model.JSONWriter;
import com.example.maxibon.todolistapp.model.TaskDTO;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tmp-sda-1107 on 11/7/17.
 */

public class Controller implements Serializable{

    private ArrayList<TaskDTO> taskList;
    private boolean modified;

    public Controller(){

        modified = false;
        taskList = new ArrayList<>();
        taskList.add(new TaskDTO("Title","descripop")); // just temp code
    }


    /**
     * Adds a new Task to the arraylist <Code>TaskList</Code>
     *
     *
     * @param newTask, the new task in which to create
     * @return false if task title already exists or fields unfilled, else true
     */
    public boolean add(TaskDTO newTask){

        if(!taskExists(newTask) && fieldsAcceptable(newTask)){

            taskList.add(newTask);
            modified = true;
            return true;
        }
        return false;
    }

    /**
     * Checks if the tasklist has any tasks matching the same titles.
     *
     *
     * @param newTask
     * @return true if the task exists, otherwise false
     */
    private boolean taskExists(TaskDTO newTask) {

        if(!taskList.isEmpty()) {
            Iterator<TaskDTO> it = taskList.iterator();

            while (it.hasNext()) {
                TaskDTO task = it.next();

                if (task.getDescription().equals(newTask.getDescription())) {
                    return true;
                }
                if (task.getTitle().equals(newTask.getTitle())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * A method to ensure that the taskDTO fields are not null
     *
     * @param taskToCheck
     * @return true if all fields are not null
     */
    private boolean fieldsAcceptable(TaskDTO taskToCheck){

        return taskToCheck.getTitle() != null && taskToCheck.getDescription() != null;
    }

    /**
     *
     * Remove a task from the tasklist on the basis of matching titles.
     *
     * @param oldTask, the task in which to remove
     * @return true, if the task did exist and was removed successfully, else false
     */
    public boolean remove(TaskDTO oldTask){

        if(taskExists(oldTask)) {
            Iterator<TaskDTO> it = taskList.iterator();
            while (it.hasNext()){
                TaskDTO taskToRemove = it.next();
                if(taskToRemove.getTitle().equals(oldTask)){
                    it.remove();
                    modified = true;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Loads all the taskDTO objects into the tasklist from the database
     *
     *
     * @return tasklist, the arraylist of taskDTOs in which represents the todolist
     */
    public ArrayList<TaskDTO> loadTasks(Context context){

        //new JSONReader("database.json", context);
                //new TaskRegistry().getTasks(file);
        return taskList;
    }

    /**
     *
     * Sends all the tasks from the tasklist to the taskregistry in order to save to a json file.
     *
     * @param context, the context in which has access to Internal Storage
     */
    public void storeTaskList(Context context){

        //new JSONWriter("database.json", context);
        new TaskRegistry("database.json", context).storeTasks(taskList);
    }

}
