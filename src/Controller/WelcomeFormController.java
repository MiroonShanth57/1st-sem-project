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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WelcomeFormController {

    public AnchorPane mainLogInAnchorPaneContext;
    public JFXTextField txtRoleID;
    public JFXTextField txtUserNAme;
    public JFXPasswordField txtPassword;

    public void open_AdministratorLogin(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/AdministratorLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        mainLogInAnchorPaneContext.getChildren().clear();
        mainLogInAnchorPaneContext.getChildren().add(load);
    }

    public void openManagerDashboard_onAction(ActionEvent actionEvent) throws IOException {


        String RoleID=txtRoleID.getText();
        String UserName=txtUserNAme.getText();
        String Password=txtPassword.getText();

        try {
            Connection connection= DBConnection.getInstance().getConnection();

            Statement statement=connection.createStatement();
            String sql="SELECT * FROM System$User WHERE RoleID = '"+RoleID+"' and UserName= '"+UserName+"' and Password= '"+Password+"'";

            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                Stage window=(Stage)mainLogInAnchorPaneContext.getScene().getWindow();
                window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/ManagerDashboardForm.fxml"))));
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
                connection.close();
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}
