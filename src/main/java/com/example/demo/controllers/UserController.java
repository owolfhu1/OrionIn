package com.example.demo.controllers;

import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
    public String index(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        if (userDetails.getAuthorities().toString().equals("[seeker]")) {
            model.addAttribute("resume", new Resume(username, eduRepository, workRepository, skillRepository, taskRepository));
        } else {
            model.addAttribute("user", new User());
        }
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
        return "redirect:/";
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
        return "redirect:/";
    }

    @RequestMapping("/addSkill")
    public String addSkill(@ModelAttribute Skill skill, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        skill.setUserName(username);
        skillRepository.save(skill);
        return "redirect:/";
    }

    @RequestMapping("/search")
    public String search(@ModelAttribute User user, Model model) {
        String search = user.getUsername();
        ArrayList<Resume> resumes = new ArrayList<>();
        ArrayList<String> matches = new ArrayList<>();

        for (Edu e : eduRepository.findAllBySchool(search))
            matches.add(e.getUserName());
        for (Work w : workRepository.findAllByCompany(search))
            matches.add(w.getUserName());
        for (Skill s : skillRepository.findAllByArea(search))
            matches.add(s.getUserName());

        console(matches.toString());

        for (String userName : matches) {
            resumes.add(new Resume(userName, eduRepository, workRepository, skillRepository, taskRepository));
        }

        model.addAttribute("resumes", resumes);
        model.addAttribute("user", new User());
        return "results";
    }


    //prints to spring console for testing
    private void console(String format, Object... objz) {
        format = "\n" + format + "\n";
        System.out.printf(format, objz);
    }
}
