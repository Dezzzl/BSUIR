package org.dezzzl.zoo.controller;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneCreateEditDto;
import org.dezzzl.zoo.dto.pet.editcreate.FeedingRationCreateEditDto;
import org.dezzzl.zoo.service.FeedTypeService;
import org.dezzzl.zoo.service.FeedingRationService;
import org.dezzzl.zoo.service.HabitatZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("feeding-ration")
@RequiredArgsConstructor
public class FeedingRationController {
    private final FeedingRationService feedingRationService;
    private final FeedTypeService feedTypeService;

    @GetMapping("/create")
    public String showCreateForm(Model model, @ModelAttribute("feedingRation") FeedingRationCreateEditDto feedingRationCreateEditDto) {
        model.addAttribute("feedTypes", feedTypeService.findAll());
        return "feedration/create";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return feedingRationService.findById(id)
                .map(feedingRation -> {
                    model.addAttribute("feedingRation", feedingRation);
                    return "feedration/feedration";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public String create(@ModelAttribute FeedingRationCreateEditDto feedingRationCreateEditDto) {
        Integer id = feedingRationService.create(feedingRationCreateEditDto);
        return "redirect:/feeding-ration/" + id;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("feedingRations", feedingRationService.findAll());
        return "feedration/feedrations";
    }

    @PostMapping("delete/{id}")
    public String deleteZone(@PathVariable Integer id) {
        if (!feedingRationService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/feeding-ration";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        return feedingRationService.findById(id)
                .map(feedingRation -> {
                    model.addAttribute("feedTypes", feedTypeService.findAll());
                    model.addAttribute("feedingRation", feedingRation);
                    return "feedration/update";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute FeedingRationCreateEditDto feedingRationCreateEditDto) {
        feedingRationService.update(id, feedingRationCreateEditDto);
        return "redirect:/feeding-ration/" + id;
    }
}
