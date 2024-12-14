package ru.lai.hw2_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.lai.hw2_rest.models.Office;
import ru.lai.hw2_rest.services.OfficeService;

import java.util.List;

@RestController
@RequestMapping("/clinic/offices")
public class OfficeController {
    private final OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping
    public List<Office> getAllOffices() {
        return officeService.getAll();
    }

    @GetMapping("/{id}")
    public Office getOfficeById(@PathVariable int id) {
        return officeService.getById(id);
    }

    @PostMapping
    public Office createOffice(@RequestBody Office office) {
        return officeService.save(office);
    }

    @PutMapping("/{id}")
    public Office updateOffice(@PathVariable int id, @RequestBody Office updatedOffice) {
        Office existingOffice = officeService.getById(id);
        existingOffice.setAddress(updatedOffice.getAddress());
        existingOffice.setDoctors(updatedOffice.getDoctors());
        return officeService.save(existingOffice);
    }

    @DeleteMapping("/{id}")
    public void deleteOffice(@PathVariable int id) {
        officeService.delete(id);
    }

}