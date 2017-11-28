package com.medjournal.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Patient {
    private StringProperty firstName;
    private StringProperty lastName;
    private Doctor doctor;
    private Note doctorsNote;

    public Patient() {
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
    }

    public Patient(String firstName, String lastName) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
    }

    public String getFirstName() {
        return firstName.getValue();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.getValue();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Note getDoctorsNote() {
        return doctorsNote;
    }

    public void setDoctorsNote(Note doctorsNote) {
        this.doctorsNote = doctorsNote;
    }
}
