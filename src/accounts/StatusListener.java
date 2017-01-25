package accounts;

import warehouse.CustomerOrder;
import warehouse.PurchaseOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 03/01/2017.
 *
 * A class for listeners to changes in status of Customers, CustomerOrders and PurchaseOrders
 * using the observer design pattern.
 */
public class StatusListener {

    // Lists of each type of listener, all of which are notified when their corresponding notifyUpdate() methods are called
    private static List<CustomerListener> customerListeners = new ArrayList<>();
    private static List<CustomerOrderListener> coListeners = new ArrayList<>();
    private static List<PurchaseOrderListener> poListeners = new ArrayList<>();

    //The interfaces for each listener - one method: onStateChange() in each
    public interface CustomerOrderListener{
        void onStateChange(Customer c, CustomerOrder co);
    }
    public interface  PurchaseOrderListener{
        void onStateChange(PurchaseOrder po);
    }
    public interface CustomerListener{
        void onStateChange(Customer c);
    }


    /**
     * Adds the CustomerOrderListener
     * @param col the CustomerOrderListener to set
     */
    public void setCoListener(CustomerOrderListener col){
        coListeners.add(col);
    }

    /**
     * Adds the PurchaseOrderListener
     * @param pol the PurchaseOrderListener to set
     */
    public void setPoListener(PurchaseOrderListener pol){
        poListeners.add(pol);
    }

    /**
     * Adds the CustomerListener
     * @param cListener the CustomerListener to set
     */
    void setCustomerListener(CustomerListener cListener){
        customerListeners.add(cListener);
    }

    /**
     * Notifies all CustomerOrderListeners of an update
     * @param c the customer who owns the order
     * @param co the CustomerOrder which has undergone a state change
     */
    public void notifyUpdate(Customer c, CustomerOrder co){
        if(!coListeners.isEmpty()){
            for(CustomerOrderListener col : coListeners){
                col.onStateChange(c, co);
            }
        }
    }

    /**
     * Notifies all PurchaseOrderListeners of an update
     * @param po the CustomerOrder which has undergone a state change
     */
    public void notifyUpdate(PurchaseOrder po){
        if(!poListeners.isEmpty()){
            for(PurchaseOrderListener pol : poListeners){
                pol.onStateChange(po);
            }
        }
    }

    /**
     * Notifies all CustomerListeners of an update
     * @param c the Customer which has undergone a state change
     */
    void notifyUpdate(Customer c){
        if(!customerListeners.isEmpty()){
            for(CustomerListener cl : customerListeners){
                cl.onStateChange(c);
            }
        }
    }

}
