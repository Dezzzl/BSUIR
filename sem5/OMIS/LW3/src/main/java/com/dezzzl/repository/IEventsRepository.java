package com.dezzzl.repository;

import com.dezzzl.entity.event.Event;
import com.dezzzl.entity.location.Location;

import java.util.List;
import java.util.Optional;

public interface IEventsRepository {
    List<Event> getEvents();

    void addEvent(Event event);

    void updateEvent(Event event);

    void deleteEvent(Integer id);

    Optional<Event> getNearestEvent(Location location);
}
