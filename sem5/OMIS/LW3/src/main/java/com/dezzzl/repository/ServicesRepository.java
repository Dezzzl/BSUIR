package com.dezzzl.repository;

import com.dezzzl.entity.event.Event;
import com.dezzzl.entity.location.Location;
import com.dezzzl.entity.review.Review;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ServicesRepository implements IEventsRepository, ILocationRepository, IReviewsRepository {
    @Override
    public List<Event> getEvents() {
        return null;
    }

    @Override
    public void addEvent(Event event) {

    }

    @Override
    public void updateEvent(Event event) {

    }

    @Override
    public void deleteEvent(Integer id) {

    }

    @Override
    public Optional<Event> getNearestEvent(Location location) {
        return Optional.empty();
    }

    @Override
    public List<Location> getLocations() {
        return null;
    }

    @Override
    public void addLocation(Location location) {

    }

    @Override
    public void updateLocation(Location location) {

    }

    @Override
    public void deleteLocation(int id) {

    }

    @Override
    public List<Review> getReviews() {
        return null;
    }

    @Override
    public void addReview(Review review) {

    }

    @Override
    public void updateReview(Review review) {

    }

    @Override
    public void deleteReview(Integer id) {

    }
}
