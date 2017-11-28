package com.medjournal;

import com.medjournal.controller.MainController;
import com.medjournal.controller.dialog.NoteViewController;
import com.medjournal.controller.dialog.DoctorEditDialogController;
import com.medjournal.controller.dialog.PatientEditDialogController;
import com.medjournal.model.Doctor;
import com.medjournal.model.Patient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class MainApp extends Application {

    private static final Logger log = Logger.getLogger(MainApp.class);

    private Stage primaryStage;
    private Parent rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Application starts");

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

    }

    private void initRootLayout() {
        log.info("Initialization of root layout");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/MainPage.fxml"));
            rootLayout = loader.load();

            MainController controller = loader.getController();
            controller.setMainApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean showDoctorEditDialog(Doctor doctor) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/dialog/DoctorEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Doctor");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DoctorEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setDoctor(doctor);
            controller.setMainApp(this);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showPatientEditDialog(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/dialog/PatientEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Patient");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PatientEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPatient(patient);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showNote(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/dialog/NoteView.fxml"));
            AnchorPane view = loader.load();

            Stage viewStage = new Stage();
            viewStage.setTitle("Note");
            viewStage.initModality(Modality.WINDOW_MODAL);
            viewStage.initOwner(primaryStage);
            viewStage.setResizable(false);

            Scene scene = new Scene(view);
            viewStage.setScene(scene);

            NoteViewController controller = loader.getController();
            controller.setViewStage(viewStage);
            controller.setPatient(patient);

            viewStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}
