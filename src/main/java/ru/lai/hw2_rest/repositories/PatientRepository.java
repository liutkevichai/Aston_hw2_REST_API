package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.models.Patient;

public class PatientRepository extends Repository<Patient> {

    public PatientRepository() {
        super(Patient.class);
    }

}
