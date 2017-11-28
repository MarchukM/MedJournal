package com.medjournal.stub;

import com.medjournal.model.Doctor;
import com.medjournal.model.Patient;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DataSourceTest {

    private DataSource dataSource = DataSource.getInstance();

    @Test
    public void testDataSourceNotNull() {
        assertNotNull(dataSource);
    }

    @Test
    public void testDataSourceHasNotEmptyPatientList() {
        List<Patient> patientList = dataSource.getPatients();

        assertNotNull(patientList);
        assertTrue(patientList.size() > 0);
    }

    @Test
    public void testDataSourceHasNotEmptyDoctorsList() {
        List<Doctor> doctorList = dataSource.getDoctors();

        assertNotNull(doctorList);
        assertTrue(doctorList.size() > 0);
    }

    @Test
    public void testFindDoctor() {
        Doctor doctor = new Doctor("Test", "Test", "Test");
        dataSource.addDoctor(doctor);
        Doctor searchResult = dataSource.findDoctor("Test", "Test");

        assertNotNull(searchResult);
        assertTrue(doctor.getFirstName().equals("Test"));
        assertTrue(doctor.getLastName().equals("Test"));
    }
}
