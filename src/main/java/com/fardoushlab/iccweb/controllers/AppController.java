package com.fardoushlab.iccweb.controllers;

import com.fardoushlab.iccweb.dtos.UserDto;
import com.fardoushlab.iccweb.models.Role;
import com.fardoushlab.iccweb.models.User;
import com.fardoushlab.iccweb.request_models.Country;
import com.fardoushlab.iccweb.services.CountryService;
import com.fardoushlab.iccweb.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.RollbackException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    CountryService countryService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String getHomePage(Model model){

        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getIndexPage(Model model){

        var countryList = new ArrayList<Country>();
        countryService.getAllCountry().forEach(country->{
            var countryRm = new Country();
            BeanUtils.copyProperties(country,countryRm);
            countryList.add(countryRm);

        });
        model.addAttribute("country", new Country());
        model.addAttribute("countryList",countryList);

        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(name="error",required = false) Boolean error){

        genereateUser();

        model.addAttribute("error",error);

        return "auth/login";
    }

    public void genereateUser(){

        if (!userService.isUserExists("Admin")){
            User superUser = new User();
            superUser.setName("Admin");
            superUser.setPassword(passwordEncoder.encode("asecret"));
            superUser.setActive(true);
            superUser.setRole(Role.ROLE_SUPER_ADMIN);
            userService.saveUser(superUser);
        }

    }


    @GetMapping("/auth/register")
    public String getRegisterPage(Model model, @RequestParam(name="error",required = false) Boolean error){

        List<String> roles = new ArrayList<>(Arrays.asList("USER","PLAYER"));
        model.addAttribute("error",error);
        model.addAttribute("user", new com.fardoushlab.iccweb.request_models.User());
        model.addAttribute("role_list",roles);


        return "auth/register";
    }

    @PostMapping("/auth/register")
    public String registerUser(Model model, @ModelAttribute(name = "user")com.fardoushlab.iccweb.request_models.User user){

        var userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setPassword(passwordEncoder.encode(user.getPassword()));
        return "";
    }

    @GetMapping("/403")
    public String _403(){
        return "403";
    }
}

















