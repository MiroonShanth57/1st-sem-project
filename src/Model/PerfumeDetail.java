package Model;

public class PerfumeDetail {
    private String PerfumeCode;
    private Double PerfumeUnitPrice;
    private int QuantityForSale;

    public PerfumeDetail() {
    }

    public PerfumeDetail(String perfumeCode, Double perfumeUnitPrice, int quantityForSale) {
        PerfumeCode = perfumeCode;
        PerfumeUnitPrice = perfumeUnitPrice;
        QuantityForSale = quantityForSale;
    }

    public String getPerfumeCode() {
        return PerfumeCode;
    }

    public void setPerfumeCode(String perfumeCode) {
        PerfumeCode = perfumeCode;
    }

    public Double getPerfumeUnitPrice() {
        return PerfumeUnitPrice;
    }

    public void setPerfumeUnitPrice(Double perfumeUnitPrice) {
        PerfumeUnitPrice = perfumeUnitPrice;
    }

    public int getQuantityForSale() {
        return QuantityForSale;
    }

    public void setQuantityForSale(int quantityForSale) {
        QuantityForSale = quantityForSale;
    }

    @Override
    public String toString() {
        return "PerfumeDetail{" +
                "PerfumeCode='" + PerfumeCode + '\'' +
                ", PerfumeUnitPrice=" + PerfumeUnitPrice +
                ", QuantityForSale=" + QuantityForSale +
                '}';
    }
}
