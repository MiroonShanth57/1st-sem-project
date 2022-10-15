package Controller;

import Model.Employee;
import Model.SystemUser;
import Util.ValidationUtil;
import View.tm.EmployeeTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import db.DBConnection;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeFormController {
    public JFXTextField txtEName;
    public JFXTextField txtEAddress;
    public JFXTextField txtECNumber;
    public JFXTextField txtENIC;
    public JFXTextField txtESalary;
    public JFXTextField txtERoleID;
    public JFXTextField txtEDescription;
    public JFXTextField txtRoleIDForSearch;
    public JFXTextField txtUserName;
    public JFXTextField txtPassword;
    public JFXButton btnSystemUser;
    public JFXToggleButton toggle;


    /////////////////////////////////////////////////////////////////////
    LinkedHashMap<TextField,Pattern> map=new LinkedHashMap();
    Pattern name=Pattern.compile("^[A-z]{3,26}$");
    //Pattern DateOfBirth=Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
    Pattern NIC=Pattern.compile("^[0-9]{11,}([X,V])?$");
    Pattern address=Pattern.compile("^[A-Z\\a-z0-9'\\.\\-\\s\\,]+$");
    Pattern roleID=Pattern.compile("^(RI-)[0-9]{1,2}$");
    Pattern roleDescription=Pattern.compile("^[A-z]{3,26}$");
    Pattern salary=Pattern.compile("^\\d{1,6}(?:\\.\\d{0,2})?$");
    Pattern CNumber=Pattern.compile("^(077|071|078|075|072|070|074|076|051|052)(-)?[0-9]{7}$");
    Pattern UserName=Pattern.compile("^(?=.{8,20})(?![_.])(?!.*[.]{2})[a-zA-Z0-9._]+(?<![.])$");
    Pattern Password=Pattern.compile("^(?=.*[\\w])(?=.*[\\W])[\\w\\W]{8,}$");

    ///////////////////////////////////////////////////////////////////////////////////////

    public void initialize(){
        btnSystemUser.setDisable(true);
        toggle.setDisable(true);
        txtUserName.setDisable(true);
        txtPassword.setDisable(true);

        ////////////////////////////////////////////////////////

        saveValidation();

    }



    public void Add_Employee_to_Database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {


            Employee employee1= new Employee(txtERoleID.getText(), txtEName.getText(), txtENIC.getText(),
                    txtEAddress.getText(), txtEDescription.getText(), Integer.parseInt(txtECNumber.getText()),
                    Double.parseDouble(txtESalary.getText())
            );

            if(saveEmployee(employee1)){
                new Alert(Alert.AlertType.INFORMATION,"Successfully Appointed").show();
                toggle.setDisable(false);
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed,\n Try Again").show();
            }


    }

    public void search_Employees_in_database(MouseEvent mouseEvent) throws ClassNotFoundException, SQLException {

            String EmployeeID=txtRoleIDForSearch.getText();
            Connection connection= DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Employee$And$Role WHERE RoleID= ?");

            preparedStatement.setObject(1,txtRoleIDForSearch.getText());
            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                Employee employee1=new Employee(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3), resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6),resultSet.getDouble(7));
                setData(employee1);

            }else {
                new Alert(Alert.AlertType.ERROR, "Empty Result set").show();
            }

        ///////////////////////////////////////////////////////////////

        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM System$User WHERE RoleID= ?");


        preparedStatement1.setObject(1,txtRoleIDForSearch.getText());
        ResultSet resultSet1=preparedStatement1.executeQuery();

        if(resultSet1.next()){
            SystemUser systemUser1=new SystemUser(resultSet1.getString(1),resultSet1.getString(2),resultSet1.getString(3));
            setDataTOSystem(systemUser1);
        }else{
        }


    }

    public void update_Employee_details_database(MouseEvent mouseEvent) throws ClassNotFoundException, SQLException {

        Employee employee1=new Employee(txtERoleID.getText(),txtEName.getText(),txtENIC.getText(),
                    txtEAddress.getText(),txtEDescription.getText(),Integer.parseInt(txtECNumber.getText()),Double.parseDouble(txtESalary.getText()));

         if (updateEmployeeDetails(employee1)) {
             new Alert(Alert.AlertType.CONFIRMATION, "Updated..").show();
         }else{
             new Alert(Alert.AlertType.WARNING,"Try Again").show();
         }

    }

    public void Delete_Employee_details_database(MouseEvent mouseEvent) throws ClassNotFoundException, SQLException {

        PreparedStatement prepareStatement=DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Employee$And$Role WHERE RoleID=?");

            prepareStatement.setObject(1,txtERoleID.getText());

                if (prepareStatement.executeUpdate()>0){
                    new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
                }else {
                    new Alert(Alert.AlertType.WARNING,"Try Again").show();
                }
   ///////////////////////////////////////////////////////////////////////////////////
        PreparedStatement prepareStatement1=DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM System$User WHERE RoleID=?");

        prepareStatement1.setObject(1,txtERoleID.getText());
        prepareStatement1.executeUpdate();

    }

    public void open_all_employee_details_Form(MouseEvent mouseEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/AllEmployeeForm.fxml"));
        Parent parent = loader.load();
        Scene scene= new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    boolean saveEmployee(Employee employee) throws SQLException, ClassNotFoundException {
        Connection connection= DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO Employee$And$Role VALUE(?,?,?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setObject(1,employee.getRoleID());
        preparedStatement.setObject(2,employee.getEmployeeName());
        preparedStatement.setObject(3,employee.getEmployeeNIC());
        preparedStatement.setObject(4,employee.getEmployeeAddress());
        preparedStatement.setObject(5,employee.getRoleDescription());
        preparedStatement.setObject(6,employee.getContactNumber());
        preparedStatement.setObject(7,employee.getBasicSalary());

        return preparedStatement.executeUpdate()>0;
    }

    void setData(Employee employee){
        txtERoleID.setText(employee.getRoleID());
        txtEName.setText(employee.getEmployeeName());
        txtENIC.setText(employee.getEmployeeNIC());
        txtEAddress.setText(employee.getEmployeeAddress());
        txtEDescription.setText(employee.getRoleDescription());
        txtECNumber.setText(String.valueOf(employee.getContactNumber()));
        txtESalary.setText(String.valueOf(employee.getBasicSalary()));
    }

    void setDataTOSystem(SystemUser systemUser) {
        txtUserName.setText(systemUser.getUserName());
        txtPassword.setText(systemUser.getPassword());
    }

    boolean updateEmployeeDetails(Employee employee) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();


        String query = "UPDATE Employee$And$Role SET EmployeeName=?,EmployeeNIC=?,EmployeeAddress=?,RoleDescription=?,ContuctNumber=?,BasicSalary=? WHERE RoleID=?";

        PreparedStatement preparedStatement=connection.prepareStatement(query);

        preparedStatement.setObject(1,employee.getEmployeeName());
        preparedStatement.setObject(2,employee.getEmployeeNIC());
        preparedStatement.setObject(3,employee.getEmployeeAddress());
        preparedStatement.setObject(4,employee.getRoleDescription());
        preparedStatement.setObject(5,employee.getContactNumber());
        preparedStatement.setObject(6,employee.getBasicSalary());
        preparedStatement.setObject(7,employee.getRoleID());

        return preparedStatement.executeUpdate()>0;
    }

    public void give_a_space_to_make_user(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        SystemUser systemUser1=new SystemUser(
                txtERoleID.getText(),
                txtUserName.getText(),
                txtPassword.getText()
        );
        
        if(saveTheSystemUserInDatabase(systemUser1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully System User Appointed").show();
        }else {
            new Alert(Alert.AlertType.ERROR,"Failed to save").show();
        }
        
    }

    private boolean saveTheSystemUserInDatabase(SystemUser systemUser) throws SQLException, ClassNotFoundException {
        
        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO System$User VALUE (?,?,?)");

        preparedStatement.setObject(1,systemUser.getRoleID());
        preparedStatement.setObject(2,systemUser.getUserName());
        preparedStatement.setObject(3,systemUser.getPassword());

        return preparedStatement.executeUpdate()>0;


    }

    ///////////////////////////////////////////
    public List<String>getEmployeeID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee$And$Role").executeQuery();

        List<String>Id=new ArrayList<>();
        while (resultSet.next()){
            Id.add(resultSet.getString(1));
        }
        return Id;
    }

    //////////////////////////////////////////

    public void make_UsernameAndPassword(MouseEvent mouseEvent) {

        btnSystemUser.setDisable(false);
        txtUserName.setDisable(false);
        txtPassword.setDisable(false);
    }


    ///////////////////////////////////////////////////////////
    public void Validation_Part(KeyEvent keyEvent) {

        Object output = ValidationUtil.validate(map);
        if (keyEvent.getCode()== KeyCode.ENTER) {
            if(output instanceof TextField){
                TextField errorText= (TextField) output;
                errorText.requestFocus();
            }else if(output instanceof Boolean){
                //new Alert(Alert.AlertType.CONFIRMATION,"Successfully Added").show();
                System.out.println(output);
            }
        }
    }

    private void saveValidation() {
        map.put(txtEName,name);
        map.put(txtENIC,NIC);
        map.put(txtEAddress,address);
        map.put(txtERoleID,roleID);
        map.put(txtEDescription,roleDescription);
        map.put(txtESalary,salary);
        map.put(txtECNumber,CNumber);
        map.put(txtUserName,UserName);
        map.put(txtPassword,Password);



    }

    /////////////////////////////////////////////////////////////

}
