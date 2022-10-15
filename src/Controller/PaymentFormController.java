package Controller;

import Model.Delivery;
import Model.Payment;
import Util.ValidationUtil;
import View.tm.DeliveredTM;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentFormController {

    public TableColumn colINumber;
    public TableColumn colDDate;
    public TableColumn colDAmount;

    public TableView tablePayment;
    public Label lblINumber;
    public Label lblAmount;
    public JFXTextField txtCash;
    public JFXComboBox <String>cmbCNumber;
    public JFXComboBox <String>cmbCollector;
    public JFXCheckBox checkBox;
    public JFXToggleButton btnToggle;


    //////////////////////////////////////
    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap();
    Pattern Amount=Pattern.compile("^\\d{1,6}(?:\\.\\d{0,2})?$");
    /////////////////////////////////////

    int tableSelectedRowAddAndRemove= -1;
    public void initialize() throws SQLException, ClassNotFoundException {
        load_Unpaid_bills();
        ///////////////////////
        tablePayment.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            tableSelectedRowAddAndRemove= (int) newValue;
        });
        //////////////////////
        loadRoleIDToCombo();
        loadVehicleNumberToCombo();
        txtCash.setDisable(true);

        ///////////////////////
        saveValidation();

    }

    private void load_Unpaid_bills() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Delivery");
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Delivery> deliveries=new ArrayList<>();
        while (resultSet.next()){
            deliveries.add(new Delivery(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getDouble(5)

            ));
        }

        setUnpaidBillsToTable(deliveries);

        colDDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colINumber.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        colDAmount.setCellValueFactory(new PropertyValueFactory<>("billAmount"));

    }

    private void setUnpaidBillsToTable(ArrayList<Delivery> deliveries) {
        ObservableList<DeliveredTM> observableList= FXCollections.observableArrayList();
        deliveries.forEach(e->{
            observableList.add(new DeliveredTM(
                    e.getInvoiceNumber(),e.getDeliveryStaff(),e.getVehicleNumber(),e.getDeliveryDate(),e.getBillAmount()
            ));

        });
        tablePayment.setItems(observableList);
    }

    public void Save_the_data_in_database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Payment payment1=new Payment(
                lblINumber.getText(),
                Double.parseDouble(lblAmount.getText()),
                Double.parseDouble(txtCash.getText()),
                Integer.parseInt(cmbCNumber.getValue()),
                cmbCollector.getValue()
        );

        if (saveThePaidBillDetailsInDatabase(payment1)){
            new Alert(Alert.AlertType.CONFIRMATION,"The Bill Paid").show();
            remove_the_Data_From_database();
            tablePayment.refresh();
        }else{
            new Alert(Alert.AlertType.ERROR,"Payment failed").show();
        }
    }

    private boolean saveThePaidBillDetailsInDatabase(Payment payment) throws SQLException, ClassNotFoundException {

        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Payment VALUES (?,?,?,?,?)");

            preparedStatement.setObject(1,payment.getInvoiceNumber());
            preparedStatement.setObject(2,payment.getPaymentAmount());
            preparedStatement.setObject(3,payment.getCash());
            preparedStatement.setObject(4,payment.getChequeNumber());
            preparedStatement.setObject(5,payment.getPaymentCollector());

            return preparedStatement.executeUpdate()>0;
    }

    private void load_The_Row_INumber() {
        DeliveredTM deliveredTM= (DeliveredTM) tablePayment.getSelectionModel().getSelectedItem();
        String INumber=deliveredTM.getInvoiceNumber();
        Double Amount=deliveredTM.getBillAmount();
        lblINumber.setText(INumber);
        lblAmount.setText(String.valueOf(Amount));
    }

    public void make_A_Paid(MouseEvent mouseEvent) {
        if(tableSelectedRowAddAndRemove==-1){

            new Alert(Alert.AlertType.WARNING,"Please Select a row,\nBefore add to the delivery").show();
        }else{
            load_The_Row_INumber();
        }
    }

    public void remove_the_Data_From_database() throws SQLException, ClassNotFoundException {
        PreparedStatement prepareStatement=DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM Delivery WHERE InvoiceNumber=?");

        prepareStatement.setObject(1,lblINumber.getText());
        prepareStatement.executeUpdate();
    }

    //////////////////////////////////////////////////////////
    private void loadRoleIDToCombo() throws SQLException, ClassNotFoundException {
        List<String> RoleID = new EmployeeFormController().getEmployeeID();
        cmbCollector.getItems().addAll(RoleID);
    }

    private void loadVehicleNumberToCombo() throws SQLException, ClassNotFoundException {
        List<String> ChequeNumber = new ChequeFormController().getChequeNumber();
        cmbCNumber.getItems().addAll(ChequeNumber);
    }

    public void save_ChequeNumber(MouseEvent swipeEvent) {
        txtCash.setDisable(false);

    }

    
    /////////////////////////////////////////////////////////
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
        map.put(txtCash,Amount);
    }
}
