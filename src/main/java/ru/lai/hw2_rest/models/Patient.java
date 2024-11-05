package ru.lai.hw2_rest.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.Map;

public class Patient {
    private int id;
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String gender;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first_name) {
        this.firstName = first_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last_name) {
        this.lastName = last_name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date date_of_birth) {
        this.dateOfBirth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setUpWithMap(Map<String, String> map) throws IllegalArgumentException {
        this.setId(Integer.parseInt(map.get("id")));
        this.setFirstName(map.get("firstName"));
        this.setLastName(map.get("lastName"));
        this.setDateOfBirth(Date.valueOf(map.get("dateOfBirth")));
        this.setGender(map.get("gender"));
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                '}';
    }
}
