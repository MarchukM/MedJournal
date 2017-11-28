package com.medjournal.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

public class Doctor {
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty speciality;

    private List<WorkingDay> workingDays;

    public Doctor() {
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.speciality = new SimpleStringProperty();
        workingDays = new ArrayList<>();
    }

    public Doctor(String firstName, String lastName, String speciality) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.speciality = new SimpleStringProperty(speciality);
        workingDays = new ArrayList<>();
    }

    public StringProperty fullNameProperty() {
        return new SimpleStringProperty(firstName.getValue() + " " + lastName.getValue());
    }


    public List<WorkingDay> getWorkingDays() {
        return workingDays;
    }

    @XmlElementWrapper(name = "schedule")
    @XmlElement(name = "workingDay")
    public void setWorkingDays(List<WorkingDay> workingDays) {
        this.workingDays = workingDays;
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

    public String getSpeciality() {
        return speciality.getValue();
    }

    public StringProperty specialityProperty() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality.set(speciality);
    }
}
