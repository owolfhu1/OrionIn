package com.example.demo.controllers;

import com.example.demo.models.Edu;
import com.example.demo.models.Skill;
import com.example.demo.models.Task;
import com.example.demo.models.Work;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

@Controller
public class UserController {

    private UserRepository userRepository;
    private EduRepository eduRepository;
    private WorkRepository workRepository;
    private TaskRepository taskRepository;
    private SkillRepository skillRepository;

    @Autowired
    public UserController (UserRepository userRepository, EduRepository eduRepository,
             WorkRepository workRepository, TaskRepository taskRepository, SkillRepository skillRepository) {
        this.userRepository = userRepository;
        this.eduRepository = eduRepository;
        this.workRepository = workRepository;
        this.taskRepository = taskRepository;
        this.skillRepository = skillRepository;
    }

    @RequestMapping("/")
    public String index() {
        return "home";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/editedu")
    public String editEdu(Model model) {
        model.addAttribute("edu", new Edu());
        return "editedu";
    }

    @RequestMapping("/editwork")
    public String editWork(Model model) {
        model.addAttribute("work", new Work());
        return "editwork";
    }

    @RequestMapping("/editskill")
    public String editSkill(Model model) {
        model.addAttribute("skill", new Skill());
        return "editskill";
    }

    @RequestMapping("/addEdu")
    public String addEdu(@ModelAttribute Edu edu, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        edu.setUserName(username);
        eduRepository.save(edu);
        return "home";
    }

    @RequestMapping("/addWork")
    public String addWork(@ModelAttribute Work work, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int tempId = ThreadLocalRandom.current().nextInt(20, 99);
        String username = userDetails.getUsername();
        String[] tasks = work.getUserName().split(",");
        work.setUserName(username);
        work.setTempId(tempId);
        workRepository.save(work);
        int workId = workRepository.findByTempId(tempId).getId();
        work.setTempId(10);
        workRepository.save(work);
        for (String task : tasks) {
            Task newTask = new Task();
            newTask.setThisTask(task);
            newTask.setWorkId(workId);
            taskRepository.save(newTask);
        }
        return "home";
    }

    @RequestMapping("/addSkill")
    public String addSkill(@ModelAttribute Skill skill, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        skill.setUserName(username);
        skillRepository.save(skill);
        return "home";
    }




    //prints to spring console for testing
    private void console(String format, Object... objz) {
        format = "\n" + format + "\n";
        System.out.printf(format, objz);
    }
}

/*

    console("userDetails: \n    -username: %s \n    -role: %s",
            userDetails.getUsername(), userDetails.getAuthorities());
        console("form values: \n    -company: %s \n    -title: %s \n    -tasks: %s",
        work.getCompany(), work.getTitle(), work.getUserName());*/
