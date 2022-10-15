package View.tm;

import java.sql.Date;

public class CartTM {
    private Date validateDate;
    private String invoiceNumber;
    private String perfumeCode;
    private int qtyForSale;
    private String perfumeName;
    private Double singleAmount;


    public CartTM() {
    }

    public CartTM(Date validateDate, String invoiceNumber, String perfumeCode, int qtyForSale, String perfumeName, Double singleAmount) {
        this.validateDate = validateDate;
        this.invoiceNumber = invoiceNumber;
        this.perfumeCode = perfumeCode;
        this.qtyForSale = qtyForSale;
        this.perfumeName = perfumeName;
        this.singleAmount = singleAmount;
    }

    public Date getValidateDate() {
        return validateDate;
    }

    public void setValidateDate(Date validateDate) {
        this.validateDate = validateDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getPerfumeCode() {
        return perfumeCode;
    }

    public void setPerfumeCode(String perfumeCode) {
        this.perfumeCode = perfumeCode;
    }

    public int getQtyForSale() {
        return qtyForSale;
    }

    public void setQtyForSale(int qtyForSale) {
        this.qtyForSale = qtyForSale;
    }

    public Double getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(Double singleAmount) {
        this.singleAmount = singleAmount;
    }

    public String getPerfumeName() {
        return perfumeName;
    }

    public void setPerfumeName(String perfumeName) {
        this.perfumeName = perfumeName;
    }

    @Override
    public String toString() {
        return "CartTM{" +
                "validateDate=" + validateDate +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", perfumeCode='" + perfumeCode + '\'' +
                ", qtyForSale=" + qtyForSale +
                ", perfumeName='" + perfumeName + '\'' +
                ", singleAmount=" + singleAmount +
                '}';
    }
}
