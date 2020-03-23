package sample.model;

public class Employee {
    private String empName,empSalary,empDesignation;

    public Employee(String empName, String empDesignation,String empSalary) {
        this.empName = empName;
        this.empSalary = empSalary;
        this.empDesignation = empDesignation;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpSalary() {
        return empSalary;
    }

    public void setEmpSalary(String empSalary) {
        this.empSalary = empSalary;
    }

    public String getEmpDesignation() {
        return empDesignation;
    }

    public void setEmpDesignation(String empDesignation) {
        this.empDesignation = empDesignation;
    }
}
