package com.medjournal.util;

import com.medjournal.model.Doctor;
import com.medjournal.model.Patient;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "root")
public class DataWrapper {

    private List<Patient> patients;
    private List<Doctor> doctors;

    public DataWrapper() {
    }

    public DataWrapper(List<Patient> patients, List<Doctor> doctors) {
        this.patients = patients;
        this.doctors = doctors;
    }

    @XmlElementWrapper(name = "patients")
    @XmlElement(name = "patient")
    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @XmlElementWrapper(name = "doctors")
    @XmlElement(name = "doctor")
    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }
}
