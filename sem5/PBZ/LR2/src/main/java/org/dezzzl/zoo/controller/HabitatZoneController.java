package org.dezzzl.zoo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneCreateEditDto;
import org.dezzzl.zoo.service.HabitatZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Controller
@RequestMapping("/zones")
@RequiredArgsConstructor
public class HabitatZoneController {

    private final HabitatZoneService habitatZoneService;

    @GetMapping("/create")
    public String showCreateZoneForm(Model model, @ModelAttribute("zone") HabitatZoneCreateEditDto habitatZoneCreateEditDto) {
        model.addAttribute("zone", habitatZoneCreateEditDto);
        return "zone/create";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return habitatZoneService.findById(id)
                .map(zone -> {
                    model.addAttribute("zone", zone);
                    return "zone/zone";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public String createZone(@ModelAttribute HabitatZoneCreateEditDto habitatZoneCreateEditDto) {
        Integer zoneId = habitatZoneService.create(habitatZoneCreateEditDto);
        return "redirect:/zones/" + zoneId;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("zones", habitatZoneService.findAll());
        return "zone/zones";
    }

    @PostMapping("delete/{id}")
    public String deleteZone(@PathVariable Integer id) {
        if (!habitatZoneService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/zones";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        return habitatZoneService.findById(id)
                .map(zone -> {
                    model.addAttribute("zone", zone);
                    return "zone/update";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/update/{id}")
    public String updateZone(@PathVariable Integer id, @ModelAttribute HabitatZoneCreateEditDto habitatZoneCreateEditDto) {
        habitatZoneService.updateZone(id, habitatZoneCreateEditDto);
        return "redirect:/zones/" + id;
    }
}
