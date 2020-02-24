package com.fardoushlab.iccweb.controllers;

import com.fardoushlab.iccweb.request_models.Player;
import com.fardoushlab.iccweb.services.PlayerService;
import com.fardoushlab.iccweb.util.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PlayerRestController {

    @Autowired
    PlayerService playerService;

    @GetMapping("/player/search")
    public ResponseEntity<?> searchPlayerByName(@RequestParam(name = "playername") String playerName){

        var playerList = new ArrayList<Player>();

        System.out.println("Player Name: "+playerName);

         playerService.searchPlayerByName(playerName).forEach(playerDto -> {
             var player = new Player();

             player.setId(playerDto.getId());
             player.setName(playerDto.getUser().getName());
             player.setCountryId(playerDto.getCountry().getId());
             player.setCountryName(playerDto.getCountry().getName());
             player.setDob(Util.getStringDate(playerDto.getDob(),Util.DOB_DATE_FORMAT));
             playerList.add(player);
         });



        return new ResponseEntity<>(playerList, HttpStatus.OK);
    }

}
