package ru.lai.hw2_rest.utils;

import jakarta.servlet.http.HttpServletRequest;
import ru.lai.hw2_rest.models.*;
import ru.lai.hw2_rest.services.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class RequestMapper {
    private static final Logger logger = Logger.getLogger(RequestMapper.class.getName());

    public static Appointment mapToAppointment(HttpServletRequest req) {
        Appointment appointment = new Appointment();

        String idParam = req.getParameter("id");
        if (idParam != null) {
            appointment.setId(parseIntParameter(req, "id"));
        }

        String datetimeParam = req.getParameter("appointmentDatetime");
        if (datetimeParam != null) {
            appointment.setAppointmentDatetime(parseLocalDateTimeParameter(req, "appointmentDatetime"));
        }

        String patientIdParam = req.getParameter("patientId");
        if (patientIdParam != null) {
            Patient patient = new Patient();
            patient.setId(parseIntParameter(req, "patientId"));
            appointment.setPatient(patient);
        }

        String doctorIdParam = req.getParameter("doctorId");
        if (doctorIdParam != null) {
            Doctor doctor = new Doctor();
            doctor.setId(parseIntParameter(req, "doctorId"));
            appointment.setDoctor(doctor);
        }

        String officeIdParam = req.getParameter("officeId");
        if (officeIdParam != null) {
            Office office = new Office();
            office.setId(parseIntParameter(req, "officeId"));
            appointment.setOffice(office);
        }

        return appointment;
    }

    public static Doctor mapToDoctor(HttpServletRequest req, Service<Office> officeService) {
        Doctor doctor = new Doctor();

        String idParam = req.getParameter("id");
        if (idParam != null) {
            doctor.setId(parseIntParameter(req, "id"));
        }

        String firstName = req.getParameter("firstName");
        if (firstName != null) {
            doctor.setFirstName(firstName);
        }

        String lastName = req.getParameter("lastName");
        if (lastName != null) {
            doctor.setLastName(lastName);
        }

        String specialization = req.getParameter("specialization");
        if (specialization != null) {
            doctor.setSpecialization(specialization);
        }

        String experienceParam = req.getParameter("yearsOfExperience");
        if (experienceParam != null) {
            doctor.setYearsOfExperience(parseIntParameter(req, "yearsOfExperience"));
        }

        String[] officeIds = req.getParameterValues("officeIds");
        if (officeIds != null) {
            List<Office> offices = Arrays.stream(officeIds)
                    .map(Integer::parseInt)
                    .map(officeService::getById)
                    .filter(Objects::nonNull)
                    .toList();
            doctor.setOffices(offices);
        }

        return doctor;
    }

    public static Patient mapToPatient(HttpServletRequest req) {
        Patient patient = new Patient();

        String idParam = req.getParameter("id");
        if (idParam != null) {
            patient.setId(parseIntParameter(req, "id"));
        }

        String firstName = req.getParameter("firstName");
        if (firstName != null) {
            patient.setFirstName(firstName);
        }

        String lastName = req.getParameter("lastName");
        if (lastName != null) {
            patient.setLastName(lastName);
        }

        String dateOfBirthParam = req.getParameter("dateOfBirth");
        if (dateOfBirthParam != null) {
            patient.setDateOfBirth(parseDateParameter(req, "dateOfBirth"));
        }

        String gender = req.getParameter("gender");
        if (gender != null) {
            patient.setGender(gender);
        }

        return patient;
    }

    public static Office mapToOffice(HttpServletRequest req, Service<Doctor> doctorService) {
        Office office = new Office();

        String idParam = req.getParameter("id");
        if (idParam != null) {
            office.setId(parseIntParameter(req, "id"));
        }

        String address = req.getParameter("address");
        if (address != null) {
            office.setAddress(address);
        }

        String[] doctorIds = req.getParameterValues("doctorIds");
        if (doctorIds != null) {
            List<Doctor> doctors = Arrays.stream(doctorIds)
                    .map(Integer::parseInt)
                    .map(doctorService::getById)
                    .filter(Objects::nonNull)
                    .toList();
            office.setDoctors(doctors);
        }

        return office;
    }

    private static Integer parseIntParameter(HttpServletRequest req, String paramName) {
        try {
            return Integer.parseInt(req.getParameter(paramName));
        } catch (NumberFormatException e) {
            logger.warning("Invalid format for parameter " + paramName + ": " + e.getMessage());
            throw new RuntimeException("Invalid format for an integer parameter");
        }
    }

    private static Date parseDateParameter(HttpServletRequest req, String paramName) {
        try {
            return Date.valueOf(req.getParameter(paramName));
        } catch (IllegalArgumentException e) {
            logger.warning("Invalid date format for parameter " + paramName + ": " + e.getMessage());
            throw new RuntimeException("Invalid format for a date parameter");
        }
    }

    private static LocalDateTime parseLocalDateTimeParameter(HttpServletRequest req, String paramName) {
        String param = req.getParameter(paramName);
        if (param == null || param.isEmpty()) {
            logger.warning("Parameter " + paramName + " is missing or empty");
            throw new RuntimeException("LocalDateTime parameter is missing or empty");
        }
        try {
            return LocalDateTime.parse(param);
        } catch (DateTimeParseException e) {
            logger.warning("Invalid date format for parameter " + paramName + ": " + e.getMessage());
            throw new RuntimeException("Invalid format for a LocalDateTime parameter");
        }
    }
}
