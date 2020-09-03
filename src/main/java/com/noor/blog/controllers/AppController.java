package com.noor.blog.controllers;

import com.noor.blog.dtos.PlayerDto;
import com.noor.blog.dtos.UserDto;
import com.noor.blog.models.Role;
import com.noor.blog.models.User;
import com.noor.blog.request_models.Country;
import com.noor.blog.request_models.Player;
import com.noor.blog.request_models.Stat;
import com.noor.blog.services.*;
import com.noor.blog.util.Util;
import com.noor.blog.services.*;
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

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class AppController {

    @Autowired
    UserService userService;

    @Autowired
    CountryService countryService;

    @Autowired
    TeamService teamService;

    @Autowired
    PlayerService playerService;

    @Autowired
    StaffService staffService;

    @Autowired
    PasswordEncoder passwordEncoder;


//    @GetMapping("/")
//    public String getHomePage(Model model){
//
//        return "redirect:/index";
//    }


    private com.noor.blog.request_models.User getCurrentUser(Authentication authentication){
        UserDto userDto = userService.getUserDtoByName(authentication.getName());

        com.noor.blog.request_models.User user = new com.noor.blog.request_models.User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setRole(Util.getStringRole(userDto.getRole()));
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());

        return user;
    }

    private Stat getStats(){
        Stat stat = new Stat();
        stat.setTotalCountry(countryService.countTotalcountry());
        stat.setActiveCountry(countryService.countActivecountry());
        stat.setInActiveCountry(stat.getTotalCountry() - stat.getActiveCountry());
        stat.setTotalTeam(teamService.countTotalTeam());
        stat.setActiveTeam(teamService.countActiveTeam());
        stat.setInactiveTeam(stat.getTotalTeam() - stat.getActiveTeam());
        stat.setTotalCoach(staffService.countTotalStaff() );
        stat.setActiveCoach(staffService.countActiveStaff());
        stat.setInActiveCoach(stat.getTotalCoach() - stat.getActiveCoach());
        stat.setTotalPlayer(playerService.countTotalPlayer());
        stat.setActivePlayer(playerService.countActivePlayer());
        stat.setInActivePlayer(stat.getTotalPlayer() - stat.getActivePlayer());
        stat.setTotalUser(userService.countTotalUser());
        stat.setActiveUser(userService.countActiveUser());
        stat.setInActiveUser(stat.getTotalUser() - stat.getActiveUser());

        System.out.println("stats: "+stat.toString());
        return stat;
    }

//    @GetMapping("/index")
//    public String getIndexPage(Model model, Authentication auth){
//
//        var countryList = new ArrayList<Country>();
//        countryService.getAllCountry().forEach(country->{
//            var countryRm = new Country();
//            BeanUtils.copyProperties(country,countryRm);
//            countryList.add(countryRm);
//
//        });
//
//        model.addAttribute("stat", getStats());
//        model.addAttribute("user",getCurrentUser(auth));
//        model.addAttribute("country", new Country());
//        model.addAttribute("countryList",countryList);
//
//        return "index";
//    }

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
            superUser.setProfilePictureUrl("/profile/images/default_profile.jpg");
            userService.saveUser(superUser);
        }

    }


//    @GetMapping("/auth/register")
//    public String getRegisterPage(Model model, @RequestParam(name="error",required = false) Boolean error){
//
//        List<String> roles = new ArrayList<>(Arrays.asList("USER","PLAYER"));
//        model.addAttribute("error",error);
//        model.addAttribute("user", new com.fardoushlab.iccweb.request_models.User());
//        model.addAttribute("role_list",roles);
//
//
//        return "auth/register";
//    }
    @GetMapping("/auth/register")
    public String getPlayerAddPage(Model model){

        var countryList = new ArrayList<Country>();
        countryService.getAllCountry().forEach(country->{
            var countryRm = new Country();
            BeanUtils.copyProperties(country,countryRm);
            countryList.add(countryRm);

        });


        model.addAttribute("player", new Player());
        model.addAttribute("country_list",countryList);

        return "/auth/register";
    }


    @PostMapping("/auth/register")
    public String addNewPlayer(Model model, @ModelAttribute(name = "player") Player player){

        System.out.println(player.toString());

        var country = countryService.getCountryById(player.getCountryId());
        User user = new User();
        user.setName(player.getName());
        user.setRole(Role.ROLE_PLAYER);
        user.setActive(false);
        user.setPassword(passwordEncoder.encode("player"));

        PlayerDto playerDto = new PlayerDto();
        playerDto.setAge(player.getAge());

        LocalDate dob = Util.getFormattedDate(player.getDob(),Util.DOB_DATE_FORMAT);
        playerDto.setDob(dob);
        playerDto.setActive(true);
        playerDto.setUser(user);
        playerDto.setCountry(country);

        playerService.addPlayer(playerDto);

        return "redirect:/auth/login";

    }


    @GetMapping("/403")
    public String _403(){
        return "403";
    }


    @GetMapping("/dashbord")
    public String dashbord(Model model, Authentication auth){

        var countryList = new ArrayList<Country>();
        countryService.getAllCountry().forEach(country->{
            var countryRm = new Country();
            BeanUtils.copyProperties(country,countryRm);
            countryList.add(countryRm);

        });

        model.addAttribute("stat", getStats());
        model.addAttribute("user",getCurrentUser(auth));
        model.addAttribute("country", new Country());
        model.addAttribute("countryList",countryList);

        return "dashbord";
    }
}
