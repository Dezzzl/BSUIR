package com.dezzzl.controller;

import com.dezzzl.entity.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
public interface IAuthController {
    @GetMapping("/profile/{userId}")
    String profileGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable Integer userId);

    @GetMapping("/register")
    String registerGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @PostMapping("/register")
    String registerPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, User user);

    @GetMapping("/login")
    String loginGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @PostMapping("/login")
    String loginPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String email, String password);

    @PostMapping("/logout")
    String logoutPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String email);

    @PostMapping("/user/update")
    String updateUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, User user);

    @PostMapping("/user/delete")
    String deleteUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String email);
}
