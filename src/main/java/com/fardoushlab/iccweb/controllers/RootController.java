package com.fardoushlab.iccweb.controllers;

import com.fardoushlab.iccweb.dtos.PostDtoMinimal;
import com.fardoushlab.iccweb.dtos.UserDto;
import com.fardoushlab.iccweb.dtos.UserDtoMinimal;
import com.fardoushlab.iccweb.dtos.UserSuggDto;
import com.fardoushlab.iccweb.request_models.CommentRM;
import com.fardoushlab.iccweb.services.PostService;
import com.fardoushlab.iccweb.services.UserService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//import java.util.logging.Logger;
//

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RootController {
    private Logger logger = Logger.getLogger(RootController.class);

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PostService postService;


    @GetMapping("/")
    public String getDefaultPage(Model model){

        return "redirect:/index4";

    }

    @GetMapping("/index4")
    public String getHomePage(Model model,
                              @RequestParam(name = "pageId", required = false) Long pageId,
                              @RequestParam(name = "requestType", required = false) String requestType, Authentication authentication){

        if (pageId == null){
            pageId = Long.valueOf(1);
        }else {
            if (requestType.equals("prev")){
                pageId--;
            }else if (requestType.equals("next")){
                pageId++;
            }

            if (pageId < 1){
                pageId  = Long.valueOf(1);
            }
        }

        logger.info("pageId: "+pageId);

        UserDto userDto = userService.getUserDtoByName(authentication.getName());
        UserDtoMinimal userDtoMinimal = new UserDtoMinimal();
        BeanUtils.copyProperties(userDto,userDtoMinimal);

        List<PostDtoMinimal> pagedMinimalposts = postService.getPagedMinimalposts(userDto.getId(), pageId);

//        List<UserSuggDto> userDtoList = userService.getNonFriendUserList(userDto.getId(),20);

        model.addAttribute("user",userDtoMinimal);
        model.addAttribute("post_list",pagedMinimalposts);
//        model.addAttribute("user_list",userDtoList);
        model.addAttribute("comment",new CommentRM());
        model.addAttribute("pageId",pageId);

        return "index4";

    }
}
