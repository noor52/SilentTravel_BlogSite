package com.fardoushlab.iccweb.controllers;


import com.fardoushlab.iccweb.dtos.StaffDto;
import com.fardoushlab.iccweb.models.Role;
import com.fardoushlab.iccweb.models.User;
import com.fardoushlab.iccweb.request_models.Country;
import com.fardoushlab.iccweb.request_models.Staff;
import com.fardoushlab.iccweb.services.CountryService;
import com.fardoushlab.iccweb.services.StaffService;
import com.fardoushlab.iccweb.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

        staffDto.setDob(Util.getFormattedDate(staff.getDob(),Util.DOB_DATE_FORMAT));
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
            staff.setDob(Util.getStringDate(staffDto.getDob(),Util.DOB_DATE_FORMAT));
            staffList.add(staff);

        });

        model.addAttribute("staff_list",staffList);
        return "staff/show-all";
    }


    @GetMapping("/staff/edit")
    public String getStaffEditPage(Model model, @RequestParam(name = "id") long id){
        var staffDto = staffService.getCoachingStaffById(id);
        var staff = new Staff();
        BeanUtils.copyProperties(staffDto,staff);
        staff.setCountryName(staffDto.getCountry().getName());
        staff.setCountryId(staffDto.getCountry().getId());
        staff.setDob(Util.getStringDate(staffDto.getDob(),Util.DOB_DATE_FORMAT));
        staff.setName(staffDto.getUser().getName());


        model.addAttribute("staff",staff);
        return "staff/edit";
    }

    @PostMapping("/staff/edit")
    public String saveEditedStaff(Model model, @ModelAttribute(name = "staff") Staff staff){

        StaffDto staffDto = staffService.getCoachingStaffById(staff.getId());
        staffDto.setDob(Util.getFormattedDate(staff.getDob(),Util.DOB_DATE_FORMAT));
        staffDto.setAge(staff.getAge());

        staffService.saveEditedCoachingStaff(staffDto);
        return "redirect:/staff/show-all";
    }


    @GetMapping("staff/delete")
    public String deleteStaffById(Model model, @RequestParam(name = "id") long id){

        staffService.changeCoachingStaffActiveStatus(id,false);
      //  staffService.deactivateTeamStaff(id);
        return "redirect:/staff/show-all";
    }

    
       /*

        @GetMapping("")
        public String showAllStaff(Model model){
            return "";
        }

    */
}
