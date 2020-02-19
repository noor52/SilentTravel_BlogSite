package com.fardoushlab.iccweb.controllers;


import com.fardoushlab.iccweb.dtos.StaffDto;
import com.fardoushlab.iccweb.models.Role;
import com.fardoushlab.iccweb.models.User;
import com.fardoushlab.iccweb.request_models.Country;
import com.fardoushlab.iccweb.request_models.Staff;
import com.fardoushlab.iccweb.services.CountryService;
import com.fardoushlab.iccweb.services.StaffService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;

@Controller
public class StaffController {
    @Autowired
    StaffService staffService;

    @Autowired
    CountryService countryService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/staff/add")
    public String getStaffAddPage(Model model){

        var countryList = new ArrayList<Country>();
        countryService.getAllCountry().forEach(country->{
            var countryRm = new Country();
            BeanUtils.copyProperties(country,countryRm);
            countryList.add(countryRm);

        });
        var roleList = new ArrayList<String>();
        roleList.add("TEAM_MANAGER");
        roleList.add("COACHING_STAFF");

        model.addAttribute("staff", new Staff());
        model.addAttribute("role_list",roleList);
        model.addAttribute("country_list",countryList);

        return "staff/add";
    }

    @PostMapping("/staff/add")
    public String addNewStaff(Model model, @ModelAttribute(name = "staff") Staff staff){

        System.out.println(staff.toString());

        var country = countryService.getCountryById(staff.getCountryId());
        User user = new User();
        user.setName(staff.getName());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode("staff"));
        if (staff.getRole().equals("TEAM_MANAGER")){
            user.setRole(Role.ROLE_TEAM_MANAGER);
        }else if (staff.getRole().equals("COACHING_STAFF")){
            user.setRole(Role.ROLE_COACHING_STAFF);
        }

        StaffDto staffDto = new StaffDto();
        staffDto.setAge(staff.getAge());
        staffDto.setDob(staff.getDob());
        staffDto.setActive(true);
        staffDto.setUser(user);
        staffDto.setCountry(country);

        staffService.addStaff(staffDto);

        return "redirect:/staff/show-all";
    }

    @GetMapping("/staff/show-all")
    public String getAllStaffShowPage(Model model){

        var staffList = new ArrayList<Staff>();

        staffService.getAllStaff().forEach(staffDto -> {
            var staff  = new Staff();

            BeanUtils.copyProperties(staffDto,staff);
            staff.setCountryName(staffDto.getCountry().getName());
            staff.setName(staffDto.getUser().getName());
            staffList.add(staff);

        });

        model.addAttribute("staff_list",staffList);
        return "staff/show-all";
    }

       /*

    @GetMapping("")
    public String method(Model model){
        return "";
    }

    */
}
