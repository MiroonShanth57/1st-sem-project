package Model;

public class Salary {
    private String roleID;
    private Double basicSalary;
    private Double monthlyAdvance;
    private Double salaryIncrement;
    private Double salaryCut;
    private Double finalSalary;

    public Salary() {
    }

    public Salary(String roleID, Double basicSalary, Double monthlyAdvance, Double salaryIncrement, Double salaryCut, Double finalSalary) {
        this.roleID = roleID;
        this.basicSalary = basicSalary;
        this.monthlyAdvance = monthlyAdvance;
        this.salaryIncrement = salaryIncrement;
        this.salaryCut = salaryCut;
        this.finalSalary = finalSalary;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public Double getMonthlyAdvance() {
        return monthlyAdvance;
    }

    public void setMonthlyAdvance(Double monthlyAdvance) {
        this.monthlyAdvance = monthlyAdvance;
    }

    public Double getSalaryIncrement() {
        return salaryIncrement;
    }

    public void setSalaryIncrement(Double salaryIncrement) {
        this.salaryIncrement = salaryIncrement;
    }

    public Double getSalaryCut() {
        return salaryCut;
    }

    public void setSalaryCut(Double salaryCut) {
        this.salaryCut = salaryCut;
    }

    public Double getFinalSalary() {
        return finalSalary;
    }

    public void setFinalSalary(Double finalSalary) {
        this.finalSalary = finalSalary;
    }
}
