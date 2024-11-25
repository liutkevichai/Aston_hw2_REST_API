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

        String idStr = map.get("id");
        if (idStr == null || idStr.isEmpty()) {
            this.setId(0);
        } else {
            this.setId(Integer.parseInt(idStr));
        }

        String dateTimeStr = map.get("appointmentDatetime");
        if (dateTimeStr == null || dateTimeStr.isEmpty()) {
            this.setAppointmentDatetime(null);
        } else {
            this.setAppointmentDatetime(LocalDateTime.parse(dateTimeStr.replace("%3A", ":")));
        }

        String patientIdStr = map.get("patientId");
        if (patientIdStr == null || patientIdStr.isEmpty()) {
            this.setPatientId(0);
        } else {
            this.setPatientId(Integer.parseInt(patientIdStr));
        }

        String doctorIdStr = map.get("doctorId");
        if (doctorIdStr == null || doctorIdStr.isEmpty()) {
            this.setDoctorId(0);
        } else {
            this.setDoctorId(Integer.parseInt(doctorIdStr));
        }

        String officeIdStr = map.get("officeId");
        if (officeIdStr == null || officeIdStr.isEmpty()) {
            this.setOfficeId(0);
        } else {
            this.setOfficeId(Integer.parseInt(officeIdStr));
        }
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
