package ru.lai.hw2_rest.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DoctorTest {
    private Doctor doctor;
    private Map<String, String> map;

    @BeforeEach
    void setup() {
        doctor = new Doctor();
        map = new HashMap<>();
    }

    @Test
    void testSetUpWithMapValidData() {
        map.put("id", "10");
        map.put("firstName", "John");
        map.put("lastName", "Null");
        map.put("specialization", "Gynecologist");
        map.put("yearsOfExperience", "100");

        doctor.setUpWithMap(map);

        assertEquals(10, doctor.getId());
        assertEquals("John", doctor.getFirstName());
        assertEquals("Null", doctor.getLastName());
        assertEquals("Gynecologist", doctor.getSpecialization());
        assertEquals(100, doctor.getYearsOfExperience());
    }

    @Test
    void testSetUpWithMapEmptyData() {
        doctor.setUpWithMap(map);

        assertEquals(0, doctor.getId());
        assertNull(doctor.getFirstName());
        assertNull(doctor.getLastName());
        assertNull(doctor.getSpecialization());
        assertEquals(0, doctor.getYearsOfExperience());
    }

    @Test
    void testSetUpWithMapInvalidInteger() {
        map.put("id", "f");

        assertThrows(NumberFormatException.class, () -> doctor.setUpWithMap(map));
    }
}