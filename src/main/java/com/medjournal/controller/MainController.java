package com.medjournal.controller;

import com.medjournal.MainApp;
import com.medjournal.controller.tab.DoctorsListController;
import com.medjournal.controller.tab.PatientListController;
import com.medjournal.threads.ReportBuilderThread;
import com.medjournal.threads.XMLInputThread;
import com.medjournal.util.XMLInputOutputUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class MainController {
    @FXML
    private DoctorsListController doctorsListController;
    @FXML
    private PatientListController patientListController;

    private MainApp mainApp;

    public void initialize() {
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        doctorsListController.setMainApp(mainApp);
        patientListController.setMainApp(mainApp);
    }

    @FXML
    private void handleExportToXML() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
                XMLInputOutputUtil.exportDataToXML(file);
            }
        }

    }

    @FXML
    private void handleImportFromXML() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            XMLInputThread xmlInputThread = new XMLInputThread(file);
            ReportBuilderThread reportBuilderThread = new ReportBuilderThread(file);
        }
    }

    @FXML
    private void handleExit() {
        System.exit(1);
    }

    @FXML
    private void handleAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("О Проекте");
        alert.setHeaderText("Курсовая работа по ООП");
        alert.setContentText("Выполнили: \nCтуденты группы 4892 \nМарчук М. \nКарабчевский А.");

        alert.showAndWait();
    }
}
