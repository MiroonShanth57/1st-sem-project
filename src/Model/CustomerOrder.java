package Model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerOrder {
    private LocalDate OrderDate;
    private String InvoiceNumber;
    private Double BillCost;
    private String CustomerName;
    private ArrayList<OrderPerfumeDetails>orderPerfumeDetails;

    public CustomerOrder(LocalDate orderDate, String invoiceNumber, Double billCost, String customerName, ArrayList<OrderPerfumeDetails>orderPerfumeDetails) {
        OrderDate = orderDate;
        InvoiceNumber = invoiceNumber;
        BillCost = billCost;
        CustomerName = customerName;
        this.orderPerfumeDetails = orderPerfumeDetails;
    }

    public CustomerOrder(Date date, String string, String resultSetString, double aDouble) {
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        OrderDate = orderDate;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public Double getBillCost() {
        return BillCost;
    }

    public void setBillCost(Double billCost) {
        BillCost = billCost;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public ArrayList<OrderPerfumeDetails> getPerfumeDetails() {
        return orderPerfumeDetails;
    }

    public void setPerfumeDetails(ArrayList<OrderPerfumeDetails> orderPerfumeDetails) {
        this.orderPerfumeDetails = orderPerfumeDetails;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "OrderDate=" + OrderDate +
                ", InvoiceNumber='" + InvoiceNumber + '\'' +
                ", BillCost=" + BillCost +
                ", CustomerName='" + CustomerName + '\'' +
                ", orderPerfumeDetails=" + orderPerfumeDetails +
                '}';
    }
}
