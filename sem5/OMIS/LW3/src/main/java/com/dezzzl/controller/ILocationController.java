package com.dezzzl.controller;

import com.dezzzl.entity.location.Location;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
public interface ILocationController {
    @GetMapping("/locations")
    String getLocations(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @GetMapping("/location")
    String getLocation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

    @PostMapping("/location/create")
    String addLocation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Location location);

    @PostMapping("/location/update")
    String updateLocation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Location location);

    @PostMapping("/location/delete")
    String deleteLocation(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer id);

    @PostMapping("location/add/admin")
    String addAdministrator(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer locationId, String email);

    @PostMapping("location/remove/admin")
    String removeAdministrator(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer locationId, String email);

//    @GetMapping("/location/create")
//    String getAddForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
//
//    @GetMapping("/location/update")
//    String getUpdateForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer locationId);
//
//    @GetMapping("/location/add/admin")
//    String getAddAdministratorForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
//
//    @GetMapping("/location/remove/admin")
//    String getRemoveAdministratorForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
