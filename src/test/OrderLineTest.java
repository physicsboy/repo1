package test;

import org.junit.Before;
import org.junit.Test;
import warehouse.OrderLine;
import warehouse.product.Gnome;
import warehouse.product.HotTub;
import warehouse.product.Location;
import warehouse.product.Product;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 04/01/2017.
 */
public class OrderLineTest {

    private OrderLine ol, ol2;
    private Product aGnome, aHotTub;

    @Before
    public void setUp() throws Exception {
        aGnome = new Gnome("Steve", "Blue/Black fishing rod on a motorcycle - female",
                new Location(3,7), Product.Colour.BLUE, 17,
                7,  7, 5.99F);
        aHotTub = new HotTub("JetStar34", "Mother of all bubbles", new Location(7,3),
                Product.Colour.PINK, 200, 100, 179.99F, true, 120, 12);

        ol = new OrderLine(aGnome, 7, true);
        ol2 = new OrderLine(aHotTub, 15, false);
    }

    @Test
    public void exceptionTest() {
        Gnome aGnome = new Gnome("Steve", "Blue/Black fishing rod on a motorcycle - female",
                new Location(4,5), Product.Colour.BLUE, 17,
                7, 7, 5.99F);

        try {
            OrderLine ol = new OrderLine(aGnome, 1, true);
            ol = new OrderLine(aGnome, 11, false);
        } catch (OrderLine.OutOfStockException ex){
            System.out.println("EXCEPTION - WANTED");
        }
        finally {
            System.out.print("Test over...");
        }
    }


    @Test
    public void getPrice(){
        assertEquals(5.99F, ol.getPrice(), 0.01);
        assertEquals(179.99F, ol2.getPrice(), 0.01);
    }

    @Test
    public void getSubtotal(){
        float olSub = 5.99F * 7;
        float ol2Sub = 179.99F * 15;

        assertEquals(olSub, ol.getSubtotal(), 0.001);
        assertEquals(ol2Sub, ol2.getSubtotal(), 0.001);
    }

    @Test
    public void setProduct() throws Exception {
        ol.setProduct(aHotTub);
        assertEquals(aHotTub, ol.getProduct());
    }

    @Test
    public void setQuantity() throws Exception {
        ol.setQuantity(2);
        assertEquals(2, ol.getQuantity());
    }

    @Test
    public void setPorousWareNeeded() throws Exception {
        ol.setPorousWareNeeded(false);
    }

    @Test
    public void getProduct() throws Exception {
        assertEquals(aGnome, ol.getProduct());
    }

    @Test
    public void getQuantity() throws Exception {
        assertEquals(7, ol.getQuantity());
    }

    @Test
    public void isPorousWareNeeded() throws Exception {
        assertEquals(true, ol.isPorousWareNeeded());
    }

}