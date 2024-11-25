package ru.lai.hw2_rest.models;

import java.util.Map;

public class Office {
    private int id;
    private String address;

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

    public void setUpWithMap(Map<String, String> map) throws NumberFormatException {

        String idStr = map.get("id");
        if (idStr == null || idStr.isEmpty()) {
            this.setId(0);
        } else {
            this.setId(Integer.parseInt(idStr));
        }

        String addressStr = map.get("address");
        if (addressStr == null || addressStr.isEmpty()) {
            this.setAddress(null);
        } else {
            this.setAddress(addressStr.replace("%2C", ","));
        }
    }

    @Override
    public String toString() {
        return "Office{" +
                "id=" + id +
                ", address='" + address + '\'' +
                '}';
    }
}
