package Model;

import java.sql.Date;
import java.time.LocalDate;


public class Invoice {
    private String invoiceNumber;
    private String customerName;
    private String address;
    private LocalDate invoiceDate;

    public Invoice(String string, String resultSetString, String setString, Date date) {
    }

    public Invoice(String invoiceNumber, String customerName, String address, LocalDate invoiceDate) {
        this.invoiceNumber = invoiceNumber;
        this.customerName = customerName;
        this.address = address;
        this.invoiceDate = invoiceDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
}
