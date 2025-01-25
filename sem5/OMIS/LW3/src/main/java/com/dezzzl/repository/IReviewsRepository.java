package com.dezzzl.repository;

import com.dezzzl.entity.review.Review;

import java.util.List;

public interface IReviewsRepository {
    List<Review> getReviews();

    void addReview(Review review);

    void updateReview(Review review);

    void deleteReview(Integer id);
}
