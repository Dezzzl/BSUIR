package com.dezzzl.service;

import com.dezzzl.entity.event.Event;
import com.dezzzl.entity.location.Location;
import com.dezzzl.repository.IEventsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventsService implements IEventsService {
    private final IEventsRepository eventsRepository;
//    private final ILocationsService locationsService;

    @Override
    public List<Event> getEvents(Location location) {
        return eventsRepository.getEvents().stream()
                .filter(event -> Objects.equals(event.getLocation(), location))
                .collect(Collectors.toList());
    }

    @Override
    public void addEvent(Event event) {
        eventsRepository.addEvent(event);
    }

    @Override
    public void updateEvent(Event event) {
        eventsRepository.updateEvent(event);
    }

    @Override
    public void deleteEvent(Integer id) {
        eventsRepository.deleteEvent(id);
    }

    @Override
    public Optional<Event> getNearestEvent(Location location) {
        return Optional.ofNullable(location.getNearestEvent());
    }

    public Location getEventLocation(Event event) {
        return event.getLocation();
    }


}
