package com.dezzzl.service;

import com.dezzzl.entity.event.Event;
import com.dezzzl.entity.location.Location;
import com.dezzzl.repository.ILocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationsService implements ILocationsService {
    private final ILocationRepository locationRepository;
//    private final IEventsService eventsService;

    @Override
    public List<Location> getLocations() {
        return locationRepository.getLocations();
    }

    @Override
    public void addLocation(Location location) {
        locationRepository.addLocation(location);
    }

    @Override
    public void updateLocation(Location location) {
        locationRepository.updateLocation(location);
    }

    @Override
    public void deleteLocation(int id) {
        locationRepository.deleteLocation(id);
    }

    @Override
    public void addAdministrator(int locationId, String email) {

    }

    @Override
    public void removeAdministrator(int locationId, String email) {

    }

//    public Optional<Event> getNearestEvent(Location location) {
//        return eventsService.getNearestEvent(location);
//    }


}
