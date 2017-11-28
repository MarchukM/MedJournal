package com.medjournal.controller.dialog;

import com.medjournal.model.Doctor;
import com.medjournal.model.Note;
import com.medjournal.model.Patient;
import com.medjournal.stub.DataSource;
import com.medjournal.util.CheckUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;


public class PatientEditDialogController {

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private ChoiceBox<String> doctor;
    @FXML
    private TextField disease;
    @FXML
    private TextArea recommendations;


    private boolean okClicked;
    private Stage dialogStage;
    private Patient patient;
    private DataSource dataSource;
    private ObservableList<Doctor> doctors;

    public PatientEditDialogController() {
        this.dataSource = DataSource.getInstance();
        this.doctors = dataSource.getDoctors();
    }

    @FXML
    private void initialize() {
        setDoctorsChoiceBox();
    }

    private void setDoctorsChoiceBox() {
        List<String> docs = doctors.stream()
                .map(doc -> String.format("%s %s", doc.getFirstName(), doc.getLastName()))
                .collect(Collectors.toList());

        doctor.setItems(FXCollections.observableArrayList(docs));
    }

    @FXML
    private void handleOk() {

        if (CheckUtil.isAllTextFieldsAreNonEmpty(firstName, lastName, disease) &&
                !doctor.getValue().isEmpty() &&
                !recommendations.getText().isEmpty()) {

            patient.setFirstName(firstName.getText());
            patient.setLastName(lastName.getText());

            String doctorFirstName = doctor.getValue().split(" ")[0];
            String doctorLastName = doctor.getValue().split(" ")[1];

            patient.setDoctor(dataSource.findDoctor(doctorFirstName, doctorLastName));
            patient.setDoctorsNote(new Note(disease.getText(), recommendations.getText()));

            okClicked = true;
            dialogStage.close();
        } else {
            showEmptyFieldsAlert();
        }
    }

    private void showEmptyFieldsAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Пустые поля");
        alert.setContentText("Все поля должны быть заполнены.");
        alert.showAndWait();
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
