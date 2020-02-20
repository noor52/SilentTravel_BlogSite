package com.fardoushlab.iccweb.controllers;

import com.fardoushlab.iccweb.dtos.CountryDto;
import com.fardoushlab.iccweb.request_models.Country;
import com.fardoushlab.iccweb.services.CountryService;
import com.fardoushlab.iccweb.services.PlayerService;
import com.fardoushlab.iccweb.services.StaffService;
import com.fardoushlab.iccweb.services.TeamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/country/add")
    public String getAddCountryPage(){
        return "country/add";
    }

    @PostMapping("/country/add")
    public String addNewCountry(Model model, @RequestParam(name = "country_name") String countryName){

        countryService.addCountry(countryName);
        model.addAttribute("message","Country Saved Successfully");
        return "redirect:/country/show-all";
    }

    @GetMapping("/country/show-all")
    public String getShowAllCountryPage(Model model){

        var countryList = new ArrayList<Country>();
        countryService.getAllCountry().forEach(country->{
            var countryRm = new Country();
            BeanUtils.copyProperties(country,countryRm);
            countryList.add(countryRm);

        });
        model.addAttribute("countryList",countryList);
        return "country/show-all";
    }


    @GetMapping("/country/edit")
    public String editCountry(Model model, @RequestParam(name = "id") long countryId){

        CountryDto countryDto = countryService.getCountryDtoById(countryId);
        Country country = new Country();
        BeanUtils.copyProperties(countryDto,country);

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
