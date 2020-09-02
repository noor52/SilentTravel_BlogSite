package com.fardoushlab.iccweb.controllers;

import com.fardoushlab.iccweb.dtos.PlayerDto;
import com.fardoushlab.iccweb.dtos.UserDto;
import com.fardoushlab.iccweb.models.Role;
import com.fardoushlab.iccweb.models.User;
import com.fardoushlab.iccweb.request_models.Country;
import com.fardoushlab.iccweb.request_models.Player;
import com.fardoushlab.iccweb.services.CountryService;
import com.fardoushlab.iccweb.services.PlayerService;
import com.fardoushlab.iccweb.services.UserService;
import com.fardoushlab.iccweb.util.Util;
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
import java.util.Date;

@Controller
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @Autowired
    CountryService countryService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserService userService;


    private com.fardoushlab.iccweb.request_models.User getCurrentUser(Authentication authentication){
        UserDto userDto = userService.getUserDtoByName(authentication.getName());

        com.fardoushlab.iccweb.request_models.User user = new com.fardoushlab.iccweb.request_models.User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setRole(Util.getStringRole(userDto.getRole()));
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());

        return user;
    }

    @GetMapping("/player/add")
    public String getPlayerAddPage(Model model, Authentication authentication){

        var countryList = new ArrayList<Country>();
        countryService.getAllCountry().forEach(country->{
            var countryRm = new Country();
            BeanUtils.copyProperties(country,countryRm);
            countryList.add(countryRm);

        });

        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("player", new Player());
        model.addAttribute("country_list",countryList);

        return "player/add";
    }


    @PostMapping("/player/add")
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
        playerDto.setActive(false);
        playerDto.setUser(user);
        playerDto.setCountry(country);

        playerService.addPlayer(playerDto);

        return "redirect:/player/show-all";
    }

    @GetMapping("/player/show-all")
    public String showAllPlayer(Model model, Authentication authentication){
        var playerList = new ArrayList<Player>();
        var deactivePlayerList = new ArrayList<Player>();

        playerService.getAllPlayer().forEach(playerDto -> {
            var player  = new Player();

            BeanUtils.copyProperties(playerDto,player);
            player.setCountryName(playerDto.getCountry().getName());
            player.setName(playerDto.getUser().getName());
            player.setDob(Util.getStringDate(playerDto.getDob(),Util.DOB_DATE_FORMAT));
            playerList.add(player);

        });

        playerService.getAllDeactivePlayer().forEach(playerDto -> {
            var player  = new Player();

            BeanUtils.copyProperties(playerDto,player);
            player.setCountryName(playerDto.getCountry().getName());
            player.setName(playerDto.getUser().getName());
            player.setDob(Util.getStringDate(playerDto.getDob(),Util.DOB_DATE_FORMAT));
            deactivePlayerList.add(player);

        });



        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("player_list",playerList);
        model.addAttribute("deactive_player_list",deactivePlayerList);
        return "player/show-all";
    }

    @GetMapping("/player/edit")
    public String getPlayerEditPage(Model model, @RequestParam(name = "id") long id, Authentication authentication){
        var playerDto = playerService.getPlayerById(id);
        var player = new Player();
        BeanUtils.copyProperties(playerDto,player);
        player.setCountryName(playerDto.getCountry().getName());
        player.setCountryId(playerDto.getCountry().getId());
        player.setName(playerDto.getUser().getName());
        player.setDob(Util.getStringDate(playerDto.getDob(),Util.DOB_DATE_FORMAT));
        player.setActive(true);

        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("player",player);
        return "player/edit";
    }

    @PostMapping("/player/edit")
    public String saveEditedPlayer(Model model, @ModelAttribute(name = "player") Player player){

        PlayerDto playerDto = playerService.getPlayerById(player.getId());

        playerDto.setDob(Util.getFormattedDate(player.getDob(),Util.DOB_DATE_FORMAT));
        playerDto.setAge(player.getAge());
        playerDto.setActive(true);

        playerService.saveEditedPlayer(playerDto);
        return "redirect:/player/show-all";
    }


    @GetMapping("player/delete")
    public String deletePlayByid(Model model, @RequestParam(name = "id") long id){

        playerService.changePlayerActiveStatus(id,false);
      //  playerService.deactivePlayerInTeam(id);

        return "redirect:/player/show-all";
    }

       /*

    @GetMapping("")
    public String showAllPlayer(Model model){
        return "";
    }

    */

}
