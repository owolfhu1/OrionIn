package com.example.demo.models;

import com.example.demo.repositories.EduRepository;
import com.example.demo.repositories.SkillRepository;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.WorkRepository;
import java.util.ArrayList;

public class Resume {
    private EduRepository eduRepository;
    private WorkRepository workRepository;
    private SkillRepository skillRepository;
    private TaskRepository taskRepository;

    private String userName;
    private ArrayList<Edu> edu = new ArrayList<>();
    private ArrayList<FullWork> work = new ArrayList<>();
    private ArrayList<Skill> skill = new ArrayList<>();

    public Resume(String userName,EduRepository eduRepository, WorkRepository workRepository,
                  SkillRepository skillRepository, TaskRepository taskRepository) {
        this.eduRepository = eduRepository;
        this.workRepository = workRepository;
        this.taskRepository = taskRepository;
        this.skillRepository = skillRepository;

        this.userName = userName;
        if (eduRepository.existsByUserName(userName))
            edu = eduRepository.findAllByUserName(userName);
        ArrayList<Work> wList = new ArrayList<>();
        if (workRepository.existsByUserName(userName))
            wList = workRepository.findAllByUserName(userName);
        work = new ArrayList<>();
        for (Work w : wList)
            work.add(new FullWork(workRepository, taskRepository, w.getId()));
        if (skillRepository.existsByUserName(userName))
            skill = skillRepository.findAllByUserName(userName);
    }

    public EduRepository getEduRepository() {
        return eduRepository;
    }

    public WorkRepository getWorkRepository() {
        return workRepository;
    }

    public SkillRepository getSkillRepository() {
        return skillRepository;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Edu> getEdu() {
        return edu;
    }

    public ArrayList<FullWork> getWork() {
        return work;
    }

    public ArrayList<Skill> getSkill() {
        return skill;
    }
}
