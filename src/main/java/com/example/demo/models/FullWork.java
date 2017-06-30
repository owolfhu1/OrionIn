package com.example.demo.models;

import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.WorkRepository;

import java.util.ArrayList;

public class FullWork {
    private TaskRepository taskRepository;
    private WorkRepository workRepository;
    private Work work;
    private ArrayList<Task> tasks = new ArrayList<>();

    public FullWork(WorkRepository workRepository, TaskRepository taskRepository, int workId) {
        this.workRepository = workRepository;
        this.taskRepository = taskRepository;
        work = workRepository.findOne(workId);
        if (taskRepository.existsByWorkId(workId))
            tasks = taskRepository.findAllByWorkId(workId);
    }

    public Work getWork() {
        return work;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
}
