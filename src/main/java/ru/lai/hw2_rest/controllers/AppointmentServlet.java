package ru.lai.hw2_rest.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ru.lai.hw2_rest.models.Appointment;
import ru.lai.hw2_rest.repositories.AppointmentRepository;
import ru.lai.hw2_rest.services.AppointmentService;
import ru.lai.hw2_rest.services.Service;
import ru.lai.hw2_rest.utils.AuthUtil;
import ru.lai.hw2_rest.utils.JsonUtil;
import ru.lai.hw2_rest.utils.RequestMapper;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "appointmentServlet", value = "/appointments/*")
public class AppointmentServlet extends HttpServlet {
    private Service<Appointment> appointmentService;

    @Override
    public void init() {
        this.appointmentService = new AppointmentService(new AppointmentRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            if (pathInfo.matches("/\\d+")) {
                int appointmentId = Integer.parseInt(pathInfo.substring(1));
                Appointment appointment = appointmentService.getById(appointmentId);

                if (appointment != null) {
                    JsonUtil.writeJsonResponse(resp, appointment);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp, "error", "Appointment not found");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Invalid appointment ID");
            }
        } else {
            List<Appointment> appointments = appointmentService.getAll();
            JsonUtil.writeJsonResponse(resp, appointments);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (AuthUtil.isAuthorized(req)) {
            Appointment appointment = RequestMapper.mapToAppointment(req);
            if (appointmentService.create(appointment) > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED); // Изменено на SC_CREATED
                JsonUtil.writeJsonResponse(resp, "created", "Appointment was created");
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Couldn't create an appointment");
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
            Appointment appointment = appointmentService.parseEntity(req);
            if  (appointmentService.update(appointment) > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.writeJsonResponse(resp, "updated", "Appointment was updated");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.writeJsonResponse(resp, "error", "Appointment not found");
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
                Appointment appointment = appointmentService.parseEntity(req);
                if (appointmentService.delete(appointment.getId()) > 0) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.writeJsonResponse(resp, "deleted", "Appointment was deleted");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp, "error", "Appointment not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Appointment id should be a number");
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.writeJsonResponse(resp, "error", "You are not authorised");
        }
    }

}
