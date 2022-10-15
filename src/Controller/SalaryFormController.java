package Controller;

import Model.Employee;
import Model.Salary;
import Model.Supplier;
import Util.ValidationUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SalaryFormController {
    public JFXComboBox<String> cmb_RolID;
    public Label lblEName;
    public Label lblBasicSalary;
    public Label lblEDescription;
    public JFXTextField txtAdvance;
    public JFXTextField txtIncrement;
    public JFXTextField txtDeduction;
    public Label lblFinalSalary;

    //////////////////////////////////////////////
    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap();

    Pattern Salary=Pattern.compile("^\\d{0,8}(\\.\\d{1,4})?$");

    /////////////////////////////////////////////


    public void initialize(){

      cmb_RolID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
          try {
              loadEmployeeData(newValue);
          } catch (SQLException throwables) {
              throwables.printStackTrace();
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
          }
      });

      ///////////////////////////
      try {
          loadEmployeeIDToCombo();
      } catch (SQLException throwables) {
          throwables.printStackTrace();
      } catch (ClassNotFoundException e) {
          e.printStackTrace();
      }
/////////////////////////////
        saveValidation();
  }

    public void Add_to_salary_details_database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
      String roleId=cmb_RolID.getValue();
      double basic=Double.parseDouble(lblBasicSalary.getText());
      double advance=0+Double.parseDouble(txtAdvance.getText());
      double increment=0+Double.parseDouble(txtIncrement.getText());
      double deduction=0+Double.parseDouble(txtDeduction.getText());
      double monthSalary;
      monthSalary=(basic+advance+increment)-deduction;

        Salary salary1=new Salary(
                cmb_RolID.getValue(),Double.parseDouble(lblBasicSalary.getText()),
                Double.parseDouble(txtAdvance.getText()),Double.parseDouble(txtIncrement.getText()),
                Double.parseDouble(txtDeduction.getText()),monthSalary);

        if (saveSalaryDetails(salary1)){
            new Alert(Alert.AlertType.INFORMATION,"Added").show();
            lblFinalSalary.setText(String.valueOf(monthSalary));
        }else{
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }


  }

    boolean saveSalaryDetails(Salary salary) throws SQLException, ClassNotFoundException {

      Connection connection=DBConnection.getInstance().getConnection();
      PreparedStatement preparedStatement= connection.prepareStatement("INSERT INTO Salary VALUE(?,?,?,?,?,?)");

        preparedStatement.setObject(1,salary.getRoleID());
        preparedStatement.setObject(2,salary.getBasicSalary());
        preparedStatement.setObject(3,salary.getMonthlyAdvance());
        preparedStatement.setObject(4,salary.getSalaryIncrement());
        preparedStatement.setObject(5,salary.getSalaryCut());
        preparedStatement.setObject(6,salary.getFinalSalary());

        return preparedStatement.executeUpdate()>0;
  }

    //////////////////////////////////////////
    private void loadEmployeeData(String employeeRoleId) throws SQLException, ClassNotFoundException {
        //String vNumber=cmb_RolID.getValue();
        Connection connection= DBConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Employee$And$Role WHERE RoleID= ?");

        preparedStatement.setObject(1,cmb_RolID.getValue());
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            Employee employee1=new Employee(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6),resultSet.getDouble(7));
            setToTextFiledEmployeeData(employee1);

        }else{
            new Alert(Alert.AlertType.ERROR,"Empty Result set").show();
        }
    }

    private void setToTextFiledEmployeeData(Employee employee1) {
        lblBasicSalary.setText(String.valueOf(employee1.getBasicSalary()));
        lblEDescription.setText(employee1.getRoleDescription());
        lblEName.setText(employee1.getEmployeeName());
    }

    private void loadEmployeeIDToCombo() throws SQLException, ClassNotFoundException {
        List<String> employeeID = new EmployeeFormController().getEmployeeID();
        cmb_RolID.getItems().addAll(employeeID);
    }

    /////////////////////////////////////////

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
        map.put(txtAdvance,Salary);
        map.put(txtIncrement,Salary);
        map.put(txtDeduction,Salary);
    }
}
