package com.example.demo.controllers;

import com.example.demo.models.Edu;
import com.example.demo.repositories.EduRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;
    EduRepository eduRepository;

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

    @RequestMapping("/addEdu")
    public String addEdu(Edu edu, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();



        eduRepository.save(edu);
        return "home";
    }
}
