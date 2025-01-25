package com.dezzzl.controller;

import com.dezzzl.entity.event.Event;
import com.dezzzl.entity.location.Location;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
public interface IEventsController {
    @GetMapping("/events")
    String getEvents(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Location location);

    @PostMapping("/event/create")
    String addEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Event event);

    @PostMapping("/event/update")
    String updateEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Event event);

    @PostMapping("/event/delete")
    String deleteEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer id);

    @GetMapping("/event")
    String getEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

//    @GetMapping("/event/create")
//    String getAddForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
//
//    @GetMapping("/event/update")
//    String getUpdateForm(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer eventId);
}
