package com.dezzzl.controller;

import com.dezzzl.entity.user.User;
import com.dezzzl.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController implements IAuthController {
    private final IAuthService authService;

    @Override
    public String profileGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable Integer userId) {
        return "auth/getProfile";
    }

    @Override
    public String registerGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return "auth/registerGet";
    }

    @Override
    public String registerPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, User user) {
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());
        authService.register(user);
        return "redirect:/login";
    }

    @Override
    public String loginGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return "auth/loginGet";
    }

    @Override
    public String loginPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String email, String password) {
        authService.login(email, password);
        return "redirect:/locations";
    }

    @Override
    public String logoutPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String email) {
        return "redirect:/login";
    }

    @Override
    public String updateUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, User user) {
        authService.updateUser(user);
        return "redirect:/profile";
    }

    @Override
    public String deleteUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String email) {
        return "redirect:/register";
    }

    private boolean validateEmail(String email) {
        return true;
    }

    private boolean validatePassword(String password) {
        return true;
    }
}
