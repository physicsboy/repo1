package warehouse;

import accounts.Customer;
import accounts.StatusListener;
import accounts.WarehouseOperative;
import warehouse.Status.PO_STATUS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static warehouse.Status.CO_STATUS.*;
import static warehouse.Status.PO_STATUS.ARRIVED;
import static warehouse.Status.PO_STATUS.UNPACKED;

/**
 * Created by Alex on 03/01/2017.
 * Stores information for the warehouse in the form of 2 lists (open orders and in-progress orders)
 * Uses the singleton design pattern so that all other classes reference the same warehouse
 */
public class Warehouse implements StatusListener.CustomerOrderListener, StatusListener.PurchaseOrderListener{

    private static Warehouse warehouseSingleton = new Warehouse();

    private List<WarehouseOperative> employees = new ArrayList<>();
    private List<Order> openOrders = new ArrayList<>();
    private List<Order> inProgress = new ArrayList<>();
    private UserInput userInput;


    /**
     * private constructor for use in the singleton design pattern
     */
    private Warehouse(){
        StatusListener listener = new StatusListener();
        listener.setCoListener(this);
        listener.setPoListener(this);
    }

    public static Warehouse getInstance(){
        return warehouseSingleton;
    }

    /**
     * @return the next open order in list or null if there are no open orders
     */
    public Order getNextOrder(){
        if(openOrders.size() !=0){
            return openOrders.get(0);
        }
        else{
            System.out.println("No Open Orders!");
            return null;
        }
    }

    /**
     * @return the list of open orders to be handled
     */
    List<Order> getOpenOrders(){
        return openOrders;
    }

    void addOrder(Order o){
        openOrders.add(o);
    }

    void cancelOrder(Order o){
        openOrders.remove(o);
    }

    /**
     *
     * @param orderID the id of the order to retrieve
     * @return the order if it is still in the hands of the warehouse (Not cancelled or despatched
     * (for {@link CustomerOrder}) or unpacked (for {@link PurchaseOrder})
     */
    Order getOrder(int orderID){
        for(Order order : openOrders){
            if(order.getOrderID() == orderID) return order;
        }
        for(Order order : inProgress){
            if(order.getOrderID() == orderID) return order;
        }

        return null;
    }

    @Override
    public void onStateChange(Customer c, CustomerOrder co) {
        //handle the changes of state of a CustomerOrder here
        if(co.getOrderStatus()==PICKING){
            openOrders.remove(co);
            inProgress.add(co);
            System.out.println("PICKING");
        }
        else if(co.getOrderStatus() == DESPATCHED){
            inProgress.remove(co);
            openOrders.remove(co);
            System.out.println("DESPATCHED");
        }
        else if(co.getOrderStatus() == PLACED){
            inProgress.remove(co);
            openOrders.add(co);
            System.out.println("PLACED");
        }
        else if(co.getOrderStatus() == ON_HOLD){
            openOrders.remove(co);
            if(!inProgress.contains(co)) inProgress.add(co);
            System.out.println("ON_HOLD");
        }
    }

    @Override
    public void onStateChange(PurchaseOrder po) {
        //handles the changes of state of a PurchaseOrder here
        if(po.getOrderStatus() == ARRIVED){
            openOrders.remove(po);
            inProgress.add(po);
            po.setDateArrived(new Date());
        }
        else if(po.getOrderStatus() == UNPACKED){
            openOrders.remove(po);
            inProgress.remove(po);
        }
        else if(po.getOrderStatus() == PO_STATUS.ORDERED){
            openOrders.add(po);
            inProgress.remove(po);
        }
    }


    /**
     *
     * @param showCustOrders will include CustomerOrders if true
     * @param showPurchaseOrders will include PurchaseOrders if true
     * @param showOpenOrders will include open orders if true
     * @param showInProgress will include orders which are in progress if true
     * @return the list of orders matching the arguments criteria
     */
    public List<Order> showOrderMenu(boolean showCustOrders, boolean showPurchaseOrders, boolean showOpenOrders, boolean showInProgress) {
        List<Order> orderObjects = new ArrayList<>();
        if (showOpenOrders) {
            for (Order order : openOrders) {
                if (order != null) {
                    if (showCustOrders) {
                        if (order instanceof CustomerOrder) orderObjects.add(order);
                    }
                    if (showPurchaseOrders) {
                        if (order instanceof PurchaseOrder) orderObjects.add(order);
                    }

                }
            }
        }
        if(showInProgress) {
            for (Order order : inProgress) {
                if (order != null) {
                    if (showCustOrders && showPurchaseOrders) orderObjects.add(order);
                    else if (showCustOrders) {
                        if (order instanceof CustomerOrder) orderObjects.add(order);
                    } else if (showPurchaseOrders) {
                        if (order instanceof PurchaseOrder) orderObjects.add(order);
                    }

                }
            }
        }

        return orderObjects;
    }





    /*
     *
     *
     *
     *
     * UNCOMMENT THIS AREA FOR USE OF THE TEXTUAL INTERFACE
     *
     *
     *
     * */
    //region TEXT USER INTERFACE
    /*
    void showMainMenu() {
        List<String> options = new ArrayList<>();
        options.add("1 - Show all open orders");
        options.add("2 - Show the next order");
        options.add("3 - Only Show Open Customer Orders");
        options.add("4 - Only Show Open Purchase Orders");
        options.add("5 - Show All Orders In Progress");
        options.add("6 - Show Customer Orders In Progress");
        options.add("7 - show Purchase Orders In progress");

        userInput = new UserInput("What do you want to do?", options, null);
        int input = userInput.getUserInput();

        switch (input){
            case 1:
                showOrderMenu(true, true, true);
                break;
            case 2:
                showOrderDetails(getNextOrder());
                break;
            case 3:
                showOrderMenu(true, false, true);
                break;
            case 4:
                showOrderMenu(false, true, true);
                break;
            case 5:
                showOrderMenu(true, true, false);
                break;
            case 6:
                showOrderMenu(true, false, false);
                break;
            case 7:
                showOrderMenu(false, true, false);
                break;
            default:
                System.out.println("Invalid input.");
                showMainMenu();

        }
    }

    private void showOrderMenu(boolean showCustOrders, boolean showPurchaseOrders, boolean showOpenOrders){
        List<String> options = new ArrayList<>();
        if(showOpenOrders) {
            for (Order order : openOrders) {
                if (order != null) {
                    if (showCustOrders) {
                        if (order instanceof CustomerOrder) options.add(order.toString());
                    }
                    if (showPurchaseOrders) {
                        if (order instanceof PurchaseOrder) options.add(order.toString());
                    }

                }
            }
        }
        else{
            for (Order order : inProgress) {
                if (order != null) {
                    if (showCustOrders && showPurchaseOrders) options.add(order.toString());
                    else if (showCustOrders) {
                        if (order instanceof CustomerOrder) options.add(order.toString());
                    } else if (showPurchaseOrders) {
                        if (order instanceof PurchaseOrder) options.add(order.toString());
                    }

                }
            }
        }
        userInput = new UserInput("-1 - Go Back\n_________________________________________________\n|\tID\t|\tType\t|\tDate\t\t|\tState\t|",
                options, "|_______________________________________________|\nCO - Customer Order \t PO - Purchase Order\n\nView detailed order information by inputting the orderID:");
        int orderID = userInput.getUserInput();
        if(orderID == -1) showMainMenu();
        else {
            Order order;
            if(showOpenOrders){
                order = getOrder(orderID, openOrders);
            }
            else {
                order = getOrder(orderID, inProgress);
            }
            if (order != null) {
                showOrderDetails(order);
            } else {
                System.out.println("Incorrect value entered.");
                showOrderMenu(showCustOrders, showPurchaseOrders, showOpenOrders);
            }
        }
    }

    private void showOrderDetails(Order order){

        Status.CO_STATUS cStatus = null;
        PO_STATUS pStatus = null;
        if(order instanceof CustomerOrder) cStatus = ((CustomerOrder) order).getOrderStatus();
        else if(order instanceof PurchaseOrder) pStatus = ((PurchaseOrder) order).getOrderStatus();

        String setToNext = "1 - Set status to ";
        if(cStatus != null) {
            cStatus = cStatus.getNext();
            setToNext += cStatus;
        }
        else if( pStatus != null) {
            pStatus = pStatus.getNext();
            setToNext += pStatus;
        }

        String details = order.getDetails();
        List<String> options = new ArrayList<>();
        options.add(details);
        options.add("-1 - Go Back");
        options.add(setToNext);
        options.add("2 - Set status to ON_HOLD");
        options.add("3 - Manually set the order of this status");
        options.add("4 - Cancel Order");

        userInput = new UserInput("Order Details: ", options, null);
        int input = userInput.getUserInput();

        switch(input){
            case -1:
                showOrderMenu(true, true, true);
                break;
            case 1:
                if(order instanceof CustomerOrder) ((CustomerOrder) order).updateOrder();
                else if (order instanceof PurchaseOrder) ((PurchaseOrder) order).updateOrder();
                break;
            case 2:
                if(getUserConfirmation("Are you sure you want to set this order to ON_HOLD")) {
                    if (order instanceof CustomerOrder) ((CustomerOrder) order).setStatus(Status.CO_STATUS.ON_HOLD);
                    else if (order instanceof PurchaseOrder) ((PurchaseOrder) order).setStatus(PO_STATUS.ON_HOLD);
                }
                break;
            case 3:
                manuallySetState(order);
                break;
            case 4:
                if(getUserConfirmation("Are you sure you want to cancel this order?")){
                    cancelOrder(order);
                    System.out.println("Order cancelled!");
                }
                break;
        }

        showMainMenu();
    }

    private void manuallySetState(Order order) {
        CustomerOrder co = null;
        PurchaseOrder po = null;

        List<String> options = new ArrayList<>();
        options.add("-1 Go back");
       if(order instanceof CustomerOrder){
           for(int i = 0; i< Status.CO_STATUS.values().length; i++){
               options.add(i+1 + " - " + Status.CO_STATUS.values()[i]);
           }
           co = (CustomerOrder) order;
       }
       else if(order instanceof PurchaseOrder){
           for(int i = 0; i< PO_STATUS.values().length; i++){
               options.add(i+1 + " - " + PO_STATUS.values()[i]);
           }
           po = (PurchaseOrder) order;
       }

        userInput = new UserInput("Manually set the sate of orderID: " + order.getOrderID(), options,null);

       int input = userInput.getUserInput();
       if(input == -1) showOrderDetails(order);
       else if(co!=null && input >=1 && input <= Status.CO_STATUS.values().length){
           co.setStatus(Status.CO_STATUS.values()[input-1]);
       }
       else if(po!=null && input >=1 && input <= Status.PO_STATUS.values().length){
           po.setStatus(Status.PO_STATUS.values()[input-1]);
       }
       else{
           System.out.println("Invalid input.");
       }
    }

    private boolean getUserConfirmation(String query){
        Scanner s = new Scanner(System.in);


        while(true){
            System.out.print(query + " (y/n)");
            String input = s.next();

            if(input.equals("y")) {
                return true;
            }
            else if(input.equals("n")) {
                return false;
            }

        }
    }
    */
    //endregion TEXT USER INTERFACE
}
