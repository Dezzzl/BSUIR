package org.dezzzl.zoo.handler;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(basePackages = "org.dezzzl.zoo.controller")
public class ControllerExceptionHandler /*extends ResponseEntityExceptionHandler*/ {

    @ExceptionHandler(ResponseStatusException.class)
    public String handleExceptions(ResponseStatusException exception, HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", "An error occurred: " + exception.getReason());
        return "main";
    }
}
