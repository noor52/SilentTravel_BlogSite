package com.noor.blog.controllers;

import com.noor.blog.dtos.PlayerDto;
import com.noor.blog.dtos.PostDto;
import com.noor.blog.dtos.UserDto;
import com.noor.blog.models.Post;
import com.noor.blog.models.Role;
import com.noor.blog.models.User;
import com.noor.blog.request_models.Country;
import com.noor.blog.request_models.Player;
import com.noor.blog.request_models.PostRM;
import com.noor.blog.services.CountryService;
import com.noor.blog.services.PlayerService;
import com.noor.blog.services.PostService;
import com.noor.blog.services.UserService;
import com.noor.blog.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.noor.blog.dtos.UserDto;
//import com.noor.blog.models.Post;
//import com.noor.blog.models.User;
//import com.noor.blog.models.Role;
//import com.noor.blog.services.CountryService;
//import com.noor.blog.services.PlayerService;
//import com.noor.blog.services.PostService;
//import com.noor.blog.services.UserService;
//import com.noor.blog.util.Util;
//import org.apache.log4j.Logger;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


@Controller
public class AdminHomeController {
 private Logger logger = Logger.getLogger(AdminHomeController.class);

    @Autowired
    PlayerService playerService;

    @Autowired
    CountryService countryService;



    @Autowired
    UserService userService;

    @Autowired
    PostService postService;


    private  com.noor.blog.request_models.User getCurrentUser(Authentication authentication){
        var userDto = userService.getUserDtoByName(authentication.getName());

        com.noor.blog.request_models.User user = new   com.noor.blog.request_models.User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setRole(Util.getStringRole(userDto.getRole()));
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());

        return user;
    }


    @GetMapping("/post/show-all")
    public String showAllPost(Model model, Authentication authentication){
        var postList = new ArrayList<Post>();
        var deactivePostList = new ArrayList<Post>();

        postService.getAllActivwPost().forEach(postDto -> {
            var post  = new Post();
            BeanUtils.copyProperties(postDto,post);
            post.setPostText(postDto.getPostText());

            postList.add(post);
        });

        postService.getAllDeactivePost().forEach(deactivePostDto -> {
            var post  = new Post();
            BeanUtils.copyProperties(deactivePostDto,post);
            post.setPostText(deactivePostDto.getPostText());
            deactivePostList.add(post);

        });
        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("post_list",postList);
        model.addAttribute("deactive_post_list",deactivePostList);
        return "post/show-all";
    }

    @GetMapping("/post/edit")
    public String getPostEditPage(Model model, @RequestParam(name = "id") long id, Authentication authentication){
        var postDto = postService.getPostById(id);
        var post = new Post();
        BeanUtils.copyProperties(postDto,post);
//        post.setPostText(postDto.get)

        post.setActive(true);

        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("post",post);
        return "post/edit";
    }

    @PostMapping("/post/edit")
    public String saveEditedPlayer(Model model, @ModelAttribute(name = "post") Post post){

        Post postDao = postService.getPostById(post.getId());
        postDao.setActive(true);

        postService.saveEditedPost(postDao);
        return "redirect:/post/show-all";
    }

    @GetMapping("post/delete")
    public String deletePostByid(Model model, @RequestParam(name = "id") long id){

        postService.changePostActiveStatus(id,false);
        //  playerService.deactivePlayerInTeam(id);

        return "redirect:/post/show-all";
    }
}








