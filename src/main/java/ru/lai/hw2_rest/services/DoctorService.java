package ru.lai.hw2_rest.services;

import org.springframework.stereotype.Service;
import ru.lai.hw2_rest.models.Doctor;
import ru.lai.hw2_rest.repositories.DoctorRepository;

@Service
public class DoctorService extends AbstractServiceImpl<Doctor> {

    public DoctorService(DoctorRepository repository) {
        super(repository);
    }
}