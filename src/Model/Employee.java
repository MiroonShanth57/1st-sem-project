package Model;

public class Employee {
    private String roleID;
    private String employeeName;
    private String employeeNIC;
    private String employeeAddress;
    private String roleDescription;
    private int contactNumber;
    private Double basicSalary;

    public Employee(String roleID, String employeeName, String employeeNIC, String employeeAddress, String roleDescription, int contactNumber, Double basicSalary) {
        this.roleID = roleID;
        this.employeeName = employeeName;
        this.employeeNIC = employeeNIC;
        this.employeeAddress = employeeAddress;
        this.roleDescription = roleDescription;
        this.contactNumber = contactNumber;
        this.basicSalary = basicSalary;
    }

    public Employee() {
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeNIC() {
        return employeeNIC;
    }

    public void setEmployeeNIC(String employeeNIC) {
        this.employeeNIC = employeeNIC;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public int getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(int contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }
}
