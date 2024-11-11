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

        String idStr = map.get("id");
        if (idStr == null || idStr.isEmpty()) {
            this.setId(0);
        } else {
            this.setId(Integer.parseInt(idStr));
        }

        this.setFirstName(map.get("firstName"));
        this.setLastName(map.get("lastName"));
        this.setSpecialization(map.get("specialization"));

        String yearsOfExperienceStr = map.get("yearsOfExperience");
        if (yearsOfExperienceStr == null || yearsOfExperienceStr.isEmpty()) {
            this.setYearsOfExperience(0);
        } else {
            this.setYearsOfExperience(Integer.parseInt(yearsOfExperienceStr));
        }

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
