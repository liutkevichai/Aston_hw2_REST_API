package ru.lai.hw2_rest.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {
    private Patient patient;
    private Map<String, String> map;

    @BeforeEach
    void setup() {
        patient = new Patient();
        map = new HashMap<>();
    }

    @Test
    void testSetUpWithMapValidData() {
        map.put("id", "400");
        map.put("firstName", "Jack");
        map.put("lastName", "Zero");
        map.put("dateOfBirth", "1980-10-30");
        map.put("gender", "Male");

        patient.setUpWithMap(map);

        assertEquals(400, patient.getId());
        assertEquals("Jack", patient.getFirstName());
        assertEquals("Zero", patient.getLastName());
        assertEquals(Date.valueOf("1980-10-30"), patient.getDateOfBirth());
        assertEquals("Male", patient.getGender());
    }

    @Test
    void testSetUpWithMapEmptyData() {
        patient.setUpWithMap(map);

        assertEquals(0, patient.getId());
        assertNull(patient.getFirstName());
        assertNull(patient.getLastName());
        assertNull(patient.getDateOfBirth());
        assertNull(patient.getGender());
    }

    @Test
    void testSetUpWithMapInvalidInteger() {
        map.put("id", "40u0");

        assertThrows(NumberFormatException.class, () -> patient.setUpWithMap(map));
    }

    @Test
    void testSetUpWithMapInvalidDate() {
        map.put("dateOfBirth", "invalid_date");

        assertThrows(IllegalArgumentException.class, () -> patient.setUpWithMap(map));
    }

}