package ru.lai.hw2_rest.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class Appointment {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentDatetime;
    private int patientId;
    private int doctorId;
    private int officeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getAppointmentDatetime() {
        return appointmentDatetime;
    }

    public void setAppointmentDatetime(LocalDateTime appointmentDatetime) {
        this.appointmentDatetime = appointmentDatetime;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public void setUpWithMap(Map<String, String> map) throws NumberFormatException, DateTimeParseException {
        this.setId(Integer.parseInt(map.get("id")));
        this.setAppointmentDatetime(LocalDateTime.parse(map.get("appointmentDatetime").replace("%3A", ":")));
        this.setPatientId(Integer.parseInt(map.get("patientId")));
        this.setDoctorId(Integer.parseInt(map.get("doctorId")));
        this.setOfficeId(Integer.parseInt(map.get("officeId")));
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointmentDatetime=" + appointmentDatetime +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", officeId=" + officeId +
                '}';
    }

}
