package com.example.maxibon.todolistapp.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by tmp-sda-1107 on 11/7/17.
 */

public class TaskDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private String title;
    private String description;

    public TaskDTO(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // ---- Getters And Setters ----

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString(){
        String i = (title + "\n-" + description);
        return i;
    }



}
