package org.dezzzl.zoo.controller;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.pet.editcreate.FeedingRationCreateEditDto;
import org.dezzzl.zoo.dto.pet.editcreate.WinteringPlaceCreateEditDto;
import org.dezzzl.zoo.service.FeedTypeService;
import org.dezzzl.zoo.service.FeedingRationService;
import org.dezzzl.zoo.service.WinteringPlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/wintering-place")
public class WinteringPlaceController {
    private final WinteringPlaceService winteringPlaceService;

    @GetMapping("/create")
    public String showCreateForm(Model model, @ModelAttribute("feedingRation") WinteringPlaceCreateEditDto winteringPlaceCreateEditDto) {
        model.addAttribute("winteringPlace", winteringPlaceCreateEditDto);
        return "winteringplace/create";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        return winteringPlaceService.findById(id)
                .map(winteringPlace -> {
                    model.addAttribute("winteringPlace", winteringPlace);
                    return "winteringplace/winteringplace";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute WinteringPlaceCreateEditDto winteringPlaceCreateEditDto) {
        Integer id = winteringPlaceService.create(winteringPlaceCreateEditDto);
        return "redirect:/wintering-place/" + id;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("winteringPlaces", winteringPlaceService.findAll());
        return "winteringplace/winteringplaces";
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        if (!winteringPlaceService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/wintering-place";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        return winteringPlaceService.findById(id)
                .map(winteringPlace -> {
                    model.addAttribute("winteringPlace", winteringPlace);
                    return "winteringplace/update";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute WinteringPlaceCreateEditDto winteringPlaceCreateEditDto) {
        winteringPlaceService.update(id, winteringPlaceCreateEditDto);
        return "redirect:/wintering-place/" + id;
    }
}
