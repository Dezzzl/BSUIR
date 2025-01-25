package com.dezzzl.service;

import com.dezzzl.entity.location.Location;

import java.util.List;

public interface ILocationsService {

    List<Location> getLocations();

    void addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocation(int id);

    void addAdministrator(int locationId, String email);

    void removeAdministrator(int locationId, String email);
}
