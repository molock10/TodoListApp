package com.example.maxibon.todolistapp.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.JsonReader;
import android.util.Log;

import com.example.maxibon.todolistapp.model.TaskDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.xml.sax.Parser;
import org.xml.sax.helpers.ParserAdapter;

import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Hudson on 11/7/17.
 */

public class TaskRegistry {

    private final String filename = "database.json";
    Context myContext;

    public TaskRegistry(Context context){
        myContext = context;
    }

    /**
     * reads the data from the json file into an arraylist
     *
     * @return registry, an arraylist of type <Code>TaskDTO</Code>
     */
    public ArrayList<TaskDTO> getTasks() {
        ArrayList<TaskDTO> registry = new ArrayList<>();

        try {
            JSONArray values = load();

            if (values == null) {
                return registry;
            } else {
                for (int i = 1; i < values.length(); i++) {
                    JSONObject object = (JSONObject) values.get(i);
                    String title = object.getString("title");
                    String description = object.getString("description");
                    TaskDTO loadedTask = new TaskDTO(title, description);
                    registry.add(loadedTask);
                }
                return registry;
            }
        }catch(JSONException e){
            Log.e("JSONReadException", e.getMessage());
        }
        return registry;
    }

    /**
     * retrieves the information stored in the json file as a <Code>JSONArray</Code>
     *
     * @return messages, a <Code>JSONArray</Code> of the data stored
     */
    private JSONArray load() throws JSONException{

        JSONArray messages = null;
        try{
            FileInputStream stream = myContext.openFileInput(filename);
            String content = readFully(stream).toString("UTF-8");
            messages = (JSONArray) new JSONTokener(content).nextValue();
            stream.close();

        }catch(IOException e) {
            Log.e("JSONReadException", e.getMessage());
        }
        return messages;
    }

    private ByteArrayOutputStream readFully(InputStream stream) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = stream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos;
    }

    /**
     * Stores all <Code>TaskDTO</Code> instances from the active arraylist <Code>tasklist</Code>
     * into the .json file <Code>database.json</Code>
     *
     * @param tasklist, the list of task to store into the database
     */
    public void storeTasks(ArrayList<TaskDTO> tasklist) {

        try {

            JSONArray tasks = new JSONArray();
            for (TaskDTO task : tasklist) {

                JSONObject object = new JSONObject();
                object.put("title", task.getTitle());
                object.put("description", task.getDescription());
                tasks.put(object);
            }

           FileOutputStream output = myContext.getApplicationContext().openFileOutput(filename, myContext.MODE_PRIVATE);
           output.write(tasks.toString().getBytes());
           output.close();

        } catch (IOException e) {
            Log.e("JSONWriteException", e.getMessage());
        } catch (JSONException e) {
            Log.e("JSONLoadException", e.getMessage());
        }

    }
}


