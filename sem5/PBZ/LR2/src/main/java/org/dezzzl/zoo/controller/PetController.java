package org.dezzzl.zoo.controller;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.employee.EmployeeReadDto;
import org.dezzzl.zoo.dto.feedtype.FeedTypeReadDto;
import org.dezzzl.zoo.dto.habitatzone.HabitatZoneReadDto;
import org.dezzzl.zoo.dto.pet.editcreate.PetCreateEditDto;
import org.dezzzl.zoo.dto.pet.read.PetReadDto;
import org.dezzzl.zoo.dto.pet.read.PetReferencesReadDto;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.dezzzl.zoo.service.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pet")
public class PetController {

    private final EmployeeService employeeService;

    private final HabitatZoneService habitatZoneService;

    private final FeedTypeService feedTypeService;

    private final PetService petService;

    private final BirdService birdService;

    private final ReptileService reptileService;

    private static final String PET = "pet";

    private static final String BIRD = "bird";

    private static final String REPTILE = "reptile";

    @GetMapping("/create")
    public String showCreatePetForm(Model model,
                                    @ModelAttribute PetCreateEditDto petCreateEditDto,
                                    @RequestParam(value = "zookeeperPage", defaultValue = "0") int zookeeperPage,
                                    @RequestParam(value = "veterinarianPage", defaultValue = "0") int veterinarianPage,
                                    @RequestParam(value = "feedingTypePage", defaultValue = "0") int feedingTypePage,
                                    @RequestParam(value = "habitatZonePage", defaultValue = "0") int habitatZonePage,
                                    Pageable pageable) {

        Page<EmployeeReadDto> zookeepers = employeeService.findAllByEmployeeType(
                PageRequest.of(zookeeperPage, pageable.getPageSize()),
                EmployeeType.ZOOKEEPER);
        Page<EmployeeReadDto> veterinarians = employeeService.findAllByEmployeeType(
                PageRequest.of(veterinarianPage, pageable.getPageSize()),
                EmployeeType.VETERINARIAN);
        Page<FeedTypeReadDto> feedingTypes = feedTypeService.findAll(PageRequest.of(feedingTypePage, pageable.getPageSize()));
        Page<HabitatZoneReadDto> habitatZones = habitatZoneService.findAll(PageRequest.of(habitatZonePage, pageable.getPageSize()));

        model.addAttribute("pet", petCreateEditDto);
        model.addAttribute("zookeepers", zookeepers);
        model.addAttribute("veterinarians", veterinarians);
        model.addAttribute("feedingTypes", feedingTypes);
        model.addAttribute("habitatZones", habitatZones);

        return "pet/create";
    }

    @PostMapping
    public String create(@ModelAttribute PetCreateEditDto petCreateEditDto) {
        Integer petId;
        if (petCreateEditDto.getPetType().equals(BIRD)) {
            petId = birdService.create(petCreateEditDto);
        } else if (petCreateEditDto.getPetType().equals(REPTILE)) {
            petId = reptileService.create(petCreateEditDto);
        } else {
            petId = petService.create(petCreateEditDto);
        }
        return "redirect:/pet/" + petId + "?petType=" + petCreateEditDto.getPetType();
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, @RequestParam String petType, Model model) {
        PetReadDto pet = null;
        if (Objects.equals(petType, PET)) {
            pet = petService.findById(id);
        } else if (Objects.equals(petType, BIRD)) {
            pet = birdService.findById(id);
        } else {
            pet = reptileService.findById(id);
        }
        model.addAttribute("pet", pet);
        return "pet/pet";
    }

    @PostMapping("delete/{id}")
    public String deletePet(@PathVariable Integer id) {
        if (!petService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/pet";
    }

    @GetMapping
    public String findAll(Model model) {
        List<PetReferencesReadDto> pets = new ArrayList<>();
        pets.addAll(petService.findAll());
        pets.addAll(birdService.findAll());
        pets.addAll(reptileService.findAll());
        model.addAttribute("pets", pets);
        return "pet/pets";
    }

    @PostMapping("/update/{id}")
    public String updateZone(@PathVariable Integer id, @ModelAttribute PetCreateEditDto petCreateEditDto) {
        if (Objects.equals(petCreateEditDto.getPetType(), PET)) {
            petService.updatePet(id, petCreateEditDto);
        } else if (Objects.equals(petCreateEditDto.getPetType(), BIRD)) {
            birdService.updateBird(id, petCreateEditDto);
        } else {
            reptileService.updateReptile(id, petCreateEditDto);
        }
        return "redirect:/pet/" + id + "?petType=" + petCreateEditDto.getPetType();
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id, @RequestParam String petType, Model model,
                                 @RequestParam(value = "zookeeperPage", defaultValue = "0") int zookeeperPage,
                                 @RequestParam(value = "veterinarianPage", defaultValue = "0") int veterinarianPage,
                                 @RequestParam(value = "feedingTypePage", defaultValue = "0") int feedingTypePage,
                                 @RequestParam(value = "habitatZonePage", defaultValue = "0") int habitatZonePage,
                                 Pageable pageable) {
        PetReadDto pet = null;
        if (Objects.equals(petType, PET)) {
            pet = petService.findById(id);
        } else if (Objects.equals(petType, BIRD)) {
            pet = birdService.findById(id);
        } else {
            pet = reptileService.findById(id);
        }
        Page<EmployeeReadDto> veterinarians = employeeService.findAllByEmployeeType(
                PageRequest.of(veterinarianPage, pageable.getPageSize()),
                EmployeeType.VETERINARIAN);
        Page<EmployeeReadDto> zookeepers = employeeService.findAllByEmployeeType(
                PageRequest.of(zookeeperPage, pageable.getPageSize()),
                EmployeeType.ZOOKEEPER);
        Page<FeedTypeReadDto> feedingTypes = feedTypeService.findAll(PageRequest.of(feedingTypePage, pageable.getPageSize()));
        Page<HabitatZoneReadDto> habitatZones = habitatZoneService.findAll(PageRequest.of(habitatZonePage, pageable.getPageSize()));

        model.addAttribute("pet", pet);
        model.addAttribute("veterinarians", veterinarians);
        model.addAttribute("zookeepers", zookeepers);
        model.addAttribute("feedingTypes", feedingTypes);
        model.addAttribute("habitatZones", habitatZones);

        return "pet/update";
    }



}
