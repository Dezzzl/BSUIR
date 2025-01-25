package com.dezzzl.repository;

import com.dezzzl.entity.location.Location;

import java.util.List;

public interface ILocationRepository {
    List<Location> getLocations();

    void addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocation(int id);
}
