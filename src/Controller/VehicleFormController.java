package Controller;

import Model.Employee;
import Model.Vehicle;
import Util.ValidationUtil;
import View.tm.EmployeeTM;
import View.tm.VehicleTM;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class VehicleFormController {
    public JFXTextField txtVNumber;
    public JFXTextField txtVType;
    public JFXTextField txtFType;
    public JFXTextField txtVColor;
    public JFXTextField txtSearchNumber;
    public TableView tblAllVehicle;
    public TableColumn colNumber;
    public TableColumn colType;
    public TableColumn colFuel;
    public TableColumn colColor;

    ////////////////////////

    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap();
    Pattern VNumber=Pattern.compile("^[A-z]+.[A-z]+.[0-9]{4}$");
    Pattern VType=Pattern.compile("^[A-z]{3,15}$");
    Pattern FType=Pattern.compile("^[A-z]{3,15}$");
    Pattern Colour=Pattern.compile("^[A-z]{3,15}$");
    // /////////////////////


    public void initialize(){
        saveValidation();
    }

    public void add_to_vehicle_database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        Vehicle vehicle1=new Vehicle(
                txtVNumber.getText(),txtVType.getText(),txtFType.getText(),txtVColor.getText()
        );

        if(saveVehicle(vehicle1)){
                new Alert(Alert.AlertType.INFORMATION,"Successfully Added").show();
        }else{
                new Alert(Alert.AlertType.ERROR,"Failed,\n Try Again").show();
        }


    }

    public void search_Vehicle_in_database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        String vNumber=txtSearchNumber.getText();
        Connection connection= DBConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Vehicle WHERE VehicleNumber= ?");

        preparedStatement.setObject(1,txtSearchNumber.getText());
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            Vehicle vehicle1=new Vehicle(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4));
            searchVehicleDetails(vehicle1);

        }else{
            new Alert(Alert.AlertType.ERROR,"Empty Result set").show();
        }
    }

    public void delete_Vehicle_details_database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement prepareStatement=DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Vehicle WHERE VehicleNumber=?");

        prepareStatement.setObject(1,txtSearchNumber.getText());

        if (prepareStatement.executeUpdate()>0){
            new Alert(Alert.AlertType.CONFIRMATION,"Deleted").show();
        }else {
            new Alert(Alert.AlertType.WARNING,"Try Again").show();
        }
    }

    public void update_Vehicle_details_database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
       Vehicle vehicle1=new Vehicle(
               txtVNumber.getText(),txtVType.getText(),txtFType.getText(),txtVColor.getText()
       );

       if (updateVehicle(vehicle1)){
           new Alert(Alert.AlertType.WARNING,"Updated").show();
       }else{
           new Alert(Alert.AlertType.ERROR,"Try Again").show();
       }

    }

    public void load_Vehicle_details(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle");
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Vehicle> vehicles=new ArrayList<>();
        while (resultSet.next()){
            vehicles.add(new Vehicle(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        setVehicleToTable(vehicles);

        colNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));
        colType.setCellValueFactory(new PropertyValueFactory<>("vehicleType"));
        colFuel.setCellValueFactory(new PropertyValueFactory<>("fuelType"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));


    }

    boolean  saveVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO Vehicle VALUE(?,?,?,?)");

        preparedStatement.setObject(1,txtVNumber.getText());
        preparedStatement.setObject(2,txtVType.getText());
        preparedStatement.setObject(3,txtFType.getText());
        preparedStatement.setObject(4,txtVColor.getText());

        return preparedStatement.executeUpdate()>0;
    }

    void searchVehicleDetails(Vehicle vehicle){
        txtVNumber.setText(vehicle.getVehicleNumber());
        txtVType.setText(vehicle.getVehicleType());
        txtFType.setText(vehicle.getFuelType());
        txtVColor.setText(vehicle.getColor());
    }

    boolean updateVehicle(Vehicle vehicle) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("UPDATE Vehicle SET VehicleType=?,FuelType=?,Color=? WHERE VehicleNumber=?");

        preparedStatement.setObject(1,vehicle.getVehicleType());
        preparedStatement.setObject(2,vehicle.getFuelType());
        preparedStatement.setObject(3,vehicle.getColor());
        preparedStatement.setObject(4,vehicle.getVehicleNumber());

        return preparedStatement.executeUpdate()>0;
    }

    private void setVehicleToTable(ArrayList<Vehicle> vehicles) {
        ObservableList<VehicleTM> observableList= FXCollections.observableArrayList();
        vehicles.forEach(e->{
            observableList.add(new VehicleTM(e.getVehicleNumber(),e.getVehicleType(),e.getFuelType(),
                    e.getColor()));

        });
        tblAllVehicle.setItems(observableList);
    }

    ///////////////////////////////////////////////////
    public List<String> getVehicleNumber() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Vehicle").executeQuery();

        List<String>Number=new ArrayList<>();
        while (resultSet.next()){
            Number.add(resultSet.getString(1));
        }
        return Number;
    }



    //////////////////////////////////////////////////
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
        map.put(txtVNumber,VNumber);
        map.put(txtVType,VType);
        map.put(txtFType,FType);
        map.put(txtVColor,Colour);
    }

    /////////////////////////////////////////////////////

}
