package Model;

import java.sql.Date;
import java.time.LocalDate;

public class Delivery {
    private String InvoiceNumber;
    private String DeliveryStaff;
    private String VehicleNumber;
    private Date DeliveryDate;
    private Double BillAmount;

    public Delivery() {
    }

    public Delivery(String invoiceNumber, String deliveryStaff, String vehicleNumber, Date deliveryDate, Double billAmount) {
        InvoiceNumber = invoiceNumber;
        DeliveryStaff = deliveryStaff;
        VehicleNumber = vehicleNumber;
        DeliveryDate = deliveryDate;
        BillAmount = billAmount;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public String getDeliveryStaff() {
        return DeliveryStaff;
    }

    public void setDeliveryStaff(String deliveryStaff) {
        DeliveryStaff = deliveryStaff;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public Date getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public Double getBillAmount() {
        return BillAmount;
    }

    public void setBillAmount(Double billAmount) {
        BillAmount = billAmount;
    }
}
