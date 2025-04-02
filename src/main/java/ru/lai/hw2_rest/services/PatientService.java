package ru.lai.hw2_rest.services;

import org.springframework.stereotype.Service;
import ru.lai.hw2_rest.models.Patient;
import ru.lai.hw2_rest.repositories.PatientRepository;

@Service
public class PatientService extends AbstractServiceImpl<Patient> {

    public PatientService(PatientRepository repository) {
        super(repository);
    }
}
