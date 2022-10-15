package Controller;

import Model.Employee;
import View.tm.EmployeeTM;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.ArrayList;

public class AllEmployeeFormController {

    public TableColumn colName;
    public TableColumn colRoleId;
    public TableColumn colNIC;
    public TableColumn colAddress;
    public TableColumn colDescription;
    public TableColumn colCNumber;
    public TableColumn colSalary;
    public TableView <EmployeeTM>tblEmployee;


    public void initialize(){
        try {
            load_employee_details();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void load_employee_details() throws ClassNotFoundException, SQLException {

            PreparedStatement preparedStatement= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Employee$And$Role");
            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Employee>employees=new ArrayList<>();
                while (resultSet.next()){
                    employees.add(new Employee(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6),
                        resultSet.getDouble(7)
                    ));
            }

            setEmployeeToTable(employees);

            colRoleId.setCellValueFactory(new PropertyValueFactory<>("ID"));
            colName.setCellValueFactory(new PropertyValueFactory<>("name"));
            colNIC.setCellValueFactory(new PropertyValueFactory<>("NIC"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
            colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colCNumber.setCellValueFactory(new PropertyValueFactory<>("CNumber"));
            colSalary.setCellValueFactory(new PropertyValueFactory<>("BSalary"));
    }

    private void setEmployeeToTable(ArrayList<Employee> employees) {
        ObservableList<EmployeeTM>observableList= FXCollections.observableArrayList();
        employees.forEach(e->{
            observableList.add(new EmployeeTM(e.getRoleID(),e.getEmployeeName(),e.getEmployeeNIC(),
                    e.getEmployeeAddress(), e.getRoleDescription(),e.getContactNumber(),
                    e.getBasicSalary()));

        });
        tblEmployee.setItems(observableList);
    }

}
