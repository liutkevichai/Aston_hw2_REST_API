package ru.lai.hw2_rest.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import ru.lai.hw2_rest.models.Office;
import ru.lai.hw2_rest.repositories.OfficeRepository;
import ru.lai.hw2_rest.services.OfficeService;
import ru.lai.hw2_rest.services.Service;
import ru.lai.hw2_rest.utils.AuthUtil;
import ru.lai.hw2_rest.utils.JsonUtil;
import ru.lai.hw2_rest.utils.RequestMapper;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "officeServlet", value = "/offices/*")
public class OfficeServlet extends HttpServlet {
    private Service<Office> officeService;

    @Override
    public void init() {
        this.officeService = new OfficeService(new OfficeRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && !pathInfo.equals("/")) {
            if (pathInfo.matches("/\\d+")) {
                int officeId = Integer.parseInt(pathInfo.substring(1));
                Office office = officeService.getById(officeId);

                if (office != null) {
                    JsonUtil.writeJsonResponse(resp, office);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp,
                            "error", "Office with ID " + officeId + " not found");
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp,
                        "error", "Invalid office ID: " + pathInfo.substring(1));
            }
        } else {
            List<Office> offices = officeService.getAll();
            JsonUtil.writeJsonResponse(resp, offices);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (AuthUtil.isAuthorized(req)) {
            Office office = RequestMapper.mapToOffice(req);
            if (officeService.create(office) > 0) {
                resp.setStatus(HttpServletResponse.SC_CREATED); // Изменено на SC_CREATED
                JsonUtil.writeJsonResponse(resp, "created", "Office was created: " + office);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Couldn't create a office: " + office);
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
            Office office = officeService.parseEntity(req);
            if  (officeService.update(office) > 0) {
                resp.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.writeJsonResponse(resp,
                        "updated", "Office with ID " + office.getId() + " was updated");
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                JsonUtil.writeJsonResponse(resp,
                        "error", "Office with ID " + office.getId() + " not found");
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
                Office office = officeService.parseEntity(req);
                if (officeService.delete(office.getId()) > 0) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.writeJsonResponse(resp,
                            "deleted", "Office with ID " + office.getId() + " was deleted");
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.writeJsonResponse(resp,
                            "error", "Office with ID " + office.getId() + " not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.writeJsonResponse(resp, "error", "Office ID should be a number");
            }

        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonUtil.writeJsonResponse(resp, "error", "You are not authorised");
        }
    }

}