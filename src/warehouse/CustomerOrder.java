package warehouse;

import accounts.Address;
import accounts.Customer;
import accounts.StatusListener;
import warehouse.product.Product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 03/01/2017.
 *
 * Holds {@link Order} placed by a {@link Customer}
 * OrderLines will automatically be put into the shortest order for collection
 */
public class CustomerOrder extends Order implements Status {

    private Customer customer;
    private Address address;
    private Date datePicked, dateDespatched, datePacked;
    private CO_STATUS status;
    private StatusListener listener;


    public CustomerOrder(List<OrderLine> products, Customer customer, Address address) {
        super(products);
        this.customer = customer;
        this.address = address;

        updateStockLevels();
        setDatePlaced(new Date());


        listener = new StatusListener();
        listener.notifyUpdate(customer, this);

        //set the status to PLACED only once the shortest route has been determined
        setShortestRoute();
        status = CO_STATUS.PLACED;
        listener.notifyUpdate(customer, this);
    }



    public Customer getCustomer(){
        return customer;
    }

    public void updateOrder(){
        status = status.getNext();
        listener.notifyUpdate(customer, this);
    }

    public void setStatus(CO_STATUS s){
        status = s;
        listener.notifyUpdate(customer, this);

        switch (status){
            case PICKED:
                datePicked = new Date();
                break;
            case DESPATCHED:
                dateDespatched = new Date();
                break;
            case PACKED:
                datePacked = new Date();
                break;
        }
    }

    public String getPostageLabel() {
        return customer.getName() + ", " + address.getAddress();
    }

    public void changeAddress(Address newAddress) {
        this.address = newAddress;
    }

    public Date getDatePicked() {
        return datePicked;
    }

    public Date getDateDespatched() {
        return dateDespatched;
    }

    public CO_STATUS getOrderStatus(){
        return status;
    }


    @Override
    public String[] getTableArray() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new String[] {String.valueOf(getOrderID()), "CO", sdf.format(getDatePlaced()), status.toString()};
    }

    @Override
    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("OrderID: %d\t\tStatus: %s\t\tForklift Needed: %s",
                getOrderID(), status, isForkliftNeeded()));
        String id = "ID", name = "Name", desc = "Desc", qty = "Qty", porous = "Porous", area = "Area";
        sb.append(String.format("\n|\t%s\t|\t%10.10s\t|\t%25.25s\t|\t%s\t|\t%s\t|\t%s\t|",
                id, name, desc, qty, porous, area));
        for(OrderLine ol : products){
            Product p = ol.getProduct();

            sb.append(String.format("\n|\t%d\t|\t%10.10s\t|\t%25.25s\t|\t%d\t|\t%s\t|\t%s\t|",
                    p.getProductID(), p.getName(), p.getDescription(), ol.getQuantity(), ol.isPorousWareNeeded(),
                    p.getLocation().toString()));
        }

        return sb.toString();
    }

    @Override
    void updateStockLevels(){
        //go through each order line and update stock
        for(OrderLine orderLine : products){
            Product p = orderLine.getProduct();
            //remove quantity ordered from stock levels
            p.updateNumInStock(-orderLine.getQuantity());
        }
    }

    @Override
    public void onOrderDelay() {
        status = CO_STATUS.ON_HOLD;
        listener.notifyUpdate(customer, this);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("|\t%d\t|\tCO\t\t|\t%s\t|\t%s\t|", getOrderID(), sdf.format(getDatePlaced()), status);
    }

    @Override
    public boolean equals(Object obj) {
        try{
            boolean idMatch = getOrderID() == ((CustomerOrder) obj).getOrderID();
            boolean statusMatch = getOrderStatus() == ((CustomerOrder) obj).getOrderStatus();
            boolean isOrderMatch = (products == ((CustomerOrder) obj).products);
            return idMatch & statusMatch & isOrderMatch;
        } catch (ClassCastException e){
            return false;
        }
    }
}
