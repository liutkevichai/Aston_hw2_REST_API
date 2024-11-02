INSERT INTO Offices (address)
VALUES ('123 Main St, Suite 100'),
       ('456 Elm St, Suite 200'),
       ('789 Maple Ave, Suite 300');

INSERT INTO Doctors (first_name, last_name, specialization, years_of_experience)
VALUES ('John', 'Doe', 'Cardiology', 10),
       ('Jane', 'Smith', 'Dermatology', 8),
       ('Michael', 'Brown', 'Neurology', 12),
       ('Emily', 'Davis', 'Orthopedics', 6),
       ('James', 'Miller', 'Pediatrics', 15),
       ('Sarah', 'Wilson', 'Psychiatry', 5),
       ('William', 'Moore', 'Endocrinology', 20),
       ('Jessica', 'Taylor', 'Gastroenterology', 7),
       ('Daniel', 'Anderson', 'Oncology', 9),
       ('Emma', 'Thomas', 'Rheumatology', 11),
       ('David', 'Jackson', 'Allergy', 4),
       ('Sophia', 'White', 'Ophthalmology', 14),
       ('Matthew', 'Harris', 'Surgery', 3),
       ('Olivia', 'Martin', 'Gynecology', 6),
       ('Joshua', 'Thompson', 'Anesthesiology', 13);

INSERT INTO Patients (first_name, last_name, date_of_birth, gender)
VALUES ('Alice', 'Johnson', '1985-02-20', 'Female'),
       ('Bob', 'Williams', '1978-11-05', 'Male'),
       ('Carol', 'Jones', '1990-06-15', 'Female'),
       ('Dave', 'Brown', '1992-01-22', 'Male'),
       ('Eve', 'Davis', '1988-09-14', 'Female'),
       ('Frank', 'Miller', '1983-04-11', 'Male'),
       ('Grace', 'Wilson', '1975-07-19', 'Female'),
       ('Hank', 'Moore', '1995-12-30', 'Male'),
       ('Ivy', 'Taylor', '1981-03-17', 'Female'),
       ('Jack', 'Anderson', '1993-09-25', 'Male'),
       ('Kathy', 'Thomas', '1984-02-10', 'Female'),
       ('Leo', 'Jackson', '1979-11-21', 'Male'),
       ('Mia', 'White', '1987-08-14', 'Female'),
       ('Nate', 'Harris', '1982-10-09', 'Male'),
       ('Olivia', 'Martin', '1994-06-29', 'Female'),
       ('Paul', 'Clark', '1991-07-15', 'Male'),
       ('Quinn', 'Lewis', '1986-12-05', 'Female'),
       ('Ray', 'Walker', '1980-01-18', 'Male'),
       ('Sophia', 'Hall', '1996-11-12', 'Female'),
       ('Tom', 'Allen', '1989-05-06', 'Male'),
       ('Uma', 'Young', '1983-03-03', 'Female'),
       ('Victor', 'King', '1977-07-07', 'Male'),
       ('Wendy', 'Scott', '1992-04-22', 'Female'),
       ('Xander', 'Green', '1985-10-13', 'Male'),
       ('Yara', 'Adams', '1993-06-02', 'Female'),
       ('Zane', 'Baker', '1991-08-20', 'Male'),
       ('Anna', 'Gonzalez', '1995-07-30', 'Female'),
       ('Brian', 'Nelson', '1988-05-21', 'Male'),
       ('Cindy', 'Carter', '1982-09-12', 'Female'),
       ('Derek', 'Mitchell', '1984-04-16', 'Male'),
       ('Ella', 'Perez', '1986-01-27', 'Female'),
       ('Finn', 'Roberts', '1980-02-23', 'Male'),
       ('Gina', 'Phillips', '1993-10-30', 'Female'),
       ('Hector', 'Evans', '1990-07-04', 'Male'),
       ('Isla', 'Turner', '1976-09-16', 'Female'),
       ('Jorge', 'Torres', '1985-11-09', 'Male'),
       ('Karen', 'Parker', '1994-12-24', 'Female'),
       ('Liam', 'Collins', '1981-03-15', 'Male'),
       ('Mona', 'Edwards', '1987-06-18', 'Female'),
       ('Nick', 'Stewart', '1983-08-22', 'Male'),
       ('Olga', 'Sanchez', '1982-05-27', 'Female'),
       ('Pete', 'Morris', '1978-04-03', 'Male'),
       ('Rita', 'Rogers', '1991-10-20', 'Female'),
       ('Sam', 'Reed', '1989-02-08', 'Male'),
       ('Tina', 'Cook', '1984-09-14', 'Female'),
       ('Ulysses', 'Morgan', '1980-12-11', 'Male'),
       ('Vera', 'Bell', '1979-06-06', 'Female'),
       ('Will', 'Murphy', '1988-07-25', 'Male'),
       ('Xenia', 'Bailey', '1992-01-10', 'Female'),
       ('Yusuf', 'Cooper', '1996-02-03', 'Male'),
       ('Zara', 'Clark', '2000-04-18', 'Female');

DO
$$
BEGIN
FOR i IN 1..70 LOOP
        INSERT INTO Appointments (appointment_datetime, patient_id, doctor_id, office_id)
        VALUES (
            TIMESTAMP '2024-11-01 08:00:00' + (random() * (TIMESTAMP '2024-12-31 17:00:00' - TIMESTAMP '2024-11-01 08:00:00')),
            (SELECT id FROM Patients ORDER BY random() LIMIT 1),
            (SELECT id FROM Doctors ORDER BY random() LIMIT 1),
            (SELECT id FROM Offices ORDER BY random() LIMIT 1)
        );
END LOOP;
END
$$;
