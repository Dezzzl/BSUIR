package com.dezzzl.controller;

import com.dezzzl.entity.review.Review;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ReviewController implements IReviewController {
    @Override
    public String getReviews(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return "event/getEvent";
    }

    @Override
    public String getReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer id) {
        return "event/getEvent";
    }

    @Override
    public String addReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Review review) {
        return "event/getEvent";
    }

    @Override
    public String updateReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Review review) {
        return "event/getEvent";
    }

    @Override
    public String deleteReview(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer id) {
        return "event/getEvent";
    }
}
