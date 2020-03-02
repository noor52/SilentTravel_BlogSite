package com.fardoushlab.iccweb.controllers;

import com.fardoushlab.iccweb.dtos.UserDto;
import com.fardoushlab.iccweb.models.Role;
import com.fardoushlab.iccweb.models.User;
import com.fardoushlab.iccweb.services.UserService;
import com.fardoushlab.iccweb.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;

    @Autowired
    ServletContext servletContext;


    private com.fardoushlab.iccweb.request_models.User getCurrentUser(Authentication authentication){
        UserDto userDto = userService.getUserDtoByName(authentication.getName());

        com.fardoushlab.iccweb.request_models.User user = new com.fardoushlab.iccweb.request_models.User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setRole(Util.getStringRole(userDto.getRole()));
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());

        return user;
    }


    @GetMapping("/user/add-admin")
    public String getAdminAddPage(Model model, Authentication authentication){

        model.addAttribute("user",getCurrentUser(authentication));
        return "user/add-admin";

    }


    @PostMapping("/user/add-admin")
    public String addAdmin(Model model, @RequestParam(name = "username") String name){

        User user = new User();
        user.setName(name);
        user.setRole(Role.ROLE_ADMIN);
        user.setActive(true);
        user.setPassword(passwordEncoder.encode("admin123"));
        // default profile picture
        user.setProfilePictureUrl("/images/profile/default_profile.jpg");

        userService.saveUser(user);

        return "redirect:/index";



    }

    @GetMapping("/user/profile")
    public String getProfilePage(Model model, Authentication authentication){


        model.addAttribute("user",getCurrentUser(authentication));


        return "user/profile";
    }

    @PostMapping("/user/uploadFile")
    public String uploadFiles(@RequestParam(name = "file")MultipartFile multipartFile, ModelMap modelMap){


        org.springframework.security.core.userdetails.User authenticateduser  = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto user = userService.getUserDtoByName(authenticateduser.getUsername());
        String pictureName = "pp"+user.getId()+".jpg";
        // Save file on system
       if (!multipartFile.getOriginalFilename().isEmpty()) {


            try {


                File directory = new File( servletContext.getRealPath("/WEB-INF/resources/images/profile/") );


                if (!directory.exists()){
                    boolean bool = directory.mkdirs();
                    if(bool){
                        System.out.println("Directory created successfully");
                    }else{
                        System.out.println("Sorry couldn't create specified directory");
                    }
                }

                System.out.println("dir path: "+directory.getAbsolutePath());

                File outputfile = new File(directory, pictureName);

                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputfile));
                outputStream.write(multipartFile.getBytes());
                outputStream.flush();
                outputStream.close();

                userService.updateuserProfilePicture(user.getName(),"/images/profile/"+pictureName);

                System.out.println("file name: "+outputfile.getName());
                modelMap.addAttribute("fileName",outputfile.getName());
                modelMap.addAttribute("photo_uri",outputfile.getAbsolutePath());
                modelMap.addAttribute("msg", "File uploaded successfully.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                modelMap.addAttribute("msg", "Failed to save file properly.");
            } catch (IOException e) {
                e.printStackTrace();
                modelMap.addAttribute("msg", "Failed to save file properly.");
            }

        } else {
            modelMap.addAttribute("msg", "Please select a valid file..");
        }


       // modelMap.addAttribute("file", multipartFile);

        return "redirect:/index";

    }



}
