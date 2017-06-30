package com.example.demo.repositories;

import com.example.demo.models.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    ArrayList<Task> findAllByWorkId(int workId);

    boolean existsByWorkId(int workId);
}
