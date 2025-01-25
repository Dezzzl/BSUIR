package com.dezzzl.service;

import com.dezzzl.entity.event.Event;
import com.dezzzl.entity.location.Location;

import java.util.List;
import java.util.Optional;

public interface IEventsService {
    List<Event> getEvents(Location location);

    void addEvent(Event event);

    void updateEvent(Event event);

    void deleteEvent(Integer id);

    Optional<Event> getNearestEvent(Location location);
}
