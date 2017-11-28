package com.medjournal.controller.dialog;

import com.medjournal.model.Patient;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class NoteViewController {
    @FXML
    Label patientName;
    @FXML
    Label doctorName;
    @FXML
    Label disease;
    @FXML
    Label recommendation;

    private Stage viewStage;
    private boolean okClicked = false;

    @FXML
    private void initialization() {
    }

    @FXML
    private void handleOkClicked(){
        okClicked = true;
        viewStage.close();
    }

    public void setViewStage(Stage viewStage) {
        this.viewStage = viewStage;
    }

    public void setPatient(Patient patient) {
            patientName.setText(String.format("%s %s", patient.getFirstName(), patient.getLastName()));
            doctorName.setText(String.format("%s %s", patient.getDoctor().getFirstName(), patient.getDoctor().getLastName()));
            disease.setText(patient.getDoctorsNote().getDisease());
            recommendation.setText(patient.getDoctorsNote().getRecommendation());
    }

    public boolean isOkClicked() {
        return okClicked;
    }
}
