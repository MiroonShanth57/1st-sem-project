package Model;

public class Supplier {
    private String ID;
    private String Name;
    private String Address;
    private String Email;
    private int CNumber;

    public Supplier(String ID, String name, String address, String email, int CNumber) {
        this.ID = ID;
        Name = name;
        Address = address;
        Email = email;
        this.CNumber = CNumber;
    }

    public Supplier() {
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getCNumber() {
        return CNumber;
    }

    public void setCNumber(int CNumber) {
        this.CNumber = CNumber;
    }
}
