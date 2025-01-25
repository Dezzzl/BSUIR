package com.dezzzl.controller;

import com.dezzzl.entity.location.Location;
import com.dezzzl.service.ILocationsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class LocationController implements ILocationController {
    private final ILocationsService locationsService;

    @Override
    public String getLocations(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<Location> locations = locationsService.getLocations();
        return "location/getLocations";
    }

    @Override
    public String getLocation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return "location/getLocation";
    }

    @Override
    public String addLocation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Location location) {
        locationsService.addLocation(location);
        return "location/getLocations";
    }

    @Override
    public String updateLocation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Location location) {
        locationsService.updateLocation(location);
        return "location/editLocation";
    }

    @Override
    public String deleteLocation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer id) {
        locationsService.deleteLocation(id);
        return "location/getLocations";
    }

    @Override
    public String addAdministrator(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer locationId, String email) {
        return "location/addAdmin";
    }

    @Override
    public String removeAdministrator(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer locationId, String email) {
        return "location/getLocation";
    }
}
