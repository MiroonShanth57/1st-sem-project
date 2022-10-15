package Model;

public class Payment {
    private String InvoiceNumber;
    private Double PaymentAmount;
    private Double Cash;
    private int ChequeNumber;
    private String PaymentCollector;

    public Payment() {
    }

    public Payment(String invoiceNumber, Double paymentAmount, Double cash, int chequeNumber, String paymentCollector) {
        InvoiceNumber = invoiceNumber;
        PaymentAmount = paymentAmount;
        Cash = cash;
        ChequeNumber = chequeNumber;
        PaymentCollector = paymentCollector;
    }

    public String getInvoiceNumber() {
        return InvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        InvoiceNumber = invoiceNumber;
    }

    public Double getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        PaymentAmount = paymentAmount;
    }

    public Double getCash() {
        return Cash;
    }

    public void setCash(Double cash) {
        Cash = cash;
    }

    public int getChequeNumber() {
        return ChequeNumber;
    }

    public void setChequeNumber(int chequeNumber) {
        ChequeNumber = chequeNumber;
    }

    public String getPaymentCollector() {
        return PaymentCollector;
    }

    public void setPaymentCollector(String paymentCollector) {
        PaymentCollector = paymentCollector;
    }
}
