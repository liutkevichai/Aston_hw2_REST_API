CREATE TYPE genders AS ENUM ('Female', 'Male');

CREATE TABLE Doctors
(
    id                  SERIAL PRIMARY KEY,
    first_name          VARCHAR(60)  NOT NULL,
    last_name           VARCHAR(50)  NOT NULL,
    specialization      VARCHAR(100) NOT NULL,
    years_of_experience INTEGER      NOT NULL DEFAULT 0
);

CREATE TABLE Patients
(
    id            SERIAL PRIMARY KEY,
    first_name    VARCHAR(60) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    gender        genders     NOT NULL
);

CREATE TABLE Offices
(
    id      SERIAL PRIMARY KEY,
    address VARCHAR(200)
);

CREATE TABLE Appointments
(
    id                   SERIAL PRIMARY KEY,
    appointment_datetime TIMESTAMP WITH TIME ZONE,
    patient_id           INTEGER NOT NULL REFERENCES Patients (id) ON DELETE CASCADE ON UPDATE CASCADE,
    doctor_id            INTEGER NOT NULL REFERENCES Doctors (id) ON DELETE CASCADE ON UPDATE CASCADE,
    office_id            INTEGER NOT NULL REFERENCES Offices (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Doctor_Office
(
    id        SERIAL PRIMARY KEY,
    doctor_id INTEGER NOT NULL REFERENCES Doctors (id) ON DELETE CASCADE ON UPDATE CASCADE,
    office_id INTEGER NOT NULL REFERENCES Offices (id) ON DELETE CASCADE ON UPDATE CASCADE
)