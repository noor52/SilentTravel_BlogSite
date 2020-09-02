package com.fardoushlab.iccweb.util;

import com.fardoushlab.iccweb.models.Role;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Util {
    public static final String DOB_DATE_FORMAT ="dd/MM/yyyy";


    public static LocalDate getFormattedDate(String strDate, String pattern){


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate date = LocalDate.parse(strDate,formatter);
        System.out.println("Date: "+date.toString());
        return date;
    }

    public static String getStringDate(LocalDate date, String pattern ){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(date);




    }

    public static String getStringRole(Role role){
        String strRole = "";
        switch (role){
            case ROLE_SUPER_ADMIN:
                strRole = "SUPER ADMIN";
                break;
            case ROLE_ADMIN:
                strRole = "ADMIN";
                break;
            case ROLE_TEAM_MANAGER:
                strRole = "TEAM MANAGER";
                break;
            case ROLE_COACHING_STAFF:
                strRole = "COACHING STAFF";
                break;
            case ROLE_CAPTAIN:
                strRole = "CAPTAIN";
                break;
            case ROLE_PLAYER:
                strRole = "PLAYER";
                break;
//            case ROLE_BLOGGER:
//                strRole = "BLOGGER";
//                break;
            case ROLE_USER:
                strRole = "USER";
                break;
        }
        return strRole;
    }
}
