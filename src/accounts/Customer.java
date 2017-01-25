package accounts;

import warehouse.CustomerOrder;
import warehouse.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static warehouse.Status.CUST_STATUS.ACTIVE;

/**
 * Created by Alex on 03/01/2017.
 */
public class Customer extends Person implements Status {

    private static int numCustomers;

    private int customerID;
    private float balance = 0F;
    private CUST_STATUS status = ACTIVE;
    private List<CustomerOrder> customerOrders = new ArrayList<>();
    private Date lastModified;


    private StatusListener listener;


    public Customer(String name, Date dob) {
        super(name, dob);

        customerID = ++numCustomers;
        lastModified = new Date();

        status = ACTIVE;
        listener = new StatusListener();
        listener.notifyUpdate(this);
    }

    int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @return the customers balance. > 0 means in debit, < 0 means in credit
     */
    float getBalance() {
        return balance;
    }

    public CUST_STATUS getStatus() {
        return status;
    }

    /**
     * @return the last activity on the account
     */
    Date getLastModified() {
        return lastModified;
    }

    /**
     * @param amount the amount owed by the customer for a specific order. This amount will be added to their balance
     */
    void chargeOrder(float amount) {
        this.balance += amount;
        lastModified = new Date();
    }


    /**
     * @param paid the amount paid by the customer. This amount will be deducted from their balance
     */
    void payForItems(float paid) {
        this.balance -= paid;
        lastModified = new Date();
    }

    void setStatus(CUST_STATUS status) {
        if (this.status != status) {
            this.status = status;
            lastModified = new Date();
            listener.notifyUpdate(this);
        }
    }
}


