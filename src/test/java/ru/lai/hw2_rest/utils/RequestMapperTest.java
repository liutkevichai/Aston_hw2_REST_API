package ru.lai.hw2_rest.utils;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.lai.hw2_rest.models.Appointment;
import ru.lai.hw2_rest.models.Doctor;
import ru.lai.hw2_rest.models.Office;
import ru.lai.hw2_rest.models.Patient;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RequestMapperTest {
    @BeforeEach
    void setUp() {
        Logger logger = Logger.getLogger(RequestMapper.class.getName());
        logger.setLevel(Level.OFF);
    }

    @Test
    void testMapToAppointment() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("appointmentDatetime")).thenReturn("2024-11-10T15:30");
        when(request.getParameter("patientId")).thenReturn("10");
        when(request.getParameter("doctorId")).thenReturn("20");
        when(request.getParameter("officeId")).thenReturn("5");

        Appointment appointment = RequestMapper.mapToAppointment(request);

        assertEquals(1, appointment.getId());
        assertEquals(LocalDateTime.of(2024, 11, 10, 15, 30),
                appointment.getAppointmentDatetime());
        assertEquals(10, appointment.getPatientId());
        assertEquals(20, appointment.getDoctorId());
        assertEquals(5, appointment.getOfficeId());
    }

    @Test
    void testMapToAppointmentNoId() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("appointmentDatetime")).thenReturn("2024-11-10T15:30");
        when(request.getParameter("patientId")).thenReturn("10");
        when(request.getParameter("doctorId")).thenReturn("20");
        when(request.getParameter("officeId")).thenReturn("5");

        Appointment appointment = RequestMapper.mapToAppointment(request);

        assertEquals(appointment.getId(), 0);
        assertEquals(LocalDateTime.of(2024, 11, 10, 15, 30),
                appointment.getAppointmentDatetime());
        assertEquals(10, appointment.getPatientId());
        assertEquals(20, appointment.getDoctorId());
        assertEquals(5, appointment.getOfficeId());
    }

    @Test
    void testMapToDoctor() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("firstName")).thenReturn("John");
        when(request.getParameter("lastName")).thenReturn("Doe");
        when(request.getParameter("specialization")).thenReturn("Surgeon");
        when(request.getParameter("yearsOfExperience")).thenReturn("15");

        Doctor doctor = RequestMapper.mapToDoctor(request);

        assertEquals(doctor.getId(), 1);
        assertEquals(doctor.getFirstName(), "John");
        assertEquals(doctor.getLastName(), "Doe");
        assertEquals(doctor.getSpecialization(), "Surgeon");
        assertEquals(doctor.getYearsOfExperience(), 15);
    }

    @Test
    void testMapToDoctorNoId() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("firstName")).thenReturn("John");
        when(request.getParameter("lastName")).thenReturn("Doe");
        when(request.getParameter("specialization")).thenReturn("Surgeon");
        when(request.getParameter("yearsOfExperience")).thenReturn("15");

        Doctor doctor = RequestMapper.mapToDoctor(request);

        assertEquals(doctor.getId(), 0);
        assertEquals(doctor.getFirstName(), "John");
        assertEquals(doctor.getLastName(), "Doe");
        assertEquals(doctor.getSpecialization(), "Surgeon");
        assertEquals(doctor.getYearsOfExperience(), 15);
    }

    @Test
    void testMapToPatient() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("firstName")).thenReturn("John");
        when(request.getParameter("lastName")).thenReturn("Doe");
        when(request.getParameter("dateOfBirth")).thenReturn("1980-07-20");
        when(request.getParameter("gender")).thenReturn("Male");

        Patient patient = RequestMapper.mapToPatient(request);

        assertEquals(patient.getId(), 1);
        assertEquals(patient.getFirstName(), "John");
        assertEquals(patient.getLastName(), "Doe");
        assertEquals(patient.getDateOfBirth(), Date.valueOf("1980-07-20"));
        assertEquals(patient.getGender(), "Male");
    }

    @Test
    void testMapToPatientNoId() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("firstName")).thenReturn("John");
        when(request.getParameter("lastName")).thenReturn("Doe");
        when(request.getParameter("dateOfBirth")).thenReturn("1980-07-20");
        when(request.getParameter("gender")).thenReturn("Male");

        Patient patient = RequestMapper.mapToPatient(request);

        assertEquals(patient.getId(), 0);
        assertEquals(patient.getFirstName(), "John");
        assertEquals(patient.getLastName(), "Doe");
        assertEquals(patient.getDateOfBirth(), Date.valueOf("1980-07-20"));
        assertEquals(patient.getGender(), "Male");
    }

    @Test
    void testMapToOffice() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getParameter("id")).thenReturn("1");
        when(request.getParameter("address")).thenReturn("Fake address, 15");

        Office office = RequestMapper.mapToOffice(request);

        assertEquals(office.getId(), 1);
        assertEquals(office.getAddress(), "Fake address, 15");
    }

    @Test
    void testMapToOfficeEmpty() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getParameter("id")).thenReturn(null);
        when(request.getParameter("address")).thenReturn(null);

        Office office = RequestMapper.mapToOffice(request);

        assertEquals(office.getId(), 0);
        assertNull(office.getAddress());
    }
}