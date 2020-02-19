package com.fardoushlab.iccweb.controllers;

import com.fardoushlab.iccweb.models.Role;
import com.fardoushlab.iccweb.models.User;
import com.fardoushlab.iccweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @GetMapping("/user/add-admin")
    public String getAdminAddPage(Model model){

        return "user/add-admin";

    }


    @PostMapping("/user/add-admin")
    public String addAdmin(Model model, @RequestParam(name = "username") String name){

        User user = new User();
        user.setName(name);
        user.setRole(Role.ROLE_ADMIN);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode("admin123"));
        userService.saveUser(user);

        return "redirect:/index";



    }
}
