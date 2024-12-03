package ru.lai.hw2_rest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lai.hw2_rest.models.Patient;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
