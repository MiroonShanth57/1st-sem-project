package View.tm;

import java.sql.Date;
import java.time.LocalDate;

public class ChequeTM {
    private int chequeNumber;
    private String nameOfCheque;
    private Date collectionDate;
    private Double chequeAmount;
    private String bank;
    private Date bankingDate;
    private String invoiceNumber;


    public ChequeTM() {
    }

    public ChequeTM(int chequeNumber, String nameOfCheque, Date collectionDate, Double chequeAmount, String bank, Date bankingDate, String invoiceNumber) {
        this.chequeNumber = chequeNumber;
        this.nameOfCheque = nameOfCheque;
        this.collectionDate = collectionDate;
        this.chequeAmount = chequeAmount;
        this.bank = bank;
        this.bankingDate = bankingDate;
        this.invoiceNumber = invoiceNumber;
    }

    public int getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(int chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getNameOfCheque() {
        return nameOfCheque;
    }

    public void setNameOfCheque(String nameOfCheque) {
        this.nameOfCheque = nameOfCheque;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public Double getChequeAmount() {
        return chequeAmount;
    }

    public void setChequeAmount(Double chequeAmount) {
        this.chequeAmount = chequeAmount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Date getBankingDate() {
        return bankingDate;
    }

    public void setBankingDate(Date bankingDate) {
        this.bankingDate = bankingDate;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
