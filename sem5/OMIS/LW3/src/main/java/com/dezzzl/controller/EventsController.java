package com.dezzzl.controller;

import com.dezzzl.entity.event.Event;
import com.dezzzl.entity.location.Location;
import com.dezzzl.service.IEventsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class EventsController implements IEventsController {
    private final IEventsService eventsService;

    @Override
    public String getEvents(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Location location) {
        eventsService.getEvents(location);
        return "event/getEvents";
    }

    @Override
    public String addEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Event event) {
        eventsService.addEvent(event);
        return "event/getEvents";
    }

    @Override
    public String updateEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Event event) {
        eventsService.updateEvent(event);
        return "event/editEvent";
    }

    @Override
    public String deleteEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer id) {
        eventsService.deleteEvent(id);
        return "event/getEvents";
    }

    @Override
    public String getEvent(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        return "event/getEvent";
    }
}
