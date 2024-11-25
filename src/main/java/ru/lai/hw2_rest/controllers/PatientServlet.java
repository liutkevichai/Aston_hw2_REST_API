package ru.lai.hw2_rest.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ru.lai.hw2_rest.models.Patient;
import ru.lai.hw2_rest.repositories.PatientRepository;
import ru.lai.hw2_rest.services.PatientService;
import ru.lai.hw2_rest.services.Service;
import ru.lai.hw2_rest.utils.AuthUtil;
import ru.lai.hw2_rest.utils.JsonUtil;
import ru.lai.hw2_rest.utils.RequestMapper;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "patientServlet", value = "/patients/*")
public class PatientServlet extends HttpServlet {
    private Service<Patient> patientService;

    @Override
    public void init() {
        this.patientService = new PatientService(new PatientRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            if (pathInfo.matches("/\\d+")) {
                int patientId = Integer.parseInt(pathInfo.substring(1));
                Patient patient = patientService.getById(patientId);

                if (patient != null) {
                    JsonUtil.writeJsonResponse(resp, patient);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp,
                            "error", "Patient with ID " + patientId + " not found");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp,
                        "error", "Invalid patient ID: " + pathInfo.substring(1));
            }
        } else {
            List<Patient> patients = patientService.getAll();
            JsonUtil.writeJsonResponse(resp, patients);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (AuthUtil.isAuthorized(req)) {
            Patient patient = RequestMapper.mapToPatient(req);
            if (patientService.create(patient) > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                JsonUtil.writeJsonResponse(resp, "created", "Patient was created:" + patient);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Couldn't create a patient:" + patient);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.writeJsonResponse(resp, "error", "You are not authorised");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (AuthUtil.isAuthorized(req)) {
            Patient patient = patientService.parseEntity(req);
            if  (patientService.update(patient) > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.writeJsonResponse(resp,
                        "updated", "Patient with ID " + patient.getId() + " was updated");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.writeJsonResponse(resp,
                        "error", "Patient with ID " + patient.getId() + " not found");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.writeJsonResponse(resp, "error", "You are not authorised");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (AuthUtil.isAuthorized(req)) {
            try {
                Patient patient = patientService.parseEntity(req);
                if (patientService.delete(patient.getId()) > 0) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.writeJsonResponse(resp,
                            "deleted", "Patient with ID " + patient.getId() + " was deleted");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp,
                            "error", "Patient with ID " + patient.getId() + " not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Patient ID should be a number");
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.writeJsonResponse(resp, "error", "You are not authorised");
        }
    }

}