package ru.lai.hw2_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.lai.hw2_rest.models.Doctor;
import ru.lai.hw2_rest.services.DoctorService;

import java.util.List;

@RestController
@RequestMapping("/clinic/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable int id) {
        return doctorService.getById(id);
    }

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.save(doctor);
    }

    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable int id, @RequestBody Doctor updatedDoctor) {
        Doctor existingDoctor = doctorService.getById(id);
        existingDoctor.setFirstName(updatedDoctor.getFirstName());
        existingDoctor.setLastName(updatedDoctor.getLastName());
        existingDoctor.setSpecialization(updatedDoctor.getSpecialization());
        existingDoctor.setYearsOfExperience(updatedDoctor.getYearsOfExperience());
        existingDoctor.setOffices(updatedDoctor.getOffices());
        return doctorService.save(existingDoctor);
    }

    @DeleteMapping("/{id}")
    public void deleteDoctor(@PathVariable int id) {
        doctorService.delete(id);
    }

}
