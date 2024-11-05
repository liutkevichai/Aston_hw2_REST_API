package ru.lai.hw2_rest.models;

import java.util.Map;

public class Doctor {
    private int id;
    private String firstName;
    private String lastName;
    private String specialization;
    private int yearsOfExperience;

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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int years_of_experience) {
        this.yearsOfExperience = years_of_experience;
    }

    public void setUpWithMap(Map<String, String> map) throws NumberFormatException {
        this.setId(Integer.parseInt(map.get("id")));
        this.setFirstName(map.get("firstName"));
        this.setLastName(map.get("lastName"));
        this.setSpecialization(map.get("specialization"));
        this.setYearsOfExperience(Integer.parseInt(map.get("yearsOfExperience")));
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                '}';
    }
}
