package accounts;

import java.util.Date;

/**
 * Created by Alex on 03/01/2017.
 */
abstract class Employee extends Person{

    private static int numEmployees;

    private int employeeID, payrollNo;

    Employee(String name, Date dob, int payrollNo) {
        super(name, dob);
        this.payrollNo = payrollNo;

        employeeID = ++numEmployees;
    }

    int getEmployeeID(){
        return employeeID;
    }

    int getPayrollNo(){
        return payrollNo;
    }

    void setPayrollNo(int num){
        this.payrollNo = num;
    }

}
