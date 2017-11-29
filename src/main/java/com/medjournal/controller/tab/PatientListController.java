package com.medjournal.controller.tab;

import com.medjournal.MainApp;
import com.medjournal.model.Patient;
import com.medjournal.stub.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;


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
    @FXML
    private TextField searchForm;

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
        initData();
    }

    private void setPatientTable(ObservableList<Patient> patients) {
        this.patientTable.setItems(patients);
    }

    private void initData() {
        firstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        doctor.setCellValueFactory(cellData -> cellData.getValue().getDoctor().fullNameProperty());
        disease.setCellValueFactory(cellData -> cellData.getValue().getDoctorsNote().diseaseProperty());
        setPatientTable(patients);
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
    private void handleSearch() {
        String fullName;
        if (searchForm.getText() != null && !searchForm.getText().isEmpty()) {
            fullName = searchForm.getText();

            String firstName;
            String lastName;

            if (fullName != null && fullName.contains(" ")) {
                firstName = fullName.split(" ")[0];
                lastName = fullName.split(" ")[1];
                List<Patient> searchResult = dataSource.findPatient(firstName, lastName);
                patients = FXCollections.observableArrayList(searchResult);
            } else {
                showNotFoundAlert();
                patients = FXCollections.observableArrayList(dataSource.getPatients());
            }


        } else {
            patients = FXCollections.observableArrayList(dataSource.getPatients());
        }

        initData();

    }

    private void showNotFoundAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Ничего нет");
        alert.setContentText("Искомый пациент не найден");

        alert.showAndWait();
    }

    @FXML
    private void handleShowNote() {
        Patient patient = patientTable.getSelectionModel().getSelectedItem();

        if (patient != null) {
            mainApp.showNote(patientTable.getSelectionModel().getSelectedItem());
        } else {
            showAlert();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Пациент не выбран");
        alert.setHeaderText("Пациент не выбран");
        alert.setContentText("Пожалуйста, выберите пациента из таблицы.");

        alert.showAndWait();
    }
}
