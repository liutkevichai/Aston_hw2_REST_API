package ru.lai.hw2_rest.utils;

import jakarta.servlet.http.HttpServletRequest;
import ru.lai.hw2_rest.models.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

public class RequestMapper {
    private static final Logger logger = Logger.getLogger(RequestMapper.class.getName());

    public static Appointment mapToAppointment(HttpServletRequest req) {
        Appointment appointment = new Appointment();

        if (req.getParameter("id") != null ){
            appointment.setId(parseIntParameter(req, "id"));
        }

        appointment.setAppointmentDatetime(parseLocalDateTimeParameter(req, "appointmentDatetime"));
        appointment.setPatientId(parseIntParameter(req, "patientId"));
        appointment.setDoctorId(parseIntParameter(req, "doctorId"));
        appointment.setOfficeId(parseIntParameter(req, "officeId"));

        return appointment;
    }

    public static Doctor mapToDoctor(HttpServletRequest req) {
        Doctor doctor = new Doctor();

        if (req.getParameter("id") != null ){
            doctor.setId(parseIntParameter(req, "id"));
        }

        doctor.setFirstName(req.getParameter("firstName"));
        doctor.setLastName(req.getParameter("lastName"));
        doctor.setSpecialization(req.getParameter("specialization"));
        doctor.setYearsOfExperience(parseIntParameter(req, "yearsOfExperience"));

        return doctor;
    }

    public static Patient mapToPatient(HttpServletRequest req) {
        Patient patient = new Patient();

        if (req.getParameter("id") != null ){
            patient.setId(parseIntParameter(req, "id"));
        }

        patient.setFirstName(req.getParameter("firstName"));
        patient.setLastName(req.getParameter("lastName"));
        patient.setDateOfBirth(parseDateParameter(req, "dateOfBirth"));
        patient.setGender(req.getParameter("gender"));

        return patient;
    }

    public static Office mapToOffice(HttpServletRequest req) {
        Office office = new Office();

        if (req.getParameter("id") != null ){
            office.setId(parseIntParameter(req, "id"));
        }

        office.setAddress(req.getParameter("address"));

        return office;
    }

    public static StaffAssignment mapToStaffAssignment(HttpServletRequest req) {
        StaffAssignment assignment = new StaffAssignment();

        if (req.getParameter("id") != null){
            assignment.setId(parseIntParameter(req, "id"));
        }

        assignment.setDoctorId(parseIntParameter(req, "doctorId"));
        assignment.setOfficeId(parseIntParameter(req, "officeId"));

        return assignment;
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
