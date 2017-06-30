package com.example.demo.repositories;

import com.example.demo.models.Work;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface WorkRepository extends CrudRepository<Work, Integer>{
    Work findByTempId(int tempId);
    ArrayList<Work> findAllByUserName(String userName);

    boolean existsByUserName(String userName);
}
