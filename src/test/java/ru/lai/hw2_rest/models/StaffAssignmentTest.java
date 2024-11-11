package ru.lai.hw2_rest.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StaffAssignmentTest {
    private StaffAssignment assignment;
    private Map<String, String> map;

    @BeforeEach
    void setup() {
        assignment = new StaffAssignment();
        map = new HashMap<>();
    }

    @Test
    void testSetUpWithMapValidData() {
        map.put("id", "10");
        map.put("doctorId", "15");
        map.put("officeId", "1");

        assignment.setUpWithMap(map);

        assertEquals(10, assignment.getId());
        assertEquals(15, assignment.getDoctorId());
        assertEquals(1, assignment.getOfficeId());
    }

    @Test
    void testSetUpWithMapEmptyData() {
        assignment.setUpWithMap(map);

        assertEquals(0, assignment.getId());
        assertEquals(0, assignment.getDoctorId());
        assertEquals(0, assignment.getOfficeId());
    }

    @Test
    void testSetUpWithMapInvalidInteger() {
        map.put("id", "d");

        assertThrows(NumberFormatException.class, () -> assignment.setUpWithMap(map));
    }

}