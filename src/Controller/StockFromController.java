package Controller;

import Model.Employee;
import Model.Perfume;
import View.tm.EmployeeTM;
import View.tm.PerfumeTM;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StockFromController {


    public TableView <PerfumeTM>tblStockBalance;
    public TableColumn colCode;
    public TableColumn colName;
    public TableColumn colQTY;

    public void initialize(){
        try {
            load_Perfume_details();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void load_Perfume_details() throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement= DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM Perfume");
        ResultSet resultSet = preparedStatement.executeQuery();

        ArrayList<Perfume> perfumes=new ArrayList<>();
        while (resultSet.next()){
            perfumes.add(new Perfume(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getDouble(6),
                    resultSet.getString(7)
            ));
        }

        setPerfumeToTable(perfumes);

        colCode.setCellValueFactory(new PropertyValueFactory<>("PerfumeCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("NameOfPerfume"));
        colQTY.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

    }

    private void setPerfumeToTable(ArrayList<Perfume> perfumes) {
        ObservableList<PerfumeTM> observableList= FXCollections.observableArrayList();
        perfumes.forEach(e->{
            observableList.add(new PerfumeTM(e.getPerfumeCode(),e.getNameOfPerfume(),e.getQuantity(),e.getDistributePrice(),
                    e.getUnitPrice(),e.getMRPPrice(),e.getSupplierID()));

        });
        tblStockBalance.setItems(observableList);
    }
}
