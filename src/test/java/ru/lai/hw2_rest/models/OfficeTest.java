package ru.lai.hw2_rest.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OfficeTest {
    private Office office;
    private Map<String, String> map;

    @BeforeEach
    void setup() {
        office = new Office();
        map = new HashMap<>();
    }

    @Test
    void testSetUpWithMapValidData() {
        map.put("id", "3");
        map.put("address", "Some address, 30");

        office.setUpWithMap(map);

        assertEquals(3, office.getId());
        assertEquals("Some address, 30", office.getAddress());
    }

    @Test
    void testSetUpWithMapEmptyData() {
        office.setUpWithMap(map);

        assertEquals(0, office.getId());
        assertNull(office.getAddress());
    }

    @Test
    void testSetUpWithMapInvalidInteger() {
        map.put("id", "g");

        assertThrows(NumberFormatException.class, () -> office.setUpWithMap(map));
    }

}