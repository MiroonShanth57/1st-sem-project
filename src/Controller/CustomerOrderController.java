package Controller;

import Model.CustomerOrder;
import Model.OrderPerfumeDetails;
import Model.PerfumeDetail;
import db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerOrderController {

    public String InvoiceNumber() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM `Customer Order` ORDER BY InvoiceNumber DESC LIMIT 1").executeQuery();

        if(resultSet.next()){
            int tempNumber=Integer.parseInt(resultSet.getString(2).split("-")[1]);
            tempNumber=tempNumber+1;
                if(tempNumber<9){
                    return "SO-000"+tempNumber;

                }else if(tempNumber<99){
                    return "SO-00"+tempNumber;
                }else if(tempNumber<999){
                    return "SO-0"+tempNumber;
                }else{
                    return "SO-"+tempNumber;
                }
        }else{
            return "SO-0001";
        }
    }

    public boolean placeCustomerOrder(CustomerOrder order){

        Connection transaction_connection=null;

        try {

            transaction_connection=DBConnection.getInstance().getConnection();
            transaction_connection.setAutoCommit(false);
            PreparedStatement preparedStatement = transaction_connection.prepareStatement("INSERT INTO `Customer Order` VALUES (?,?,?,?)");

            preparedStatement.setObject(1,order.getOrderDate());
            preparedStatement.setObject(2,order.getInvoiceNumber());
            preparedStatement.setObject(3,order.getCustomerName());
            preparedStatement.setObject(4,order.getBillCost());


            if (preparedStatement.executeUpdate()>0){
                if(saveCustomerOrderDetails(order.getInvoiceNumber(),order.getPerfumeDetails())){
                    transaction_connection.commit();
                    return true;
                }else {
                    transaction_connection.rollback();
                    return false;
                }
            }else{
                transaction_connection.rollback();
                return false;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            try {
                transaction_connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        return false;
    }

    private boolean saveCustomerOrderDetails(String invoiceNumber, ArrayList<OrderPerfumeDetails>orderPerfumeDetails) throws SQLException, ClassNotFoundException {

        for (OrderPerfumeDetails temp:orderPerfumeDetails
             ) {
            PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Ordered$Perfume VALUES (?,?,?,?)");

                preparedStatement.setObject(1,temp.getPerfumeCode());
                preparedStatement.setObject(2,temp.getQuantityForSale());
                preparedStatement.setObject(3,temp.getCost());
                preparedStatement.setObject(4,temp.getInvoiceNumber());

                if (preparedStatement.executeUpdate()>0){
                    if (remove_QTY_From_The_Stock(temp.getPerfumeCode(),temp.getQuantityForSale())){

                    }else{
                        return false;
                    }

                }else {
                    return false;
                }

        }
        return true;
    }

    private boolean remove_QTY_From_The_Stock(String perfumeCode,int quantity) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE Perfume SET Quantity=(Quantity-" + quantity + ")WHERE PerfumeCode='" + perfumeCode + "'");

            return preparedStatement.executeUpdate()>0;

    }
}
