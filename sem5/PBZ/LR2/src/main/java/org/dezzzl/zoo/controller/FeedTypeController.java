package org.dezzzl.zoo.controller;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.feedtype.FeedTypeCreateEditDto;
import org.dezzzl.zoo.service.FeedTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/feed-type")
public class FeedTypeController {
    private final FeedTypeService feedTypeService;

    @GetMapping("/create")
    public String showCreateForm(Model model, @ModelAttribute("feedType") FeedTypeCreateEditDto feedTypeCreateEditDto) {
        model.addAttribute("feedType", feedTypeCreateEditDto);
        return "feedtype/create";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return feedTypeService.findById(id)
                .map(feedType -> {
                    model.addAttribute("feedType", feedType);
                    return "feedtype/feedtype";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public String create(@ModelAttribute FeedTypeCreateEditDto feedTypeCreateEditDto) {
        Integer zoneId = feedTypeService.create(feedTypeCreateEditDto);
        return "redirect:/feed-type/" + zoneId;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("feedTypes", feedTypeService.findAll());
        return "feedtype/feedtypes";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        if (!feedTypeService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/feed-type";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        return feedTypeService.findById(id)
                .map(feedType -> {
                    model.addAttribute("feedType", feedType);
                    return "feedtype/update";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute FeedTypeCreateEditDto feedTypeCreateEditDto) {
        feedTypeService.update(id, feedTypeCreateEditDto);
        return "redirect:/feed-type/" + id;
    }
}
