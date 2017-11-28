package com.medjournal.controller.tab;

import com.medjournal.MainApp;
import com.medjournal.model.Doctor;
import com.medjournal.model.WorkingDay;
import com.medjournal.stub.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorsListController {

    private static final String ALL = "Все";

    @FXML
    private TableView<Doctor> doctorTable;
    @FXML
    private TableColumn<Doctor, String> firstNameColumn;
    @FXML
    private TableColumn<Doctor, String> lastNameColumn;
    @FXML
    private ChoiceBox<String> specialities;

    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label speciality;
    @FXML
    private Label mondayTime, tuesdayTime, wednesdayTime, thursdayTime, fridayTime, saturdayTime, sundayTime;
    @FXML
    private Label mondayRoom, tuesdayRoom, wednesdayRoom, thursdayRoom, fridayRoom, saturdayRoom, sundayRoom;

    private DataSource dataSource;
    private ObservableList<Doctor> doctors;

    private MainApp mainApp;

    public DoctorsListController() {
        dataSource = DataSource.getInstance();
        doctors = dataSource.getDoctors();
    }

    @FXML
    public void initialize() {
        initSpecialities();

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        setDoctorTable(doctors);

        showDoctorDetails(null);

        specialities.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDoctorsTable(newValue));

        doctorTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDoctorDetails(newValue));
    }

    @FXML
    private void handleNewDoctor() {
        Doctor tempPerson = new Doctor();
        boolean okClicked = mainApp.showDoctorEditDialog(tempPerson);
        if (okClicked) {
            dataSource.addDoctor(tempPerson);
            initSpecialities();
        }
    }

    @FXML
    private void handleDeletePerson() {
        Doctor selectedPerson = doctorTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            dataSource.removeDoctor(selectedPerson);
            initSpecialities();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleEditDoctor() {
        Doctor selectedPerson = doctorTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showDoctorEditDialog(selectedPerson);
            if (okClicked) {
                showDoctorDetails(selectedPerson);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Доктор не выбран");
            alert.setHeaderText("Доктор не выбран");
            alert.setContentText("Пожалуйста, выберите доктора из таблицы.");

            alert.showAndWait();
        }
    }

    private void initSpecialities() {
        List<String> list = doctors.stream().map(Doctor::getSpeciality).distinct().collect(Collectors.toList());
        list.add(ALL);
        specialities.setItems(FXCollections.observableArrayList(list));
        specialities.setValue(ALL);
    }

    private void showDoctorsTable(String newValue) {
        if (StringUtils.isEmpty(newValue)) {
            return;
        }

        if (newValue.equals(ALL)) {
            setDoctorTable(doctors);
        } else {
            ObservableList<Doctor> newDocs = FXCollections.observableArrayList(doctors.stream()
                    .filter(doc -> doc.getSpeciality().equals(newValue))
                    .collect(Collectors.toList()));
            setDoctorTable(newDocs);
        }

        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void setDoctorTable(ObservableList<Doctor> docs) {
        this.doctorTable.setItems(docs);
    }

    private void showDoctorDetails(Doctor doctor) {
        if (doctor != null) {
            firstName.setText(doctor.getFirstName());
            lastName.setText(doctor.getLastName());
            speciality.setText(doctor.getSpeciality());
            setSchedule(doctor);
        } else {
            firstName.setText("-");
            lastName.setText("-");
            speciality.setText("-");
        }
    }

    private void cleanSchedule() {
        mondayTime.setText("-");
        tuesdayTime.setText("-");
        wednesdayTime.setText("-");
        thursdayTime.setText("-");
        fridayTime.setText("-");
        saturdayTime.setText("-");
        sundayTime.setText("-");

        mondayRoom.setText("-");
        tuesdayRoom.setText("-");
        wednesdayRoom.setText("-");
        thursdayRoom.setText("-");
        fridayRoom.setText("-");
        saturdayRoom.setText("-");
        sundayRoom.setText("-");
    }

    private void setScheduleLabels(WorkingDay day, Label time, Label room) {
        time.setText(String.format("%s-%s", day.getStartTime(), day.getEndTime()));
        room.setText(day.getRoom());
    }

    private void setSchedule(Doctor doctor) {
        cleanSchedule();

        if (doctor.getWorkingDays() == null) {
            return;
        }

        for (WorkingDay day : doctor.getWorkingDays()) {
            if (day == null) {
                continue;
            }

            switch (day.getDayOfWeek()) {
                case MONDAY:
                    setScheduleLabels(day, mondayTime, mondayRoom);
                    break;
                case TUESDAY:
                    setScheduleLabels(day, tuesdayTime, tuesdayRoom);
                    break;
                case WEDNESDAY:
                    setScheduleLabels(day, wednesdayTime, wednesdayRoom);
                    break;
                case THURSDAY:
                    setScheduleLabels(day, thursdayTime, thursdayRoom);
                    break;
                case FRIDAY:
                    setScheduleLabels(day, fridayTime, fridayRoom);
                    break;
                case SATURDAY:
                    setScheduleLabels(day, saturdayTime, saturdayRoom);
                    break;
                case SUNDAY:
                    setScheduleLabels(day, sundayTime, sundayRoom);
                    break;
            }
        }
    }
}
