package ru.lai.hw2_rest.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ru.lai.hw2_rest.models.StaffAssignment;
import ru.lai.hw2_rest.repositories.StaffAssignmentRepository;
import ru.lai.hw2_rest.services.Service;
import ru.lai.hw2_rest.services.StaffAssignmentService;
import ru.lai.hw2_rest.utils.AuthUtil;
import ru.lai.hw2_rest.utils.JsonUtil;
import ru.lai.hw2_rest.utils.RequestMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "staffAssignmentServlet", value = "/staff/*")
public class StaffAssignmentServlet extends HttpServlet {
    private Service<StaffAssignment> staffAssignmentService;

    @Override
    public void init() {
        this.staffAssignmentService = new StaffAssignmentService(new StaffAssignmentRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            if (pathInfo.matches("/\\d+")) {
                int assignmentId = Integer.parseInt(pathInfo.substring(1));
                StaffAssignment assignment = staffAssignmentService.getById(assignmentId);

                if (assignment != null) {
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp,
                            "error", "Staff assignment with ID " + assignmentId + " not found");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp,
                        "error", "Invalid staff assignment ID: " + pathInfo.substring(1));
            }
        } else {
            List<Map<String, Object>> assignmentsWithLinks = new ArrayList<>();
            for (StaffAssignment assignment: staffAssignmentService.getAll()) {
                assignmentsWithLinks.add(getMapWithLinks(assignment));
            }
            JsonUtil.writeJsonResponse(resp, assignmentsWithLinks);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (AuthUtil.isAuthorized(req)) {
            StaffAssignment assignment = RequestMapper.mapToStaffAssignment(req);
            if (staffAssignmentService.create(assignment) > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                JsonUtil.writeJsonResponse(resp,
                        "created", "Staff assignment was created: " + assignment);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp,
                        "error", "Couldn't create a staff assignment: " + assignment);
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
            StaffAssignment assignment = staffAssignmentService.parseEntity(req);
            if  (staffAssignmentService.update(assignment) > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.writeJsonResponse(resp,
                        "updated", "Staff assignment with ID " + assignment.getId() + " was updated");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.writeJsonResponse(resp,
                        "error", "Staff assignment with ID " + assignment.getId() + " not found");
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
                StaffAssignment assignment = staffAssignmentService.parseEntity(req);
                if (staffAssignmentService.delete(assignment.getId()) > 0) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.writeJsonResponse(resp,
                            "deleted", "Staff assignment with ID " + assignment.getId() + " was deleted");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp,
                            "error", "Staff assignment with ID " + assignment.getId() + " not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Staff assignment ID should be a number");
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.writeJsonResponse(resp, "error", "You are not authorised");
        }
    }

    private Map<String, Object> getMapWithLinks(StaffAssignment assignment) {
        Map<String, Object> links =
                Map.of("doctor_endpoint", "/clinic/doctors/" + assignment.getDoctorId(),
                        "office_endpoint", "/clinic/offices/" + assignment.getOfficeId());
        Map<String, Object> mapWithLinks = new LinkedHashMap<>();
        mapWithLinks.put("assignment", assignment);
        mapWithLinks.put("links", links);
        return mapWithLinks;
    }

}