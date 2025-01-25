package com.dezzzl.controller;

import com.dezzzl.entity.review.Review;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public interface IReviewController {
    @GetMapping("/reviews")
    String getReviews(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @GetMapping("/review/{id}")
    String getReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable Integer id);

    @PostMapping("/review/create")
    String addReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Review review);

    @PostMapping("/review/update")
    String updateReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Review review);

    @PostMapping("/review/update")
    String deleteReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer id);

//    @GetMapping("/review/create")
//    String getAddForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
//
//    @GetMapping("/review/update")
//    String getUpdateForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer reviewId);
}
