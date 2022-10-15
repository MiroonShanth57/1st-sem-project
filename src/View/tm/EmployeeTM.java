package View.tm;

public class EmployeeTM {
    private String ID;
    private String Name;
    private String NIC;
    private String Address;
    private String Description;
    private int CNumber;
    private Double BSalary;

    public EmployeeTM(String ID, String name, String NIC, String address, String description, int CNumber, Double BSalary) {
        this.ID = ID;
        this.Name = name;
        this.NIC = NIC;
        this.Address = address;
        this.Description = description;
        this.CNumber = CNumber;
        this.BSalary = BSalary;
    }

    public EmployeeTM() {
    }




    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getCNumber() {
        return CNumber;
    }

    public void setCNumber(int CNumber) {
        this.CNumber = CNumber;
    }

    public Double getBSalary() {
        return BSalary;
    }

    public void setBSalary(Double BSalary) {
        this.BSalary = BSalary;
    }
}
