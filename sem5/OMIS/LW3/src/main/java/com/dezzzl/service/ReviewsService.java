package com.dezzzl.service;

import com.dezzzl.entity.review.Review;
import com.dezzzl.repository.IReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewsService implements IReviewsService {
    private final IReviewsRepository reviewsRepository;


    @Override
    public List<Review> getReviews(List<Integer> reviewsIds) {
        List<Review> allReviews = reviewsRepository.getReviews();
        return allReviews.stream()
                .filter(review -> reviewsIds.contains(review.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Review> getReviews(int reviewAuthorId) {
        List<Review> allReviews = reviewsRepository.getReviews();
        return allReviews.stream()
                .filter(review -> Objects.equals(review.getAuthor().getId(), reviewAuthorId))
                .collect(Collectors.toList());
    }

    @Override
    public void addReview(Review review) {
        reviewsRepository.addReview(review);
    }

    @Override
    public void updateReview(Review review) {
        reviewsRepository.updateReview(review);
    }

    @Override
    public void deleteReview(Integer id) {
        reviewsRepository.deleteReview(id);
    }
}
