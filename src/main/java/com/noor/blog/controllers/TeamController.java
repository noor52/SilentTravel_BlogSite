package com.noor.blog.controllers;

import com.noor.blog.dtos.UserDto;
import com.noor.blog.models.Country;
import com.noor.blog.models.Team;
import com.noor.blog.models.TeamStaff;
import com.noor.blog.request_models.*;
import com.noor.blog.models.Player;
import com.noor.blog.request_models.Staff;
import com.noor.blog.response_model.LongIdListObject;
import com.noor.blog.dtos.CountryDto;
import com.noor.blog.dtos.TeamDto;
import com.noor.blog.models.TeamPlayer;
import com.noor.blog.services.*;
import com.noor.blog.services.*;
import com.noor.blog.util.Util;
import com.noor.blog.models.*;
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
import java.util.List;

@Controller
public class TeamController {

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


    private com.noor.blog.request_models.User getCurrentUser(Authentication authentication){
        UserDto userDto = userService.getUserDtoByName(authentication.getName());

        com.noor.blog.request_models.User user = new com.noor.blog.request_models.User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setRole(Util.getStringRole(userDto.getRole()));
        user.setProfilePictureUrl(userDto.getProfilePictureUrl());

        return user;
    }


    @GetMapping("/team/add")
    public String getTeamAddPage(Model model, Authentication authentication){

        var countryList = new ArrayList<com.noor.blog.request_models.Country>();
        countryService.getAllCountry().forEach(country->{
            var countryRm = new com.noor.blog.request_models.Country();
            BeanUtils.copyProperties(country,countryRm);
            countryList.add(countryRm);

        });

        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("team", new com.noor.blog.request_models.Team());
        model.addAttribute("country_list",countryList);

        return "team/add";
    }

    @PostMapping("/team/add")
    public String addNewteam(Model model, @ModelAttribute(name = "team") com.noor.blog.request_models.Team team){
        System.out.println(team.toString());

        CountryDto countryDto = countryService.getCountryDtoById(team.getCountryId());

        Country country = new Country();
        BeanUtils.copyProperties(countryDto,country);

        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        teamDto.setCountry(country);

        teamService.addNewTeam(teamDto);


        return "redirect:/team/show-all";
    }

    @GetMapping("/team/show-all")
    public String getTeamShowAllPage(Model model, Authentication authentication){

        var teamList = new ArrayList<com.noor.blog.request_models.Team>();
       teamService.getAllTeam().forEach(teamDto -> {
           var team = new com.noor.blog.request_models.Team();
           BeanUtils.copyProperties(teamDto,team);
           team.setCountryName(teamDto.getCountry().getName());
           teamList.add(team);
       });

        model.addAttribute("user",getCurrentUser(authentication));
       model.addAttribute("team_list",teamList);

        return "team/show-all";
    }

    @GetMapping("/team/add-team-player")
    public String getTeamPlayerAddPage(Model model, @RequestParam(name = "team_id") long team_id, Authentication authentication){

        var teamDto = teamService.getTeamDtoById(team_id);
        var team = new com.noor.blog.request_models.Team();

        BeanUtils.copyProperties(teamDto,team);

        List<com.noor.blog.request_models.Player> players = new ArrayList<>();
         playerService.getNonAssignedCountryPlayer(teamDto.getCountry().getId())
                 .forEach(playerDto -> {
                     var player = new com.noor.blog.request_models.Player();
                     player.setId(playerDto.getId());
                     player.setName(playerDto.getUser().getName());
                     players.add(player);
                 });

        model.addAttribute("user",getCurrentUser(authentication));
         model.addAttribute("team",team);
         model.addAttribute("players",players);
         model.addAttribute("idList",new LongIdListObject());

        return "team/add-team-player";
    }

    @PostMapping("/team/add-team-player")
    public String assignPlayerToTeam(Model model, @ModelAttribute(name = "idList") LongIdListObject longIdListObject , @RequestParam(name = "team_id") long team_id ){
       System.out.println( longIdListObject.toString());

        Team team = new Team();
        team.setId(team_id);

        /*
        * Violation of DTO
        * */

        List<TeamPlayer> teamPlayers = new ArrayList<>();

        longIdListObject.getIds().forEach(id->{
            var player = new Player();
            player.setId(id);
            TeamPlayer teamPlayer = new TeamPlayer();
            teamPlayer.setPlayer(player);
            teamPlayer.setTeam(team);
            teamPlayers.add(teamPlayer);

        });

        teamService.addPlayers(teamPlayers);

        return "redirect:/team/show-all";
    }

    @GetMapping("/team/add-team-staff")
    public String getAddTeamStaffPage(Model model, @RequestParam(name = "team_id") long team_id, Authentication authentication){

        var teamDto = teamService.getTeamDtoById(team_id);
        var team = new com.noor.blog.request_models.Team();

        BeanUtils.copyProperties(teamDto,team);

        List<Staff> staffs = new ArrayList<>();
        staffService.getNonAssignedCountryStaff(teamDto.getCountry().getId())
                .forEach(staffDto -> {
                    var Staff = new Staff();
                    Staff.setId(staffDto.getId());
                    Staff.setName(staffDto.getUser().getName());
                    staffs.add(Staff);
                });

        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("team",team);
        model.addAttribute("staffs",staffs);
        model.addAttribute("idList",new LongIdListObject());

        return "team/add-team-staff";
    }

    @PostMapping("/team/add-team-staff")
    public String assignStaffToTeam(Model model, @ModelAttribute(name = "idList") LongIdListObject longIdListObject , @RequestParam(name = "team_id") long team_id ){
        System.out.println( longIdListObject.toString());

        Team team = new Team();
        team.setId(team_id);

        /*
         * Violation of DTO
         * */

        List<TeamStaff> teamStaffs = new ArrayList<>();

        longIdListObject.getIds().forEach(id->{
            var staff = new CoachingStaff();
            staff.setId(id);
            TeamStaff teamStaff = new TeamStaff();
            teamStaff.setStaff(staff);
            teamStaff.setTeam(team);
            teamStaffs.add(teamStaff);

        });

        teamService.addStaffs(teamStaffs);

        return "redirect:/team/show-all";
    }

    @PostMapping("/team/team-members")
    public String showAllTeamMember(Model model, @RequestParam(name = "id") long countryId, Authentication authentication){

    //    var countryDto = countryService.getCountryDtoById(countryId);
     //   var country = new Country();
     //   BeanUtils.copyProperties(countryDto,country);
        var team = new com.noor.blog.request_models.Team();
        TeamDto teamDto = teamService.getTeamByCountry(countryId);
        BeanUtils.copyProperties(teamDto,team);
        team.setCountryId(teamDto.getCountry().getId());
        team.setCountryName(teamDto.getCountry().getName());


        var playerList = new ArrayList<com.noor.blog.request_models.Player>();
        playerService.getTeamPlayers(team.getId()).forEach(dto->{
            var player = new com.noor.blog.request_models.Player();
            player.setId(dto.getId());
            player.setName(dto.getUser().getName());
            player.setAge(dto.getAge());

            player.setDob(Util.getStringDate(dto.getDob(),Util.DOB_DATE_FORMAT));
            player.setCountryName(dto.getCountry().getName());
            playerList.add(player);
        });

        var staffList = new ArrayList<Staff>();
        staffService.getTeamStaffs(team.getId()).forEach(dto->{
            var staff = new Staff();
           staff.setId(dto.getId());
           staff.setName(dto.getUser().getName());
           staff.setAge(dto.getAge());
           staff.setDob(Util.getStringDate(dto.getDob(),Util.DOB_DATE_FORMAT));
           staff.setCountryName(dto.getCountry().getName());
            staffList.add(staff);
        });

        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("team",team);
        model.addAttribute("players",playerList);
        model.addAttribute("staffs",staffList);

        return "team/team-members";
    }

    @GetMapping("/team/edit")
    public String getTeamEditPage(Model model, @RequestParam("team_id") long teamId, Authentication authentication){

        TeamDto dto = teamService.getTeamDtoById(teamId);
        com.noor.blog.request_models.Team team = new com.noor.blog.request_models.Team();
        BeanUtils.copyProperties(dto,team);
        team.setCountryId(dto.getCountry().getId());
        team.setCountryName(dto.getCountry().getName());

        model.addAttribute("user",getCurrentUser(authentication));
        model.addAttribute("team",team);
        return "team/edit";
    }

    @PostMapping("/team/edit")
    public String saveEditedTeam(Model model, @ModelAttribute(name="team") com.noor.blog.request_models.Team team){

        TeamDto dto = teamService.getTeamDtoById(team.getId());
        dto.setName(team.getName());
        teamService.saveEditedTeam(dto);

        return "redirect:/team/show-all";
    }

    @GetMapping("/team/delete")
    public String deleteTeamById(Model model, @RequestParam(name = "team_id") long teamId){
        // MUST NEED TO DO IN ONE TRANSACTION
        teamService.changeTeamActiveStatus(teamId,false);
      //  teamService.deactiveTeamPlayers(teamId);
      //  teamService.deactiveTeamStaff(teamId);
        return "redirect:/team/show-all";
    }



    /*

    @GetMapping("")
    public String CHANGE_METHOD_NAME_(Model model){
        return "";
    }

    */


}
