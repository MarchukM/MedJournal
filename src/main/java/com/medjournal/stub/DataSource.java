package com.medjournal.stub;

import com.medjournal.exception.IllegalTimeFormatException;
import com.medjournal.model.Doctor;
import com.medjournal.model.Note;
import com.medjournal.model.Patient;
import com.medjournal.model.WorkingDay;
import com.medjournal.util.DataWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataSource {
    private static DataSource instance;
    private ObservableList<Doctor> doctors = FXCollections.observableArrayList();
    private ObservableList<Patient> patients = FXCollections.observableArrayList();


    private DataSource() {
        doctors.add(new Doctor("Иван", "Петров", "Окулист"));
        doctors.add(new Doctor("Петр", "Петров", "Дерматолог"));
        doctors.add(new Doctor("Евгений", "Сорокин", "Окулист"));
        doctors.add(new Doctor("Павел", "Генадьев", "Дерматолог"));
        doctors.add(new Doctor("Иоган", "Петров", "Онколог"));
        doctors.add(new Doctor("Тест", "Тестович", "Генеколог"));
        doctors.add(new Doctor("Иоган", "Петров", "Терапевт"));
        doctors.add(new Doctor("Иоган", "Петров", "Терапевт"));

        Doctor doc1 = new Doctor("Максим", "Марчук", "Онколог");
        Doctor doc2 = new Doctor("Василий", "Власов", "Онколог");

        try {
            doc1.setWorkingDays(Arrays.asList(
                    new WorkingDay(DayOfWeek.MONDAY, "08:00", "12:00", "101"),
                    new WorkingDay(DayOfWeek.TUESDAY, "08:00", "12:00", "101"),
                    new WorkingDay(DayOfWeek.WEDNESDAY, "08:00", "12:00", "101"),
                    new WorkingDay(DayOfWeek.FRIDAY, "08:00", "12:00", "101")
            ));
            doc2.setWorkingDays(Arrays.asList(
                    new WorkingDay(DayOfWeek.MONDAY, "08:00", "18:00", "101"),
                    new WorkingDay(DayOfWeek.TUESDAY, "08:00", "18:00", "101"),
                    new WorkingDay(DayOfWeek.WEDNESDAY, "08:00", "18:00", "101"),
                    new WorkingDay(DayOfWeek.FRIDAY, "08:00", "18:00", "101"),
                    new WorkingDay(DayOfWeek.SATURDAY, "08:00", "18:00", "101")
            ));
        } catch (IllegalTimeFormatException e) {
            e.printStackTrace();
        }

        doctors.add(doc1);
        doctors.add(doc2);

        Patient p1 = new Patient("Валентина", "Ивановна");
        p1.setDoctor(doctors.get(1));
        p1.setDoctorsNote(new Note("Волчанка", "Пить больше воды"));

        Patient p2 = new Patient("Джон", "Доу");
        p2.setDoctor(doctors.get(1));
        p2.setDoctorsNote(new Note("Ветрянка", "Мазаться зеленкой"));

        Patient p3 = new Patient("Анастасия", "Волочкова");
        p3.setDoctor(doctors.get(1));
        p3.setDoctorsNote(new Note("ОРВИ", "Тантум верде форте"));

        Patient p4 = new Patient("Индиана", "Джонс");
        p4.setDoctor(doctors.get(1));
        p4.setDoctorsNote(new Note("Сальманелез", "Целебная клизма"));

        Patient p5 = new Patient("Индиана", "Джонс");
        p5.setDoctor(doctors.get(1));
        p5.setDoctorsNote(new Note("Сальманелез", "Тантум верде форте"));

        Patient p6 = new Patient("Индиана", "Джонс");
        p6.setDoctor(doctors.get(1));
        p6.setDoctorsNote(new Note("Сальманелез", "Иглоукалывание"));

        Patient p7 = new Patient("Индиана", "Джонс");
        p7.setDoctor(doctors.get(1));
        p7.setDoctorsNote(new Note("ОРВИ", "Тантум верде форте"));

        patients.add(p1);
        patients.add(p2);
        patients.add(p3);
        patients.add(p4);
        patients.add(p5);
        patients.add(p6);
        patients.add(p7);
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public ObservableList<Patient> getPatients() {
        return patients;
    }

    public ObservableList<Doctor> getDoctors() {
        return doctors;
    }

    public boolean addDoctor(Doctor doctor) {
        return doctors.add(doctor);
    }

    public boolean addPatient(Patient patient) {
        return patients.add(patient);
    }

    public Doctor findDoctor(String firstName, String lastName) {
        return doctors.stream()
                .filter(doc -> doc.getFirstName().equals(firstName) && doc.getLastName().equals(lastName))
                .findFirst()
                .get();
    }

    public List<Patient> findPatient(String firstName, String lastName) {
        return patients.stream()
                .filter(patient -> patient.getFirstName().equals(firstName) && patient.getLastName().equals(lastName))
                .collect(Collectors.toList());
    }

    public void clearData() {
        doctors.clear();
        patients.clear();
    }

    public void setData(DataWrapper data) {
        doctors.addAll(data.getDoctors());
        patients.addAll(data.getPatients());
    }

    public boolean removeDoctor(Doctor doctor) {
        return doctors.remove(doctor);
    }

    public boolean removePatient(Patient patient) {
        return patients.remove(patient);
    }
}
