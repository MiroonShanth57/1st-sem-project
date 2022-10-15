package Controller;

import Model.*;
import Util.ValidationUtil;
import View.tm.CartTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;


public class InvoiceFormController {

    public Label lblInvoiceNumber;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXDatePicker dateInvoicedDate;
    public JFXButton btnValidate;

    public JFXComboBox <String>cmbPerfumeCode;
    public Label lblPName;
    public Label lblPrice;
    public JFXTextField txtForSale;
    public Label lblQty;
    public Label lblCost;

    public TableView <CartTM>tblCart;
    public TableColumn colDateValidate;
    public TableColumn colInvoiceNo;
    public TableColumn colPCode;
    public TableColumn colPName;
    public TableColumn colQTY;
    public TableColumn colAmount;
    public JFXButton btnConfirm;
    public JFXButton btn_remove;


    //////////////////////////////
    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap();
    Pattern name=Pattern.compile("^[A-z]{3,26}$");
    Pattern customerAddress=Pattern.compile("^[A-z]{3,46}$");
    Pattern qty=Pattern.compile("^[0-9]+$");

    /////////////////////////////
    public void initialize(){
        saveValidation();

        /////////////////////

        colDateValidate.setCellValueFactory(new PropertyValueFactory<>("validateDate"));
        colInvoiceNo.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        colPCode.setCellValueFactory(new PropertyValueFactory<>("perfumeCode"));
        colPName.setCellValueFactory(new PropertyValueFactory<>("perfumeName"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("qtyForSale"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("singleAmount"));
        ////////////////////////////
        btnValidate.setDisable(true);
        btnConfirm.setDisable(true);
        btn_remove.setDisable(true);
        dateInvoicedDate.setDisable(false);

        //////////////////////////////////////

        cmbPerfumeCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            try {
                loadPerfumeDetails(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        /////////////////////////////////////////

        try {
            loadPerfumeCodeToComboBox();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////////////////

        tblCart.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            remove_Selected_Row_Perfume= (int) newValue;
        });
        //////////////////////////////////////////////////////////////////

        setInvoiceNumberToCustomerOrder();
    }

    private void setInvoiceNumberToCustomerOrder() {
        try {
            lblInvoiceNumber.setText(new CustomerOrderController().InvoiceNumber());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    int remove_Selected_Row_Perfume= -1;

    ObservableList<CartTM>observableList= FXCollections.observableArrayList();


    public void Save_Customer_In_Database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        Invoice invoice1= new Invoice(
                lblInvoiceNumber.getText(),txtCustomerName.getText(),
                txtCustomerAddress.getText(),dateInvoicedDate.getValue());

        if(saveCustomerInDatabase(invoice1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Done").show();
            btnValidate.setDisable(false);
            dateInvoicedDate.setDisable(true);
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
        }
    }

    boolean saveCustomerInDatabase(Invoice invoice) throws SQLException, ClassNotFoundException {

         Connection connection= DBConnection.getInstance().getConnection();

         PreparedStatement preparedStatement= connection.prepareStatement("INSERT INTO Customer$Invoice VALUE(?,?,?,?)");

         preparedStatement.setObject(1,invoice.getInvoiceNumber());
         preparedStatement.setObject(2,invoice.getCustomerName());
         preparedStatement.setObject(3,invoice.getAddress());
         preparedStatement.setObject(4,invoice.getInvoiceDate());

         return preparedStatement.executeUpdate()>0;

    }

    public void Add_to_Database_Invoice_Details(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        ////////////////////////////////////////////////////////////////
        int sale= Integer.parseInt(txtForSale.getText());
        Double price=Double.parseDouble(lblPrice.getText());

        Double cost=sale*price;

        OrderPerfumeDetails order1=new OrderPerfumeDetails(cmbPerfumeCode.getValue(),Integer.parseInt(txtForSale.getText()),Double.parseDouble(String.valueOf(cost)),lblInvoiceNumber.getText()
        );

        if (saveOrderDetail(order1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Done").show();
            btnConfirm.setDisable(false);
            btn_remove.setDisable(false);
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
        }
        ///////////////////////////////////////////////////////////////

        Date VDate = Date.valueOf(dateInvoicedDate.getValue());
        String INumber= lblInvoiceNumber.getText();
        String PCode=cmbPerfumeCode.getValue();
        String PName=lblPName.getText();
        int QTYForSale=Integer.parseInt(txtForSale.getText());
        double Amount =Double.parseDouble(cost.toString());

        if (Integer.parseInt(lblQty.getText())<QTYForSale){
            new Alert(Alert.AlertType.WARNING,"Invalid Quantity\nOut of stock").show();
            return;
        }


        CartTM tm = new CartTM(
                VDate,INumber,PCode,QTYForSale,PName,Amount
        );

               int rowNumber=samePerfumeExists(tm) ;



                if (samePerfumeExists(tm)==-1){
                    observableList.add(tm);
                }else {
                    CartTM TM = observableList.get(rowNumber);
                    CartTM newTm = new CartTM(
                            TM.getValidateDate(), TM.getInvoiceNumber(), TM.getPerfumeCode(),
                            TM.getQtyForSale() + QTYForSale, TM.getPerfumeName(), TM.getSingleAmount() + Amount
                    );
                    observableList.remove(rowNumber);
                    observableList.add(newTm);

                }
                
                tblCart.setItems(observableList);
                getFinalAmount();
    }

    private boolean saveOrderDetail(OrderPerfumeDetails order) throws SQLException, ClassNotFoundException {

        Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO Ordered$Perfume VALUE (?,?,?,?)");

        preparedStatement.setObject(1,order.getPerfumeCode());
        preparedStatement.setObject(2,order.getQuantityForSale());
        preparedStatement.setObject(3,order.getCost());
        preparedStatement.setObject(4,order.getInvoiceNumber());

        return preparedStatement.executeUpdate()>0;

    }

    private int samePerfumeExists(CartTM tm){

        for (int i = 0; i < observableList.size(); i++) {
            if (tm.getPerfumeCode().equals(observableList.get(i).getPerfumeCode())) {
                return i;
            }
       }
        return -1;
    }

    void getFinalAmount(){

        double totalAmount=0;

        for (CartTM tm: observableList) {
           totalAmount+= tm.getSingleAmount();

        }
        lblCost.setText("Rs "+totalAmount+" /=");
    }

    public void Remove_From_Perfume_Table(MouseEvent mouseEvent) {
        if (remove_Selected_Row_Perfume==-1) {
            new Alert(Alert.AlertType.WARNING,"Select a row").show();
        }else{
            new Alert(Alert.AlertType.CONFIRMATION,"You remove the selected perfume").show();
            observableList.remove(remove_Selected_Row_Perfume);
            tblCart.refresh();
            getFinalAmount();
        }
    }

    ////////////////////////////////////
    private void loadPerfumeCodeToComboBox() throws SQLException, ClassNotFoundException {
        List<String> perfumeCode = new PerfumeFormController().getPerfumeCode();
        cmbPerfumeCode.getItems().addAll(perfumeCode);
    }

    private void loadPerfumeDetails(String perfumeCode) throws SQLException, ClassNotFoundException {
        String vNumber=cmbPerfumeCode.getValue();
        Connection connection= DBConnection.getInstance().getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Perfume WHERE PerfumeCode= ?");

        preparedStatement.setObject(1,cmbPerfumeCode.getValue());
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            Perfume perfume1=new Perfume(resultSet.getString(1),resultSet.getString(2),resultSet.getInt(3),
                    resultSet.getDouble(4),resultSet.getDouble(5),resultSet.getDouble(6),resultSet.getString(7));
            setToTextFiledPerfumeDetails(perfume1);

        }else{
            new Alert(Alert.AlertType.ERROR,"Empty Result set").show();
        }
    }

    private void setToTextFiledPerfumeDetails(Perfume perfume1) {
        lblPName.setText(perfume1.getNameOfPerfume());
        lblPrice.setText(String.valueOf(perfume1.getDistributePrice()));
        lblQty.setText(String.valueOf(perfume1.getQuantity()));
    }


    ////////////////////////////////////

    public void Save_the_final_order(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {
        ArrayList<OrderPerfumeDetails>orderPerfumeDetails=new ArrayList<>();

        double totalCost=0;

        for (CartTM tempTM:observableList) {
            totalCost+=tempTM.getSingleAmount();
            orderPerfumeDetails.add(new OrderPerfumeDetails(
                    tempTM.getPerfumeCode(),tempTM.getQtyForSale(),totalCost,tempTM.getInvoiceNumber()
            ));
        }


        CustomerOrder order= new CustomerOrder(
                dateInvoicedDate.getValue(),lblInvoiceNumber.getText(),totalCost,txtCustomerName.getText(),orderPerfumeDetails
        );

        PendingDelivery pendingDelivery1=new PendingDelivery(
                Date.valueOf(dateInvoicedDate.getValue()),
                lblInvoiceNumber.getText(),
                txtCustomerName.getText(),
                txtCustomerAddress.getText(),
                totalCost
        );

        if (new CustomerOrderController().placeCustomerOrder(order)&&SavePendingDeliver(pendingDelivery1)){
            new Alert(Alert.AlertType.CONFIRMATION,"Success").show();
            setInvoiceNumberToCustomerOrder();
        }else{
            new Alert(Alert.AlertType.ERROR,"Try Again").show();
        }
        //////////////////////////////////////////////////////////////////////////////////////////

    }

    private boolean SavePendingDeliver(PendingDelivery pendingDelivery) throws SQLException, ClassNotFoundException {
            Connection connection=DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PendingDelivery VALUES (?,?,?,?,?)");

        preparedStatement.setObject(1,pendingDelivery.getOrderDate());
        preparedStatement.setObject(2,pendingDelivery.getInvoiceNumber());
        preparedStatement.setObject(3,pendingDelivery.getCustomerName());
        preparedStatement.setObject(4,pendingDelivery.getCustomerAddress());
        preparedStatement.setObject(5,pendingDelivery.getBillCost());

        return preparedStatement.executeUpdate()>0;
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
        map.put(txtCustomerName,name);
        map.put(txtCustomerAddress,customerAddress);
        map.put(txtForSale,qty);

    }

    public void printInvoice(ActionEvent actionEvent) throws JRException {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/bill.jrxml"));
        JasperReport jasperReport= JasperCompileManager.compileReport(design);

        ObservableList<CartTM>observableList= FXCollections.observableArrayList();

        String Inumber=lblInvoiceNumber.getText();
        String name=txtCustomerName.getText();
        String Address=txtCustomerAddress.getText();

        HashMap map =new HashMap();
        map.put("InvoiceNumber",Inumber);
        map.put("name",name);
        map.put("Address",Address);

        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,map,new JRBeanArrayDataSource(observableList.toArray(new CartTM[0])));
        JasperViewer.viewReport(jasperPrint,false);
    }
}
