package com.myapplicationdev.android.taskmanager;

import java.io.Serializable;

public class Task implements Serializable{

    private Integer id;
    private String title;
    private String description;

    public Task(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
