package test;

import accounts.Address;
import accounts.Customer;
import org.junit.Before;
import org.junit.Test;
import warehouse.CustomerOrder;
import warehouse.OrderLine;
import warehouse.Status;
import warehouse.product.Gnome;
import warehouse.product.HotTub;
import warehouse.product.Location;
import warehouse.product.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


/**
 * Created by Administrator on 04/01/2017.
 */
public class CustomerOrderTest {

    private CustomerOrder co;
    private Address address;
    private Gnome aGnome;

    @Before
    public void setup() {
        aGnome = new Gnome("Steve", "Blue/Black fishing rod on a motorcycle - female", new Location(5,4),
                Product.Colour.BLUE, 17, 7, 7, 5.99F);
        HotTub aHotTub = new HotTub("JetStar34", "Mother of all bubbles", new Location(5,4), Product.Colour.PINK, 200, 100, 179.99F, true, 120, 12);

        OrderLine ol = null, ol2 = null;
        try {
            ol = new OrderLine(aGnome, 7, true);
            ol2 = new OrderLine(aHotTub, 15, false);
        } catch (OrderLine.OutOfStockException e) {
        }

        List<OrderLine> orders = new ArrayList<>();
        if (ol != null) orders.add(ol);
        if (ol2 != null) orders.add(ol2);

        address = new Address("123", "1", "2", "3", "4");


        co = new CustomerOrder(orders, new Customer("John", new Date()), address);
    }



    @Test
    public void getPostageLabel() throws Exception {
        assertEquals("John, " + address.getAddress(), co.getPostageLabel());
    }

    @Test
    public void changeAddress() throws Exception {
        Address newAdd = new Address("456", "7", "8", "9", "10");
        co.changeAddress(newAdd);
        assertEquals("John, " + newAdd.getAddress(), co.getPostageLabel());
    }

    @Test
    public void getDatePlaced() throws Exception {
        assertEquals(new Date(), co.getDatePlaced());
    }

    @Test
    public void getDatePicked() throws Exception {
        co.setStatus(Status.CO_STATUS.PICKED);
        assertEquals(new Date(), co.getDatePicked());
    }

    @Test
    public void getDateDespatched() throws Exception {
        co.setStatus(Status.CO_STATUS.DESPATCHED);
        assertEquals(new Date(), co.getDateDespatched());
    }


    @Test
    public void onOrderDelay() throws Exception {

    }


    @Test
    public void getSubtotal() throws Exception {
        float sub = 5.99F * 7 + 179.99F * 15;

        assertEquals(sub, co.getSubtotal(), 0.001);
    }

    @Test
    public void changeOrder() throws Exception {
        float sub = 5.99F * 6 + 179.99F * 15;
        co.updateOrder(aGnome, 6);
        assertEquals(sub, co.getSubtotal(), 0.01);
    }

    @Test
    public void isForkliftNeeded() throws Exception {

    }

}