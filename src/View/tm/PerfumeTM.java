package View.tm;

public class PerfumeTM {
    private String PerfumeCode;
    private String NameOfPerfume;
    private int Quantity;
    private Double DistributePrice;
    private Double UnitPrice;
    private Double MRPPrice;
    private String SupplierID;

    public PerfumeTM() {
    }

    public PerfumeTM(String perfumeCode, String nameOfPerfume, int quantity, Double distributePrice, Double unitPrice, Double MRPPrice, String supplierID) {
        PerfumeCode = perfumeCode;
        NameOfPerfume = nameOfPerfume;
        Quantity = quantity;
        DistributePrice = distributePrice;
        UnitPrice = unitPrice;
        this.MRPPrice = MRPPrice;
        SupplierID = supplierID;
    }

    public String getPerfumeCode() {
        return PerfumeCode;
    }

    public void setPerfumeCode(String perfumeCode) {
        PerfumeCode = perfumeCode;
    }

    public String getNameOfPerfume() {
        return NameOfPerfume;
    }

    public void setNameOfPerfume(String nameOfPerfume) {
        NameOfPerfume = nameOfPerfume;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public Double getDistributePrice() {
        return DistributePrice;
    }

    public void setDistributePrice(Double distributePrice) {
        DistributePrice = distributePrice;
    }

    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }

    public Double getMRPPrice() {
        return MRPPrice;
    }

    public void setMRPPrice(Double MRPPrice) {
        this.MRPPrice = MRPPrice;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }
}
