package accounts;

import accounts.email_updates.Emailer;
import warehouse.CustomerOrder;
import warehouse.Order;
import warehouse.PurchaseOrder;
import warehouse.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 04/01/2017.
 *
 * The AccountsTeam are responsible for keeping customers informed of changes to their order
 * and monitoring the process of PurchaseOrders
 */
public class AccountsTeam implements StatusListener.PurchaseOrderListener, StatusListener.CustomerOrderListener,
        StatusListener.CustomerListener{

    private List<Customer> customers = new ArrayList<>();
    private List<Order> completedOrders = new ArrayList<>();

    private Emailer emailer;

    public AccountsTeam(){
        //Adding listeners for statuses in customerOrder, PurchaseOrder and Customer so accounts can handle any changes
        StatusListener listener = new StatusListener();
        listener.setCoListener(this);
        listener.setPoListener(this);
        listener.setCustomerListener(this);

        emailer = new Emailer();

    }


    public void addCustomer(Customer c){
        customers.add(c);
    }

    /**
     * returns all customers with a given customer status
     * @param status the customer status of the customers to be found
     */
    private void getCustomers(Status.CUST_STATUS status){

    }

    /**
     * @return a list of customers who are in debit
     */
    private List<Customer> getOwingCustomers(){
        List<Customer> owing = new ArrayList<>();

        for( Customer c : customers){
            if(c.getBalance() > 0) owing.add(c);
        }

        return owing;
    }

    @Override
    public void onStateChange(Customer c, CustomerOrder co) {
        System.out.println("Account have been notified of update to a CO");
        if(co.getOrderStatus() == Status.CO_STATUS.DESPATCHED || co.getOrderStatus() == Status.CO_STATUS.ON_HOLD){
            emailer.notifyCustomer(co);
        }
    }

    @Override
    public void onStateChange(PurchaseOrder po) {
        //Accounts have been notified
        System.out.println("Account have been notified of update to a PurchaseOrder");

    }

    @Override
    public void onStateChange(Customer c) {
        if(!customers.contains(c)){
            customers.add(c);
            emailer.notifyCustomer(c);
        }
    }
}
