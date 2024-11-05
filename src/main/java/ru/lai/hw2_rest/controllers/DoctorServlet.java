package ru.lai.hw2_rest.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ru.lai.hw2_rest.models.Doctor;
import ru.lai.hw2_rest.repositories.DoctorRepository;
import ru.lai.hw2_rest.services.DoctorService;
import ru.lai.hw2_rest.services.Service;
import ru.lai.hw2_rest.utils.AuthUtil;
import ru.lai.hw2_rest.utils.JsonUtil;
import ru.lai.hw2_rest.utils.RequestMapper;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "doctorServlet", value = "/doctors/*")
public class DoctorServlet extends HttpServlet {
    private Service<Doctor> doctorService;

    @Override
    public void init() {
        this.doctorService = new DoctorService(new DoctorRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            if (pathInfo.matches("/\\d+")) {
                int doctorId = Integer.parseInt(pathInfo.substring(1));
                Doctor doctor = doctorService.getById(doctorId);

                if (doctor != null) {
                    JsonUtil.writeJsonResponse(resp, doctor);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp, "error", "Doctor not found");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Invalid doctor ID");
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
            Doctor doctor = RequestMapper.mapToDoctor(req);
            if (doctorService.create(doctor) > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                JsonUtil.writeJsonResponse(resp, "created", "Doctor was created");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Couldn't create a doctor");
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
            Doctor doctor = doctorService.parseEntity(req);
            if  (doctorService.update(doctor) > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.writeJsonResponse(resp, "updated", "Doctor was updated");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.writeJsonResponse(resp, "error", "Doctor not found");
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
                Doctor doctor = doctorService.parseEntity(req);
                if (doctorService.delete(doctor.getId()) > 0) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.writeJsonResponse(resp, "deleted", "Doctor was deleted");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp, "error", "Doctor not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Doctor id should be a number");
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.writeJsonResponse(resp, "error", "You are not authorised");
        }
    }

}
