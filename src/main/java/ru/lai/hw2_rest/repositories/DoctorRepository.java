package ru.lai.hw2_rest.repositories;

import ru.lai.hw2_rest.models.Doctor;

public class DoctorRepository extends Repository<Doctor> {

    public DoctorRepository(){
        super(Doctor.class);
    }

}
