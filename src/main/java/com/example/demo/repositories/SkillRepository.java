package com.example.demo.repositories;

import com.example.demo.models.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface SkillRepository extends CrudRepository<Skill, Integer> {
    ArrayList<Skill> findAllByUserName(String userName);

    boolean existsByUserName(String userName);
}
