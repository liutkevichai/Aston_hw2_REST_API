package ru.lai.hw2_rest.models;

import java.util.Map;

public class Office {
    int id;
    String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUpWithMap(Map<String, String> map) throws IllegalArgumentException {
        this.setId(Integer.parseInt(map.get("id")));
        this.setAddress(map.get("address").replace("%20", ","));
    }

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", address='" + address + '\'' +
                '}';
    }
}
