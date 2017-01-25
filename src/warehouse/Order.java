package warehouse;

import warehouse.product.Location;
import warehouse.product.Product;
import warehouse.product.TravellingSalesman;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 03/01/2017.
 * <p>
 * superclass of all {@link PurchaseOrder} and {@link CustomerOrder}
 */
abstract public class Order {

    private static int numOrders;

    private int orderID;
    private Date datePlaced;
    List<OrderLine> products;
    private float subtotal;

    Order(List<OrderLine> products) {
        this.products = new ArrayList<>(products);
        datePlaced = new Date();
        calculateSubtotal();

        orderID = ++numOrders;
    }

    public Date getDatePlaced() {
        return datePlaced;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public int getOrderID() {
        return orderID;
    }

    /**
     * @param p           the product to be updated
     * @param newQuantity the new quantity required
     */
    public void updateOrder(Product p, int newQuantity) {
        for (OrderLine ol : products) {
            if (ol.getProduct().equals(p)) {
                p.updateNumInStock(ol.getQuantity());
                if (newQuantity == 0) products.remove(ol);
                else {
                    ol.setQuantity(newQuantity);
                    p.updateNumInStock(-ol.getQuantity());
                }
                break;
            }
        }

        calculateSubtotal();
        setShortestRoute();
    }


    public void addNewOrderLine(OrderLine ol) {
        products.add(ol);
        setShortestRoute();
    }

    /**
     * @return true if a forklift is required to fulfil the order, false if it can be done by hand
     */
    public boolean isForkliftNeeded() {
        float totalWeight = 0.0F;
        for (OrderLine orderLine : products) {
            float orderLineWeight = orderLine.getProduct().getWeight() * orderLine.getQuantity();

            //If an individual part of the order weighs more than 25KG than a forklift is required
            if (orderLineWeight > 25.0) {
                return true;
            }

            totalWeight += orderLineWeight;
        }

        //if the total weight of the order is over 100KG then a forklift is required to move the cart
        return totalWeight > 100.0;
    }

    public List<OrderLine> getOrder() {
        return products;
    }

    private void calculateSubtotal() {
        float total = 0;
        for (OrderLine product : products) {
            total += product.getSubtotal();
        }
        subtotal = total;
    }

    /**
     * Uses {@link warehouse.product.TravellingSalesman} to generate the shortest route
     * between products on different {@link OrderLine} objects in the List. The list of {@link OrderLine} objects
     * is automatically placed in the shortest route for collection
     */
    void setShortestRoute() {

        List<Location> locations = new LinkedList<>();
        for (OrderLine orderLine : products) locations.add(orderLine.getProduct().getLocation());


        TravellingSalesman tsm = new TravellingSalesman(locations);
        locations = new ArrayList<>(tsm.findShortestRoute());
        //locations now holds the optimized route between those locations


        //generate the optimized list of OrderLines from the optimized list of locations
        List<OrderLine> optimizedOrder = new LinkedList<>();
        for (Location location : locations) {
            for (OrderLine ol : products) {
                if (ol.getProduct().getLocation().equals(location)) {
                    optimizedOrder.add(ol);
                    break;
                }
            }
        }
        //set the new optimized list to replace the old list
        products = new ArrayList<>(optimizedOrder);
    }

    void setDatePlaced(Date d){
        datePlaced = d;
    }


    abstract void updateStockLevels();

    abstract public void onOrderDelay();

    abstract public String getDetails();

    abstract public String[] getTableArray();

    @Override
    abstract public String toString();

    @Override
    abstract public boolean equals(Object obj);
}
