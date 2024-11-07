package ru.lai.hw2_rest.models;

import java.util.Map;

public class StaffAssignment {
    private int id;
    private int doctorId;
    private int officeId;


    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getDoctorId() { return doctorId; }

    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    public int getOfficeId() { return officeId; }

    public void setOfficeId(int officeId) { this.officeId = officeId; }

    public void setUpWithMap(Map<String, String> map) throws NumberFormatException {

        String idStr = map.get("id");
        if (idStr == null || idStr.isEmpty()) {
            this.setId(0);
        } else {
            this.setId(Integer.parseInt(idStr));
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
        return "StaffAssignment{" +
                "id=" + id +
                ", doctorId=" + doctorId +
                ", officeId=" + officeId +
                '}';
    }
}
