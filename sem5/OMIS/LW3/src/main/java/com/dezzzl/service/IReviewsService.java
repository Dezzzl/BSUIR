package com.dezzzl.service;

import com.dezzzl.entity.review.Review;

import java.util.List;

public interface IReviewsService {
    List<Review> getReviews(List<Integer> reviewsIds);

    List<Review> getReviews(int reviewAuthorId);

    public void addReview(Review review);

    public void updateReview(Review review);

    public void deleteReview(Integer id);
}
