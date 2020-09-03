package com.noor.blog;

import com.noor.blog.exceptions.ResourceAlreadyExistException;
import com.noor.blog.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Component
@ControllerAdvice
public class GlobalExceptionHandler  {

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(ResourceAlreadyExistException.class)
    public String handleConflict(HttpServletRequest req, Exception e, Model model){

        model.addAttribute("errorMessage",e.getMessage());
        e.printStackTrace();
        return "error";
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(HttpServletRequest req, Exception e, Model model){

        model.addAttribute("errorMessage",e.getMessage());
        e.printStackTrace();
        return "error";
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String handleInternalServerError(HttpServletRequest req, Exception e, Model model){

        model.addAttribute("errorMessage",e.getMessage());
        e.printStackTrace();
        return "error";
    }
}
