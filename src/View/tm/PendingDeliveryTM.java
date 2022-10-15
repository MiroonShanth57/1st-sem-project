package View.tm;

import java.sql.Date;
import java.time.LocalDate;

public class PendingDeliveryTM {
    private Date ODate;
    private String INumber;
    private String CName;
    private String CAddress;
    private Double Cost;

    public PendingDeliveryTM() {
    }

    public PendingDeliveryTM(Date ODate, String INumber, String CName, String CAddress, Double cost) {
        this.ODate = ODate;
        this.INumber = INumber;
        this.CName = CName;
        this.CAddress = CAddress;
        Cost = cost;
    }

    public Date getODate() {
        return ODate;
    }

    public void setODate(Date ODate) {
        this.ODate = ODate;
    }

    public String getINumber() {
        return INumber;
    }

    public void setINumber(String INumber) {
        this.INumber = INumber;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getCAddress() {
        return CAddress;
    }

    public void setCAddress(String CAddress) {
        this.CAddress = CAddress;
    }

    public Double getCost() {
        return Cost;
    }

    public void setCost(Double cost) {
        Cost = cost;
    }
}
