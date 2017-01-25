package warehouse;

import accounts.Address;
import accounts.Customer;
import org.junit.Before;
import org.junit.Test;
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
public class WarehouseTest {

    private CustomerOrder order1, order2;
    private PurchaseOrder order3;
    private Warehouse wh;


    @Before
    public void setUp() throws Exception {
        Product aGnome = new Gnome("Steve", "Blue/Black fishing rod on a motorcycle - female",
                new Location(3,1), Product.Colour.BLUE, 17,
                7,  7, 5.99F);
        Product aHotTub = new HotTub("JetStar34", "Mother of all bubbles",
                new Location(7,3), Product.Colour.PINK, 200,
                100, 179.99F, true, 120, 12);

        OrderLine ol = new OrderLine(aGnome, 7, true);
        OrderLine ol2 = new OrderLine(aHotTub, 15, false);

        Address address = new Address("123", "A Road", "A place", "DEVON", "DV327PZ");
        Customer customer = new Customer("John", new Date());

        List<OrderLine> products = new ArrayList<>();
        products.add(ol);
        products.add(ol2);
        order1 = new CustomerOrder(products, customer, address);

        products.remove(ol);
        order2 = new CustomerOrder(products, customer, address);

        order3 = new PurchaseOrder(12, products);

        wh = Warehouse.getInstance();
        wh.addOrder(order1);
        wh.addOrder(order2);
        wh.addOrder(order3);
    }

    @Test
    public void getNextOrder() throws Exception {
        assertEquals(3, wh.getOpenOrders().size());
        assertEquals(order1, wh.getNextOrder());
        assertEquals(2, wh.getOpenOrders().size());
        assertEquals(order2, wh.getNextOrder());
        assertEquals(1, wh.getOpenOrders().size());
        assertEquals(order3, wh.getNextOrder());
        assertEquals(0, wh.getOpenOrders().size());
    }


    @Test
    public void cancelOrder() throws Exception {
        wh.cancelOrder(order2);
        assertEquals(2, wh.getOpenOrders().size());
    }

}