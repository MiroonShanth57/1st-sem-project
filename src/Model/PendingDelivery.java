package Model;

import java.sql.Date;
import java.time.LocalDate;

public class PendingDelivery {
    private Date OrderDate;
    private String InvoiceNumber;
    private String CustomerName;
    private String CustomerAddress;
    private Double BillCost;

    public PendingDelivery() {
    }

    public PendingDelivery(Date orderDate, String invoiceNumber, String customerName, String customerAddress, Double billCost) {
        OrderDate = orderDate;
        InvoiceNumber = invoiceNumber;
        CustomerName = customerName;
        CustomerAddress = customerAddress;
        BillCost = billCost;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        CustomerAddress = customerAddress;
    }

    public Double getBillCost() {
        return BillCost;
    }

    public void setBillCost(Double billCost) {
        BillCost = billCost;
    }
}
