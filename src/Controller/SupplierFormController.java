package Controller;

import Model.Supplier;
import Util.ValidationUtil;
import View.tm.SupplierTM;
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

public class SupplierFormController {


    public JFXTextField txtSName;
    public JFXTextField txtSAddress;
    public JFXTextField txtSEmail;
    public JFXTextField txtCNumber;
    public JFXTextField txtSID;
    public TableView tblSupplier;
    public TableColumn colID;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colCNumber;
    public TableColumn colEmailID;
    public TableColumn colDelete;


    ///////////////////////////
    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap();
    Pattern ID=Pattern.compile("^(SI-)[0-9]{1,2}$");
    Pattern name=Pattern.compile("^[A-z]{3,15}$");
    Pattern Address=Pattern.compile("^[A-z]{3,25}$");
    Pattern CNumber=Pattern.compile("^(077|071|078|075|072|070|074|076|051|052)(-)?[0-9]{7}$");
    Pattern Email=Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    ///////////////////////////

    public void initialize(){
        saveValidation();
    }

    public void Add_Supplier_To_database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {




        Supplier supplier1=new Supplier(txtSID.getText(),
                txtSName.getText(),
                txtSAddress.getText(),
                txtSEmail.getText(),
                Integer.parseInt(txtCNumber.getText())
        );

        if(saveSupplier(supplier1)){
                new Alert(Alert.AlertType.INFORMATION,"Successfully Added").show();
            }else{
                new Alert(Alert.AlertType.ERROR,"Failed,\n Try Again").show();
            }

    }



    public Supplier load_Supplier_details(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        PreparedStatement preparedStatement=DBConnection.getInstance().getConnection().
                prepareStatement("SELECT * FROM Supplier");

        ResultSet resultSet = preparedStatement.executeQuery();

        ObservableList<SupplierTM> observableList= FXCollections.observableArrayList();

            while (resultSet.next()) {
                observableList.add(new SupplierTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
            tblSupplier.setItems(observableList);

            colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            colAddress.setCellValueFactory(new PropertyValueFactory<>("Address"));
            colEmailID.setCellValueFactory(new PropertyValueFactory<>("Email"));
            colCNumber.setCellValueFactory(new PropertyValueFactory<>("CNumber"));

        return null;
    }

    boolean saveSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();
        String sql="INSERT INTO Supplier VALUE(?,?,?,?,?)";

        PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setObject(1,supplier.getID());
        preparedStatement.setObject(2,supplier.getName());
        preparedStatement.setObject(3,supplier.getAddress());
        preparedStatement.setObject(4,supplier.getEmail());
        preparedStatement.setObject(5,supplier.getCNumber());
        return preparedStatement.executeUpdate()>0;

    }

    ///////////////////////////////////////////////////////////////

    public List<String> getSupplierID() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Supplier").executeQuery();

        List<String>Id=new ArrayList<>();
        while (resultSet.next()){
            Id.add(resultSet.getString(1));
        }
        return Id;
    }



    ///////////////////////////////////////////////////////////////
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
        map.put(txtSID,ID);
        map.put(txtSName,name);
        map.put(txtSAddress,Address);
        map.put(txtCNumber,CNumber);
        map.put(txtSEmail,Email);
    }

}
