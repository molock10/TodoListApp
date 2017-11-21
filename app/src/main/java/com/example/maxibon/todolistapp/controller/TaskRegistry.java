package com.example.maxibon.todolistapp.controller;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.JsonReader;

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
 * Created by tmp-sda-1107 on 11/7/17.
 */

public class TaskRegistry {

    String filename;
    Context myContext;

    public TaskRegistry(String filename, Context context) {

        this.filename = filename;
        this.myContext = context;
    }

    /**
     * @return
     */
    public ArrayList<TaskDTO> getTasks(){

        ArrayList<TaskDTO> registry = new ArrayList<>();

        try {
            File fileDirectory = new File(myContext.getFilesDir(), "FeedBookDir");

            if(!fileDirectory.exists()){
                fileDirectory.mkdir();
            }
            File file = new File(fileDirectory, filename);

            if(!file.exists()){
                FileWriter writer = new FileWriter(file);
                writer.append("");
                writer.flush();
                writer.close();
            }

            FileInputStream stream = new FileInputStream(file);
            DataInputStream input = new DataInputStream(stream);

            byte[] buffer = new byte[1024];
            input.read(buffer);
            input.close();

            //String jsonQuery = new String(buffer, "UTF-8");

            String content = readFully(stream).toString("UTF-8");

            JSONArray messages = (JSONArray) new JSONTokener(content).nextValue();


            for (int i = 1; i < messages.length(); i++) {

                JSONObject object = (JSONObject) messages.get(i);
                String title = (String) object.get("title");
                String descrition = (String) object.get("description");
                TaskDTO loadedTask = new TaskDTO(title, descrition);
                registry.add(loadedTask);
            }
            stream.close();
            return registry;

        } catch (Exception e) {

        }
        return registry;
    }


    // temporarily blocked
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
            String writing = tasks.toString(2);

            File fileDir = new File(myContext.getFilesDir(),"FeedBookDir");
            //during first time app load when Directory for internal storage does not exists
            if(!fileDir.exists()){
                fileDir.mkdir();
            }
            File file = new File(fileDir, filename);
            FileWriter writer = new FileWriter(file);

            writer.write(writing);
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (JSONException e) {

        }

    }
}


