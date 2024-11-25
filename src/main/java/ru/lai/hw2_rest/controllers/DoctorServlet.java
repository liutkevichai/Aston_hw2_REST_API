package ru.lai.hw2_rest.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ru.lai.hw2_rest.models.Doctor;
import ru.lai.hw2_rest.models.Office;
import ru.lai.hw2_rest.repositories.DoctorRepository;
import ru.lai.hw2_rest.repositories.OfficeRepository;
import ru.lai.hw2_rest.services.DoctorService;
import ru.lai.hw2_rest.services.OfficeService;
import ru.lai.hw2_rest.services.Service;
import ru.lai.hw2_rest.utils.AuthUtil;
import ru.lai.hw2_rest.utils.JsonUtil;
import ru.lai.hw2_rest.utils.RequestMapper;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "doctorServlet", value = "/doctors/*")
public class DoctorServlet extends HttpServlet {
    private Service<Doctor> doctorService;
    private Service<Office> officeService;

    @Override
    public void init() {
        this.doctorService = new DoctorService(new DoctorRepository());
        this.officeService = new OfficeService(new OfficeRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            if (pathInfo.matches("/\\d+")) {
                int doctorId = Integer.parseInt(pathInfo.substring(1));
                Doctor doctor = doctorService.getById(doctorId);

                if (doctor != null) {
                    JsonUtil.writeJsonResponse(resp, doctor);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp,
                            "error", "Doctor with ID " + doctorId + " not found");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp,
                        "error", "Invalid doctor ID: " + pathInfo.substring(1));
            }
        } else {
            List<Doctor> doctors = doctorService.getAll();
            JsonUtil.writeJsonResponse(resp, doctors);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (AuthUtil.isAuthorized(req)) {
            Doctor doctor = RequestMapper.mapToDoctor(req, officeService);
            if (doctorService.save(doctor) != null) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                JsonUtil.writeJsonResponse(resp, "created", "Doctor was created: " + doctor);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Couldn't create a doctor: " + doctor);
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
            Doctor doctor = RequestMapper.mapToDoctor(req, officeService);
            if  (doctorService.save(doctor) != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.writeJsonResponse(resp,
                        "updated", "Doctor with ID " + doctor.getId() + " was updated");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.writeJsonResponse(resp,
                        "error", "Doctor with ID " + doctor.getId() + " not found");
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
                Doctor doctor = RequestMapper.mapToDoctor(req, officeService);

                if (doctorService.getById(doctor.getId()) != null) {
                    doctorService.delete(doctor.getId());
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.writeJsonResponse(resp,
                            "deleted", "Doctor with ID " + doctor.getId() + " was deleted");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp, "error", "Doctor with ID " + doctor.getId() + " not found");
                }

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Doctor ID should be a number");
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.writeJsonResponse(resp, "error", "You are not authorised");
        }
    }

}
