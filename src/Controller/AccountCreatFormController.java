package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sun.invoke.empty.Empty;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class AccountCreatFormController {
    public AnchorPane AccountCreate_Context;
    public JFXTextField txtAdminName;
    public JFXTextField txtAdminDateOfBirth;
    public JFXTextField txtAdminNIC;
    public JFXTextField txtAdminCNumber;
    public JFXTextField txtAdminEmail;
    public JFXTextField txtAdminUserName;
    public JFXTextField txtAdminPassword;
    public JFXButton btn_Creat;

    public void initialize(){ }

    public void back_to_AdministratorLoginForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/AdministratorLoginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AccountCreate_Context.getChildren().clear();
        AccountCreate_Context.getChildren().add(load);

    }

    public void create_Admin_Account_in_DataBases(MouseEvent mouseEvent)  {

        String AdminName= txtAdminName.getText();
        String AdminDateOfBirth= txtAdminDateOfBirth.getText();
        String AdminNIC=txtAdminNIC.getText();
        int ContactNumber=Integer.parseInt(txtAdminCNumber.getText());
        String AdminEmail=txtAdminEmail.getText();
        String AdminUserName=txtAdminUserName.getText();
        String Password=txtAdminPassword.getText();

        try {

            Connection connection=DBConnection.getInstance().getConnection();

            Statement statement = connection.createStatement();
            String sql = "INSERT INTO Administrator VALUE("+"'"+AdminName+"'"+","+"'"+AdminDateOfBirth+"'"+","+"'"+AdminNIC+"'"+","+"'"+ContactNumber+"'"+","+"'"+AdminEmail+"'"+","+"'"+AdminUserName+"'"+","+"'"+Password+"'"+")";

            int i = statement.executeUpdate(sql);

            if(i>0){

                new Alert(Alert.AlertType.CONFIRMATION,"Has been successfully saved").show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }

    public void validationPart(KeyEvent keyEvent) {
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////////////////////////////////
}
