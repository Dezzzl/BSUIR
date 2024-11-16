package org.dezzzl.zoo.controller;

import lombok.RequiredArgsConstructor;
import org.dezzzl.zoo.dto.employee.EmployeeCreateEditDto;
import org.dezzzl.zoo.dto.employee.EmployeeReadDto;
import org.dezzzl.zoo.dto.employee.EmployeeReferenceReadDto;
import org.dezzzl.zoo.entity.employee.EmployeeType;
import org.dezzzl.zoo.entity.employee.MaritalStatus;
import org.dezzzl.zoo.service.EmployeeService;
import org.dezzzl.zoo.service.SpousesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final SpousesService spousesService;


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employee/employees";
    }

    @GetMapping("/create")
    public String create(Model model, @ModelAttribute EmployeeCreateEditDto employeeCreateEditDto, Pageable pageable) {
        model.addAttribute("employee", employeeCreateEditDto);
        model.addAttribute("employeeTypes", EmployeeType.values());
        model.addAttribute("maritalStatuses", MaritalStatus.values());
        Page<EmployeeReferenceReadDto> employeesWithoutSpouses = employeeService.findEmployeesWithoutSpouses(pageable);
        model.addAttribute("employeesWithoutSpouses", employeesWithoutSpouses.getContent());
        model.addAttribute("page", employeesWithoutSpouses);
        return "employee/create";
    }

    @PostMapping
    public String create(@ModelAttribute EmployeeCreateEditDto employeeCreateEditDto) {
        Integer employeeId = employeeService.create(employeeCreateEditDto);
        return "redirect:employee/" + employeeId;
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        return employeeService.findById(id)
                .map(employee -> {
                    model.addAttribute("employee", employee);
                    return "employee/employee";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no employee with that id"));
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model, Pageable pageable) {
        return employeeService.findById(id)
                .map(employee -> {
                    model.addAttribute("employeeTypes", EmployeeType.values());
                    model.addAttribute("maritalStatuses", MaritalStatus.values());
                    model.addAttribute("employee", employee);
                    Page<EmployeeReferenceReadDto> employeesWithoutSpouses = employeeService.findEmployeesWithoutSpouses(pageable);
                    model.addAttribute("employeesWithoutSpouses", employeesWithoutSpouses.getContent());
                    model.addAttribute("page", employeesWithoutSpouses);
                    return "employee/update";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("delete/{id}")
    public String delete(@PathVariable Integer id) {
        if (!employeeService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/employee";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute EmployeeCreateEditDto employeeCreateEditDto) {
        employeeService.update(id, employeeCreateEditDto);
        return "redirect:/employee/" + id;
    }

    @GetMapping("/spouses/{id}")
    public String getSpouse(@PathVariable Integer id, Model model) {
        return spousesService.findSpouseForEmployee(id)
                .map(employee -> {
                    model.addAttribute("employee", employee);
                    return "employee/employee";
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/spouses")
    public String getAllSpouses(Model model) {
        List<EmployeeReadDto> employees = spousesService.findAllSpouses();
        model.addAttribute("employees", employees);
        return "employee/spouses";
    }


}
