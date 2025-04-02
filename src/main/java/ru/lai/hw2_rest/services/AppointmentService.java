package ru.lai.hw2_rest.services;

import org.springframework.stereotype.Service;
import ru.lai.hw2_rest.models.Appointment;
import ru.lai.hw2_rest.repositories.AppointmentRepository;

@Service
public class AppointmentService extends AbstractServiceImpl<Appointment> {

    public AppointmentService(AppointmentRepository repository) {
        super(repository);
    }
}