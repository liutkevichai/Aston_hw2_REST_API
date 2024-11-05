package ru.lai.hw2_rest.controllers;

import java.io.*;
import java.util.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ru.lai.hw2_rest.utils.JsonUtil;

@WebServlet(name = "apiControllerServlet", value = "/")
public class ApiControllerServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        Map<String, Object> apiInfo = new LinkedHashMap<>();
        apiInfo.put("description", "Welcome to the Private Clinic REST API. This API provides endpoints to manage doctors, patients, offices, and appointments.");
        apiInfo.put("version", "1.0");
        apiInfo.put("resources", List.of(
                Map.of("name", "Doctors", "endpoint", "/clinic/doctors", "methods", List.of("GET", "POST", "PUT", "DELETE")),
                Map.of("name", "Offices", "endpoint", "/clinic/offices", "methods", List.of("GET", "POST", "PUT", "DELETE")),
                Map.of("name", "Patients", "endpoint", "/clinic/patients", "methods", List.of("GET", "POST", "PUT", "DELETE")),
                Map.of("name", "Appointments", "endpoint", "/clinic/appointments", "methods", List.of("GET", "POST", "PUT", "DELETE"))
        ));

        JsonUtil.writeJsonResponse(resp, apiInfo);
    }
}