package Model;

public class OrderPerfumeDetails {
    private String perfumeCode;
    private int quantityForSale;
    private Double cost;
    private String invoiceNumber;

    public OrderPerfumeDetails() {
    }

    public OrderPerfumeDetails(String perfumeCode, int quantityForSale, Double cost, String invoiceNumber) {
        this.perfumeCode = perfumeCode;
        this.quantityForSale = quantityForSale;
        this.cost = cost;
        this.invoiceNumber = invoiceNumber;
    }

    public String getPerfumeCode() {
        return perfumeCode;
    }

    public void setPerfumeCode(String perfumeCode) {
        this.perfumeCode = perfumeCode;
    }

    public int getQuantityForSale() {
        return quantityForSale;
    }

    public void setQuantityForSale(int quantityForSale) {
        this.quantityForSale = quantityForSale;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
}
