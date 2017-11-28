package com.medjournal.controller.dialog;

import com.medjournal.MainApp;
import com.medjournal.exception.EmptyFieldsException;
import com.medjournal.exception.IllegalTimeFormatException;
import com.medjournal.model.Doctor;
import com.medjournal.model.WorkingDay;
import com.medjournal.util.CheckUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;


public class DoctorEditDialogController {

    private static final Logger log = Logger.getLogger(DoctorEditDialogController.class);

    @FXML
    private TextField firstName, lastName, speciality;

    @FXML
    private TextField mondayFrom, mondayTo,
            tuesdayFrom, tuesdayTo,
            wednesdayFrom, wednesdayTo,
            thursdayFrom, thursdayTo,
            fridayFrom, fridayTo,
            saturdayFrom, saturdayTo,
            sundayFrom, sundayTo;

    @FXML
    private TextField mondayRoom,
            tuesdayRoom,
            wednesdayRoom,
            thursdayRoom,
            fridayRoom,
            saturdayRoom,
            sundayRoom;

    private boolean okClicked = false;
    private Stage dialogStage;
    private Doctor doctor;
    private MainApp mainApp;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;

        firstName.setText(doctor.getFirstName());
        lastName.setText(doctor.getLastName());
        speciality.setText(doctor.getSpeciality());

        if (doctor.getWorkingDays() == null) {
            return;
        }

        for (WorkingDay day : doctor.getWorkingDays()) {
            if (day == null) {
                continue;
            }

            switch (day.getDayOfWeek()) {
                case MONDAY:
                    setWorkingDayParams(day, mondayFrom, mondayTo, mondayRoom);
                    break;
                case TUESDAY:
                    setWorkingDayParams(day, tuesdayFrom, tuesdayTo, tuesdayRoom);
                    break;
                case WEDNESDAY:
                    setWorkingDayParams(day, wednesdayFrom, wednesdayTo, wednesdayRoom);
                    break;
                case THURSDAY:
                    setWorkingDayParams(day, thursdayFrom, thursdayTo, thursdayRoom);
                    break;
                case FRIDAY:
                    setWorkingDayParams(day, fridayFrom, fridayTo, fridayRoom);
                    break;
                case SATURDAY:
                    setWorkingDayParams(day, saturdayFrom, saturdayTo, saturdayRoom);
                    break;
                case SUNDAY:
                    setWorkingDayParams(day, sundayFrom, sundayTo, sundayRoom);
                    break;
            }
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML
    private void handleOk() {
        boolean everythingOk = true;

        try {
            doctor.setFirstName(firstName.getText());
            doctor.setLastName(lastName.getText());
            doctor.setSpeciality(speciality.getText());

            List<WorkingDay> workingDayList = new ArrayList<>();
            workingDayList.add(createWorkingDay(DayOfWeek.MONDAY, mondayFrom, mondayTo, mondayRoom));
            workingDayList.add(createWorkingDay(DayOfWeek.TUESDAY, tuesdayFrom, tuesdayTo, tuesdayRoom));
            workingDayList.add(createWorkingDay(DayOfWeek.WEDNESDAY, wednesdayFrom, wednesdayTo, wednesdayRoom));
            workingDayList.add(createWorkingDay(DayOfWeek.THURSDAY, thursdayFrom, thursdayTo, thursdayRoom));
            workingDayList.add(createWorkingDay(DayOfWeek.FRIDAY, fridayFrom, fridayTo, fridayRoom));
            workingDayList.add(createWorkingDay(DayOfWeek.SATURDAY, saturdayFrom, saturdayTo, saturdayRoom));
            workingDayList.add(createWorkingDay(DayOfWeek.SUNDAY, sundayFrom, sundayTo, sundayRoom));
            doctor.setWorkingDays(workingDayList);

        } catch (IllegalTimeFormatException e) {
            log.error("Wrong time format: ", e);
            everythingOk = false;
            showTimeFormatAlert();

        } catch (EmptyFieldsException e) {
            log.error("Empty fields error: ", e);
            everythingOk = false;
            showEmptyFieldsAlert();
        }

        if (everythingOk) {
            okClicked = true;
            dialogStage.close();
        }
    }

    private WorkingDay createWorkingDay(DayOfWeek dayOfWeek, TextField startTime, TextField endTime, TextField room)
            throws IllegalTimeFormatException, EmptyFieldsException {
        WorkingDay workingDay = new WorkingDay();

        if (CheckUtil.isTextFieldsAreEmpty(startTime, endTime, room)) {
            return null;
        } else if (!CheckUtil.isAllTextFieldsAreNonEmpty(startTime, endTime, room)) {
            throw new EmptyFieldsException();
        } else {
            workingDay.setDayOfWeek(dayOfWeek);
            workingDay.setStartTime(startTime.getText());
            workingDay.setEndTime(endTime.getText());
            workingDay.setRoom(room.getText());
        }

        return workingDay;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    private void setWorkingDayParams(WorkingDay day, TextField from, TextField to, TextField room) {
        from.setText(day.getStartTime());
        to.setText(day.getEndTime());
        room.setText(day.getRoom());
    }

    private void showTimeFormatAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Формат времени");
        alert.setHeaderText("Неправильный формат времени.");
        alert.setContentText("Пожалуйста, введите время начала и конца рабочего дня в правильном формате hh:mm.");

        alert.showAndWait();
    }

    private void showEmptyFieldsAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Пустые поля");
        alert.setHeaderText("Пустыe поля при заполнении графика.");
        alert.setContentText("Какой-то из рабочих дней заполнен не полностью. Поля должны оставаться пустыми, либо быть заполнены полностью.");

        alert.showAndWait();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
