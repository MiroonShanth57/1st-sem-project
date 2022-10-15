package Controller;

import Model.*;
import View.tm.ChequeTM;
import View.tm.DeliveredTM;
import View.tm.PendingDeliveryTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.table.TableModel;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeliveryFormController {
    public TableView tblPendingDelivery;
    public TableColumn colValidateDate;
    public TableColumn colInvoiceNumber;
    public TableColumn colCustomerName;
    public TableColumn colAddress;
    public TableColumn colAmount;
    public JFXComboBox <String>cmbDeliveryManRoleId;
    public JFXComboBox <String>cmbVehicleNumber;



    public JFXDatePicker dateDeliveryDate;
    public Label lblInvoiceNumber;

    public TableView<DeliveredTM>tblDelivered;
    public TableColumn colDaliveredDate;
    public TableColumn colDInvoiceNumber;
    public TableColumn colDCustomerName;
    public TableColumn colDeliveryMan;
    public TableColumn colDAmount;
    public TableColumn colVehicleNumber;
    public JFXButton btnAdd;


    Double BillAmount;
    int tableSelectedRowAddAndRemove= -1;

    public void initialize() throws SQLException, ClassNotFoundException {
        loadDeliveredBillOrder();
        ////////////////////////////////
        loadPendingDelivery();
        //////////////////////////////////////////
        try {
            loadVehicleNumberToCombo();
            loadRoleIDToCombo();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ////////////////////////////////////////////////
        cmbDeliveryManRoleId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            try {
                loadRoleIDData(String.valueOf(newValue));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
            ///////////////////////////////////////////////////

        tblPendingDelivery.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->{
            tableSelectedRowAddAndRemove= (int) newValue;
        });
    }

    private void loadPendingDelivery() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM PendingDelivery");
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<PendingDelivery> pendingDeliveries=new ArrayList<>();
        while (resultSet.next()){
            pendingDeliveries.add(new PendingDelivery(
                    resultSet.getDate(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDouble(5)
            ));
        }

        setDeliveriesToTable(pendingDeliveries);

        colValidateDate.setCellValueFactory(new PropertyValueFactory<>("ODate"));
        colInvoiceNumber.setCellValueFactory(new PropertyValueFactory<>("INumber"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("CName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("CAddress"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("Cost"));
    }

    private void setDeliveriesToTable(ArrayList<PendingDelivery> pendingDeliveries) {
        ObservableList<PendingDeliveryTM> observableList= FXCollections.observableArrayList();
        pendingDeliveries.forEach(e->{
            observableList.add(new PendingDeliveryTM(e.getOrderDate(),e.getInvoiceNumber(),
                    e.getCustomerName(),e.getCustomerAddress(),e.getBillCost()));

        });
        tblPendingDelivery.setItems(observableList);
    }

    public void customerOrder_Deliver_To_Customer(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        Delivery delivery1=new Delivery(
                lblInvoiceNumber.getText(),
                cmbDeliveryManRoleId.getValue(),
                cmbVehicleNumber.getValue(),
                Date.valueOf(dateDeliveryDate.getValue()),
                BillAmount
        );

        if(saveTheDataInDatabase(delivery1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Order processing").show();
            loadDeliveredBillOrder();
            remove_the_DataFrom_Database();
            tblPendingDelivery.refresh();
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
        }
    }

    private void remove_the_DataFrom_Database() throws SQLException, ClassNotFoundException {
        PreparedStatement prepareStatement=DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM PendingDelivery WHERE InvoiceNumber=?");

        prepareStatement.setObject(1,lblInvoiceNumber.getText());
        prepareStatement.executeUpdate();

    }

    private boolean saveTheDataInDatabase(Delivery delivery) throws SQLException, ClassNotFoundException {
        Connection connection=DBConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Delivery VALUES (?,?,?,?,?)");

        preparedStatement.setObject(1,delivery.getInvoiceNumber());
        preparedStatement.setObject(2,delivery.getDeliveryStaff());
        preparedStatement.setObject(3,delivery.getVehicleNumber());
        preparedStatement.setObject(4,delivery.getDeliveryDate());
        preparedStatement.setObject(5,delivery.getBillAmount());

        return preparedStatement.executeUpdate()>0;
    }

    private void loadDeliveredBillOrder() throws SQLException, ClassNotFoundException {
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

        setDeliveredBillsToTable(deliveries);

        colDInvoiceNumber.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        colDeliveryMan.setCellValueFactory(new PropertyValueFactory<>("deliveryStaff"));
        colDaliveredDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colDAmount.setCellValueFactory(new PropertyValueFactory<>("billAmount"));
        colVehicleNumber.setCellValueFactory(new PropertyValueFactory<>("vehicleNumber"));



    }

    private void setDeliveredBillsToTable(ArrayList<Delivery> deliveries) {
        ObservableList<DeliveredTM> observableList= FXCollections.observableArrayList();
        deliveries.forEach(e->{
            observableList.add(new DeliveredTM(
                    e.getInvoiceNumber(),e.getDeliveryStaff(),e.getVehicleNumber(),e.getDeliveryDate(),e.getBillAmount()
            ));

        });
        tblDelivered.setItems(observableList);
    }

    ////////////////////////////////////////////////

    private void loadVehicleNumberToCombo() throws SQLException, ClassNotFoundException {
        List<String> vehicleNumber = new VehicleFormController().getVehicleNumber();
        cmbVehicleNumber.getItems().addAll(vehicleNumber);
    }

    ///////////////////////////////////////////////

    private void loadRoleIDToCombo() throws SQLException, ClassNotFoundException {
        List<String> roleID = new EmployeeFormController().getEmployeeID();
        cmbDeliveryManRoleId.getItems().addAll(roleID);
    }

    private void loadRoleIDData(String RoleID) throws SQLException, ClassNotFoundException {
        String roleID= String.valueOf(cmbDeliveryManRoleId.getValue());
        Connection connection= DBConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Employee$And$Role WHERE RoleID= ?");

        preparedStatement.setObject(1,cmbDeliveryManRoleId.getValue());
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            Employee employee=new Employee(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),
                    resultSet.getString(4),resultSet.getString(5),resultSet.getInt(6),resultSet.getDouble(7));
            setToTextFiledRoleIDDetails(employee);

        }else{
            new Alert(Alert.AlertType.ERROR,"Empty Result set").show();
        }
    }

    private void setToTextFiledRoleIDDetails(Employee employee1) {


    }

    //////////////////////////////////////////////

    public void Add_To_Delivery(MouseEvent mouseEvent) {
        if(tableSelectedRowAddAndRemove==-1){

           new Alert(Alert.AlertType.WARNING,"Please Select a row,\nBefore add to the delivery").show();
        }else{

            load_The_Row_INumber();
            System.out.println(BillAmount);
            tblPendingDelivery.refresh();
        }
    }

    private void load_The_Row_INumber(){
        PendingDeliveryTM pendingDelivery= (PendingDeliveryTM) tblPendingDelivery.getSelectionModel().getSelectedItem();
        String INumber=pendingDelivery.getINumber();
        Double Amount=pendingDelivery.getCost();
        lblInvoiceNumber.setText(INumber);
        BillAmount=Amount;
    }

    ////////////////////////////////////////////////////////////////////////


}
