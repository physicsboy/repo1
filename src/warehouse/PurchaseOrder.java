package warehouse;

import accounts.StatusListener;
import warehouse.product.Product;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static warehouse.Status.PO_STATUS.*;

/**
 * Created by Administrator on 03/01/2017.
 */
public class PurchaseOrder extends Order implements Status{

    private int supplierID;
    private Date dateArrived;
    private boolean isUnpacked;
    private PO_STATUS status;

    private StatusListener listener;


    public PurchaseOrder(int supplierID, List<OrderLine> products) {
        super(products);
        this.supplierID = supplierID;

        status = ORDERED;
        setDatePlaced(new Date());
        listener = new StatusListener();
        listener.notifyUpdate(this);
    }

    public int getSupplierID() {
        return supplierID;
    }

    public Date getDateArrived() {
        return dateArrived;
    }

    public Status.PO_STATUS getOrderStatus(){
        return status;
    }

    public void setStatus(Status.PO_STATUS s){
        status = s;
        listener.notifyUpdate(this);

        switch(status){
            case UNPACKED:
                setUnpacked(true);
                break;
            case ARRIVED:
                dateArrived = new Date();
                break;
            case ORDERED:
                setDatePlaced(new Date());
                break;
        }
    }

    public void updateOrder(){
        status = status.getNext();
        listener.notifyUpdate(this);
    }

    public boolean isUnpacked() {
        return isUnpacked;
    }

    public List<OrderLine> getProducts() {
        return products;
    }

    public void setDateArrived(Date dateArrived) {
        this.dateArrived = dateArrived;
        status = ARRIVED;
    }


    private void setUnpacked(boolean unpacked) {
        isUnpacked = unpacked;
        updateStockLevels();
    }


    @Override
    public String[] getTableArray() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return new String[] {String.valueOf(getOrderID()), "PO", sdf.format(getDatePlaced()), status.toString()};
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
    public void updateStockLevels() {
        for(OrderLine ol : products){
            Product p = ol.getProduct();
            p.updateNumInStock(ol.getQuantity());
        }

    }

    @Override
    public void onOrderDelay() {
        status = ON_HOLD;
        listener.notifyUpdate(this);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("|\t%d\t|\tPO\t\t|\t%s\t|\t%s\t|", getOrderID(), sdf.format(getDatePlaced()), status);
    }

    @Override
    public boolean equals(Object obj) {
        try{
            boolean idMatch = getOrderID() == ((PurchaseOrder) obj).getOrderID();
            boolean statusMatch = getOrderStatus() == ((PurchaseOrder) obj).getOrderStatus();
            return idMatch & statusMatch;
        } catch (ClassCastException e){
            return false;
        }
    }
}
