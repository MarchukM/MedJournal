package com.medjournal.controller.tab;

import com.medjournal.MainApp;
import com.medjournal.model.Patient;
import com.medjournal.stub.DataSource;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class PatientListController {
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> firstName;
    @FXML
    private TableColumn<Patient, String> lastName;
    @FXML
    private TableColumn<Patient, String> doctor;
    @FXML
    private TableColumn<Patient, String> disease;

    private DataSource dataSource;
    private ObservableList<Patient> patients;
    private MainApp mainApp;

    public PatientListController() {
        this.dataSource = DataSource.getInstance();
        this.patients = dataSource.getPatients();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize() {
        firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        doctor.setCellValueFactory(cellData -> cellData.getValue().getDoctor().fullNameProperty());
        disease.setCellValueFactory(cellData -> cellData.getValue().getDoctorsNote().diseaseProperty());

        setPatientTable(patients);
    }

    private void setPatientTable(ObservableList<Patient> patients) {
        this.patientTable.setItems(patients);
    }

    @FXML
    private void handleNewPatient() {
        Patient tempPerson = new Patient();
        boolean okClicked = mainApp.showPatientEditDialog(tempPerson);
        if (okClicked) {
            dataSource.addPatient(tempPerson);
        }
    }

    @FXML
    private void handleDeletePatient() {
        Patient patient = patientTable.getSelectionModel().getSelectedItem();
        if (patient != null) {
            dataSource.removePatient(patient);
        } else {
            showAlert();
        }
    }

    @FXML
    private void handleShowNote() {
        Patient patient = patientTable.getSelectionModel().getSelectedItem();

        if(patient != null) {
            mainApp.showNote(patientTable.getSelectionModel().getSelectedItem());
        } else {
            showAlert();
        }
    }

    private void showAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Пациент не выбран");
        alert.setHeaderText("Пациент не выбран");
        alert.setContentText("Пожалуйста, выберите пациента из таблицы.");

        alert.showAndWait();
    }
}
