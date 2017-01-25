package accounts;

import java.util.Date;

/**
 * Created by Alex on 03/01/2017.
 */
public class WarehouseOperative extends Employee {

    private boolean forkliftTrained;
    private Date trainingDate;

    WarehouseOperative(String name, Date dob, int payrollNo, boolean forkliftTrained, Date trainingDate) {
        super(name, dob, payrollNo);
        this.forkliftTrained = forkliftTrained;
        this.trainingDate = trainingDate;
    }

    public boolean isForkliftTrained() {
        return forkliftTrained;
    }

    void setForkliftTrained(boolean forkliftTrained) {
        this.forkliftTrained = forkliftTrained;
    }

    /**
     *
     * @return The date of their forklift training qualification, or null if not forklift trained
     */
    Date getTrainingDate() {
        return trainingDate;
    }

    void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }
}
