package com.example.demo.controllers;

import com.example.demo.models.Edu;
import com.example.demo.models.Task;
import com.example.demo.models.Work;
import com.example.demo.repositories.EduRepository;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
public class UserController {
    private int workId;

    UserRepository userRepository;
    EduRepository eduRepository;
    WorkRepository workRepository;
    TaskRepository taskRepository;

    @Autowired
    public UserController (UserRepository userRepository, EduRepository eduRepository, WorkRepository workRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.eduRepository = eduRepository;
        this.workRepository = workRepository;
        this.taskRepository = taskRepository;
        ArrayList<Work> works = (ArrayList<Work>) workRepository.findAll();
        workId = works.size();
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
        String username = userDetails.getUsername();
        String[] tasks = work.getUserName().split(",");
        work.setUserName(username);
        work.setId(workId);
        workRepository.save(work);
        for (String task : tasks) {
            Task newTask = new Task();
            newTask.setThisTask(task);
            newTask.setWorkId(workId);
            taskRepository.save(newTask);
        }
        workId++;
        return "home";
    }

}
