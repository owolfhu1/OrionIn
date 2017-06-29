package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int workId;
    private String thisTask;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getWorkId() {
        return workId;
    }
    public void setWorkId(int workId) {
        this.workId = workId;
    }
    public String getThisTask() {
        return thisTask;
    }
    public void setThisTask(String thisTask) {
        this.thisTask = thisTask;
    }
}
