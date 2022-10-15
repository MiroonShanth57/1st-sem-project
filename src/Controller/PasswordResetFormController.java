package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class PasswordResetFormController {

    public AnchorPane Password_Reset_Context;

    public void back_to_AdministratorLoginForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/AdministratorLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        Password_Reset_Context.getChildren().clear();
        Password_Reset_Context.getChildren().add(load);
    }
}
