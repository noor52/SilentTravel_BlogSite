package com.noor.blog.controllers;

import com.noor.blog.dtos.CountryDto;
import com.noor.blog.dtos.UserDto;
import com.noor.blog.request_models.Country;
import com.noor.blog.request_models.User;
import com.noor.blog.services.*;
import com.noor.blog.util.Util;
import com.noor.blog.services.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class CountryController {

    @Autowired
    CountryService countryService;

    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @Autowired
    StaffService staffService;

    @Autowired
    UserService userService;


    private User getCurrentUser(Authentication authentication){
        UserDto userDto = userService.getUserDtoByName(authentication.getName());

        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setRole(Util.getStringRole(userDto.getRole()));
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());

        return user;
    }

    @GetMapping("/country/add")
    public String getAddCountryPage(Model model,Authentication authentication){

        model.addAttribute("user",getCurrentUser(authentication));
        return "country/add";
    }

    @PostMapping("/country/add")
    public String addNewCountry(Model model, @RequestParam(name = "country_name") String countryName){

        countryService.addCountry(countryName);
        model.addAttribute("message","Country Saved Successfully");
        return "redirect:/country/show-all";
    }

    @GetMapping("/country/show-all")
    public String getShowAllCountryPage(Model model, Authentication authentication){

        var countryList = new ArrayList<Country>();
        countryService.getAllCountry().forEach(country->{
            var countryRm = new Country();
            BeanUtils.copyProperties(country,countryRm);
            countryList.add(countryRm);

        });
        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("countryList",countryList);
        return "country/show-all";
    }


    @GetMapping("/country/edit")
    public String editCountry(Model model, @RequestParam(name = "id") long countryId, Authentication authentication){

        CountryDto countryDto = countryService.getCountryDtoById(countryId);
        Country country = new Country();
        BeanUtils.copyProperties(countryDto,country);

        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("country",country);

        return "country/edit";
    }

    @PostMapping("/country/edit")
    public String saveCountry(Model model, @ModelAttribute(name = "country") Country country, @RequestParam(name = "isActive") boolean active){
        country.setActive(active);
        System.out.println(country.toString());
        CountryDto dto = new CountryDto();
        BeanUtils.copyProperties(country,dto);
        dto.setActive(active);

        countryService.saveEditedCourse(dto);

        return "redirect:/country/show-all";
    }

    @GetMapping("/country/delete")
    public String deleteCountry(Model model, @RequestParam(name = "id") long countryId){

        // MUST NEED TO DO THIS IN ONE TRANSACTION
        countryService.changeCountryActiveStatus(countryId,false);
        teamService.changeCountryTeamActiveStatus(countryId,false);
        playerService.changeCountryPlayerActiveStatus(countryId,false);
        staffService.changeCountryCoachingStaffActiveStatus(countryId,false);



        return "redirect:/country/show-all";
    }


}
