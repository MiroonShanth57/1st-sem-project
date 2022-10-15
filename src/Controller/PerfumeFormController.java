package Controller;

import Model.Perfume;
import Model.Supplier;
import Model.Vehicle;
import Util.ValidationUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class PerfumeFormController {
    public JFXTextField txtPCode;
    public JFXComboBox<String>cmbSupplierID;
    public JFXTextField txtSName;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtPName;
    public JFXTextField txtPQuantity;
    public JFXTextField txtSellingPrice;
    public JFXTextField txtMRPPrice;


    //////////////////////////////////////////
    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap();
    Pattern code=Pattern.compile("^(IC-)[0-9]{1,3}$");
    Pattern name=Pattern.compile("^[A-z]{3,26}$");
    Pattern qty=Pattern.compile("^[0-9]+$");
    Pattern price=Pattern.compile("^\\d{0,8}(\\.\\d{1,4})?$");
    //////////////////////////////////////////


    public void initialize(){

        cmbSupplierID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            try {
                loadSupplierData(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        ////////////////////////
        try {
            loadSupplierIDToCombo();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //////////////////////////
        saveValidation();

    }

    public void Add_Perfume_InTo_Database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        Perfume perfume1=new Perfume(
                txtPCode.getText(),txtPName.getText(),Integer.parseInt(txtPQuantity.getText()),
                Double.parseDouble(txtSellingPrice.getText()),Double.parseDouble(txtUnitPrice.getText()),Double.parseDouble(txtMRPPrice.getText()),cmbSupplierID.getValue()
        );

        if(savePerfumeDatabase(perfume1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Added").show();
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
        }
    }

    boolean savePerfumeDatabase(Perfume perfume) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();

        PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO Perfume VALUE(?,?,?,?,?,?,?)");

        preparedStatement.setObject(1,perfume.getPerfumeCode());
        preparedStatement.setObject(2,perfume.getNameOfPerfume());
        preparedStatement.setObject(3,perfume.getQuantity());
        preparedStatement.setObject(4,perfume.getDistributePrice());
        preparedStatement.setObject(5,perfume.getUnitPrice());
        preparedStatement.setObject(6,perfume.getMRPPrice());
        preparedStatement.setObject(7,perfume.getSupplierID());

        return preparedStatement.executeUpdate()>0;
    }

    public void Open_Stock_Details(MouseEvent mouseEvent) {
    }
    /////////////////////////////////////////////////////////

    public List<String> getPerfumeCode() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Perfume").executeQuery();

        List<String>PCode=new ArrayList<>();
        while (resultSet.next()){
            PCode.add(resultSet.getString(1));
        }
        return PCode;
    }

    ///////////////////////////////////////////////////
    private void loadSupplierIDToCombo() throws SQLException, ClassNotFoundException {
        List<String> supplierId = new SupplierFormController().getSupplierID();
        cmbSupplierID.getItems().addAll(supplierId);
    }

    private void loadSupplierData(String supplierID) throws SQLException, ClassNotFoundException {
        String vNumber=cmbSupplierID.getValue();
        Connection connection= DBConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Supplier WHERE SupplierID= ?");

        preparedStatement.setObject(1,cmbSupplierID.getValue());
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            Supplier supplier1=new Supplier(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4),resultSet.getInt(5));
            setToTextFiledSupplierDetails(supplier1);

        }else{
            new Alert(Alert.AlertType.ERROR,"Empty Result set").show();
        }
    }

    private void setToTextFiledSupplierDetails(Supplier supplier1) {
        txtSName.setText(supplier1.getName());
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
        map.put(txtPCode,code);
        map.put(txtPName,name);
        map.put(txtPQuantity,qty);
        map.put(txtSellingPrice,price);
        map.put(txtMRPPrice,price);
        map.put(txtUnitPrice,price);

    }

    public void OpenStock(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/StockForm.fxml"));
        Parent parent = loader.load();
        Scene scene= new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
