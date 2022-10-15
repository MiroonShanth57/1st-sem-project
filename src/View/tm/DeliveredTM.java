package View.tm;

import java.sql.Date;
import java.time.LocalDate;

public class DeliveredTM {
    private String invoiceNumber;
    private String deliveryStaff;
    private String vehicleNumber;
    private Date deliveryDate;
    private Double billAmount;

    public DeliveredTM() {
    }

    public DeliveredTM(String invoiceNumber, String deliveryStaff, String vehicleNumber, Date deliveryDate, Double billAmount) {
        this.invoiceNumber = invoiceNumber;
        this.deliveryStaff = deliveryStaff;
        this.vehicleNumber = vehicleNumber;
        this.deliveryDate = deliveryDate;
        this.billAmount = billAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getDeliveryStaff() {
        return deliveryStaff;
    }

    public void setDeliveryStaff(String deliveryStaff) {
        this.deliveryStaff = deliveryStaff;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }
}
