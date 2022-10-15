package Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

public class AdministratorLoginFormController {
    public AnchorPane context_Admin;
    public JFXTextField txtAdminUserName;
    public JFXPasswordField txtAdminPassword;


    public void open_Account_Create(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/AccountCreatForm.fxml");
        Parent load = FXMLLoader.load(resource);
        context_Admin.getChildren().clear();
        context_Admin.getChildren().add(load);
    }

    public void open_PassWord_Reset_Form(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/PasswordResetForm.fxml");
        Parent load = FXMLLoader.load(resource);
        context_Admin.getChildren().clear();
        context_Admin.getChildren().add(load);
    }

    public void back_to_welcome_loginForm(MouseEvent mouseEvent) throws IOException {
        Stage window=(Stage)context_Admin.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/WelcomeForm.fxml"))));
    }

    public void openAdministratorDashboard_onAction(ActionEvent actionEvent) throws IOException {


        String UserName=txtAdminUserName.getText();
        String Password=txtAdminPassword.getText();

        try {
            Connection connection= DBConnection.getInstance().getConnection();

            Statement statement=connection.createStatement();
            String sql="SELECT * FROM Administrator WHERE AdminUserName = '"+UserName+"' and Password= '"+Password+"'";

            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                Stage window=(Stage)context_Admin.getScene().getWindow();
                window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/AdministratorDashboardForm.fxml"))));
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
                connection.close();
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

}
