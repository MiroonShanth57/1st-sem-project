package Util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {



    public static Object validate(LinkedHashMap<TextField, Pattern> map) {

        for (TextField textField : map.keySet()) {
            Pattern pattern = map.get(textField);

            if (!pattern.matcher(textField.getText()).matches()) {
                textField.getParent().setStyle("-fx-border-color: red");
                textField.getParent().setStyle("-fx-background-radius:13px");
                return textField;
            }
            textField.getParent().setStyle("-fx-background-color: #b1eeb1");

        }
        return true;
    }
}
