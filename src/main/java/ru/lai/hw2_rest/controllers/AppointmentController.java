package ru.lai.hw2_rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.lai.hw2_rest.models.Appointment;
import ru.lai.hw2_rest.services.AppointmentService;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAll();
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable int id) {
        return appointmentService.getById(id);
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.save(appointment);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable int id, @RequestBody Appointment updatedAppointment) {
        Appointment existingAppointment = appointmentService.getById(id);
        existingAppointment.setAppointmentDatetime(updatedAppointment.getAppointmentDatetime());
        existingAppointment.setPatient(updatedAppointment.getPatient());
        existingAppointment.setDoctor(updatedAppointment.getDoctor());
        existingAppointment.setOffice(updatedAppointment.getOffice());
        return appointmentService.save(existingAppointment);
    }

    @DeleteMapping("/{id}")
    public void deleteAppointment(@PathVariable int id) {
        appointmentService.delete(id);
    }

}
