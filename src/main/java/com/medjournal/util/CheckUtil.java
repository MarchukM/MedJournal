package com.medjournal.util;

import javafx.scene.control.TextField;

public class CheckUtil {

    public static boolean isTextFieldsAreEmpty(TextField... textField) {
        boolean isEmpty = true;
        for (TextField text : textField) {
            if (text != null && !text.getText().isEmpty()) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    public static boolean isAllTextFieldsAreNonEmpty(TextField... textFields) {
        boolean isAllNonEmpty = true;
        for (TextField text : textFields) {
            if (text == null || text.getText().isEmpty()) {
                isAllNonEmpty = false;
            }
        }
        return isAllNonEmpty;
    }
}
