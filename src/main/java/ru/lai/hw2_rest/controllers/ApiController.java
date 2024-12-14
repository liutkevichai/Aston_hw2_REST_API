package ru.lai.hw2_rest.controllers;

import java.io.*;
import java.util.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinic")
public class ApiController {

    @GetMapping(produces = "application/json")
    public Map<String, Object> doGet() throws IOException {

        Map<String, Object> apiInfo = new LinkedHashMap<>();
        apiInfo.put("description", "Welcome to the Private Clinic REST API. This API provides endpoints to manage doctors, patients, offices, and appointments.");
        apiInfo.put("version", "2.0");
        apiInfo.put("resources", List.of(
                Map.of("name", "Doctors", "endpoint", "/clinic/doctors", "methods", List.of("GET", "POST", "PUT", "DELETE")),
                Map.of("name", "Offices", "endpoint", "/clinic/offices", "methods", List.of("GET", "POST", "PUT", "DELETE")),
                Map.of("name", "Patients", "endpoint", "/clinic/patients", "methods", List.of("GET", "POST", "PUT", "DELETE")),
                Map.of("name", "Appointments", "endpoint", "/clinic/appointments", "methods", List.of("GET", "POST", "PUT", "DELETE"))
        ));

        return apiInfo;
    }
}