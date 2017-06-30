package com.example.demo.repositories;

import com.example.demo.models.Work;
import org.springframework.data.repository.CrudRepository;

public interface WorkRepository extends CrudRepository<Work, Integer>{
    Work findByTempId(int tempId);
}
