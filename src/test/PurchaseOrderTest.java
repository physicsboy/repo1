package test;

import org.junit.Before;
import org.junit.Test;
import warehouse.OrderLine;
import warehouse.PurchaseOrder;
import warehouse.product.Gnome;
import warehouse.product.HotTub;
import warehouse.product.Location;
import warehouse.product.Product;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static warehouse.Status.PO_STATUS.ARRIVED;
import static warehouse.Status.PO_STATUS.UNPACKED;

/**
 * Created by Administrator on 04/01/2017.
 */
public class PurchaseOrderTest {

    private PurchaseOrder po;
    private ArrayList<OrderLine> products;

    @Before
    public void setUp() throws Exception {
        Gnome aGnome = new Gnome("Steve", "Blue/Black fishing rod on a motorcycle - female",
                new Location(4,5), Product.Colour.BLUE, 17,
                7, 7, 5.99F);
        HotTub aHotTub = new HotTub("JetStar34", "Mother of all bubbles",
                new Location(7,5), Product.Colour.PINK, 200, 100,
                179.99F, true, 120, 12);


        OrderLine ol = null, ol2 = null;
        try {
            ol = new OrderLine(aGnome, 7, true);
            ol2 = new OrderLine(aHotTub, 15, false);
        } catch (OrderLine.OutOfStockException e) {
        }

        products = new ArrayList<>();
        if (ol != null) products.add(ol);
        if (ol2 != null) products.add(ol2);

        po = new PurchaseOrder(123, products);
    }

    @Test
    public void getOrderID() throws Exception {
       assertEquals(15, po.getOrderID());
    }

    @Test
    public void getSupplierID() throws Exception {
        assertEquals(123, po.getSupplierID());
    }

    @Test
    public void getDateArrived() throws Exception {
        assertEquals(null, po.getDateArrived());

        po.setStatus(ARRIVED);
        assertEquals(new Date(), po.getDateArrived());
    }

    @Test
    public void isUnpacked() throws Exception {
        assertEquals(false, po.isUnpacked());
        po.setStatus(UNPACKED);
        assertEquals(true, po.isUnpacked());
    }

    @Test
    public void getProducts() throws Exception {
        assertEquals(products, po.getProducts());
    }

    @Test
    public void setDateArrived() throws Exception {
        po.setDateArrived(new Date());
        assertEquals(new Date(), po.getDateArrived());
    }

    @Test
    public void onOrderDelay() throws Exception {

    }

}