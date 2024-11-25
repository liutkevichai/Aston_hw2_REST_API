package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.models.Appointment;

public class AppointmentRepository extends Repository<Appointment> {

    public AppointmentRepository() {
        super(Appointment.class);
    }
}
