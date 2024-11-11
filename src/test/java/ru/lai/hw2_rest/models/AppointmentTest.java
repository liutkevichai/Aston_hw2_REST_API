package ru.lai.hw2_rest.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class AppointmentTest {
    private Appointment appointment;
    private Map<String, String> map;

    @BeforeEach
    void setup() {
        appointment = new Appointment();
        map = new HashMap<>();
    }

    @Test
    void testSetUpWithMapValidData() {
        map.put("id", "1");
        map.put("appointmentDatetime", "2024-11-06T10:30:00");
        map.put("patientId", "101");
        map.put("doctorId", "202");
        map.put("officeId", "303");

        appointment.setUpWithMap(map);

        assertEquals(1, appointment.getId());
        assertEquals(LocalDateTime.of(2024, 11, 6, 10, 30), appointment.getAppointmentDatetime());
        assertEquals(101, appointment.getPatientId());
        assertEquals(202, appointment.getDoctorId());
        assertEquals(303, appointment.getOfficeId());
    }

    @Test
    void testSetUpWithMapEmptyData() {
        appointment.setUpWithMap(map);

        assertEquals(0, appointment.getId());
        assertNull(appointment.getAppointmentDatetime());
        assertEquals(0, appointment.getPatientId());
        assertEquals(0, appointment.getDoctorId());
        assertEquals(0, appointment.getOfficeId());
    }

    @Test
    void testSetUpWithMapInvalidInteger() {
        map.put("id", "invalid");

        assertThrows(NumberFormatException.class, () -> appointment.setUpWithMap(map));
    }

    @Test
    void testSetUpWithMapInvalidDateTime() {
        map.put("appointmentDatetime", "invalid_datetime");

        assertThrows(DateTimeParseException.class, () -> appointment.setUpWithMap(map));
    }
}
