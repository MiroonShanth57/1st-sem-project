package Controller;

import Model.Cheque;
import Util.ValidationUtil;
import View.tm.ChequeTM;
import com.jfoenix.controls.JFXDatePicker;
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

public class ChequeFormController {
    public JFXTextField txtCNumber;
    public JFXDatePicker dteckrCollectionDate;
    public JFXTextField txtAmount;
    public JFXTextField txtCName;
    public JFXTextField txtBank;
    public JFXDatePicker dteckrBankingDate;
    public JFXTextField txtInvoiceNumber;
    public TableView <ChequeTM>tblAllCheque;

    public TableColumn colCNumber;
    public TableColumn colBank;
    public TableColumn ColAmount;
    public TableColumn colBankingDate;
    public TableColumn colAction;
    public TableColumn colAmount;

    /////////////////////////////////
    LinkedHashMap<TextField, Pattern> map=new LinkedHashMap();

    Pattern CNumber=Pattern.compile("^[0-9]{6,18}$");
    Pattern INumber=Pattern.compile("^(SO-)[0-9]{1,4}$");
    Pattern CusName=Pattern.compile("^[A-z]{3,26}$");
    Pattern Bank=Pattern.compile("^[A-z]{3,26}$");
    Pattern Amount=Pattern.compile("^\\d{1,6}(?:\\.\\d{0,2})?$");

    //////////////////////////////////


    public void initialize() throws SQLException, ClassNotFoundException {
        load_the_data_for_table_method();
        saveValidation();
    }



    public void Add_Cheque_Details_Database(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException {

        Cheque cheque1 = new Cheque(Integer.parseInt(txtCNumber.getText()), txtCName.getText(),
                Date.valueOf(dteckrCollectionDate.getValue()), Double.parseDouble(txtAmount.getText()),
                txtBank.getText(), Date.valueOf(dteckrBankingDate.getValue()), txtInvoiceNumber.getText());

        if (saveChequeDetailsInDatabase(cheque1)) {
            load_the_data_for_table_method();
            new Alert(Alert.AlertType.CONFIRMATION,"Successfully Added").show();
        } else {
            new Alert(Alert.AlertType.CONFIRMATION,"Oops, something went wrong. Please try again.").show();
        }
    }

    boolean saveChequeDetailsInDatabase(Cheque cheque) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();

        PreparedStatement preparedStatement= connection.prepareStatement("INSERT INTO Cheque VALUES (?,?,?,?,?,?,?)");

        preparedStatement.setObject(1,cheque.getChequeNumber());
        preparedStatement.setObject(2,cheque.getNameOfCheque());
        preparedStatement.setObject(3,cheque.getCollectionDate());
        preparedStatement.setObject(4,cheque.getChequeAmount());
        preparedStatement.setObject(5,cheque.getBank());
        preparedStatement.setObject(6,cheque.getBankingDate());
        preparedStatement.setObject(7,cheque.getInvoiceNumber());

        return preparedStatement.executeUpdate()>0;
    }

    /////////////////////////////////////////////////

    private void load_the_data_for_table_method() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Cheque");
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Cheque> cheques=new ArrayList<>();
        while (resultSet.next()){
            cheques.add(new Cheque(
                    resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getDate(3),
                    resultSet.getDouble(4),
                    resultSet.getString(5),
                    resultSet.getDate(6),
                    resultSet.getString(7)
            ));
        }

        setChequesToTable(cheques);

        colCNumber.setCellValueFactory(new PropertyValueFactory<>("chequeNumber"));
        colBank.setCellValueFactory(new PropertyValueFactory<>("bank"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("chequeAmount"));
        colBankingDate.setCellValueFactory(new PropertyValueFactory<>("bankingDate"));


    }

    private void setChequesToTable(ArrayList<Cheque> cheques) {
        ObservableList<ChequeTM> observableList= FXCollections.observableArrayList();
        cheques.forEach(e->{
            observableList.add(new ChequeTM(e.getChequeNumber(),e.getNameOfCheque(),e.getCollectionDate(),
                    e.getChequeAmount(), e.getBank(),e.getBankingDate(),
                    e.getInvoiceNumber()));

        });
        tblAllCheque.setItems(observableList);

    }

    /////////////////////////////

    public List<String> getChequeNumber()throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Cheque").executeQuery();

        List<String>ChequeNumber=new ArrayList<>();
        while (resultSet.next()){
            ChequeNumber.add(resultSet.getString(1));
        }
        return ChequeNumber;
    }

    //////////////////////////////////////

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
        map.put(txtCNumber,CNumber);
        map.put(txtInvoiceNumber,INumber);
        map.put(txtCName,CusName);
        map.put(txtBank,Bank);
        map.put(txtAmount,Amount);

    }

    //////////////////////////////////////

}
