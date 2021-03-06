package com.example.demo.repositories;

import com.example.demo.models.Edu;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface EduRepository extends CrudRepository<Edu, Integer> {
    ArrayList<Edu> findAllByUserName(String userName);

    boolean existsByUserName(String userName);

    ArrayList<Edu> findAllBySchool(String search);
}
