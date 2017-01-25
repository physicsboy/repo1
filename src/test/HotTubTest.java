package test;

import org.junit.Before;
import org.junit.Test;
import warehouse.product.HotTub;
import warehouse.product.Location;
import warehouse.product.Product;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 03/01/2017.
 */
public class HotTubTest {

    HotTub ht;

    @Before
    public void setUp() throws Exception {
        ht = new HotTub("Bubble 120", "BUBBLES!", new Location(5,4), Product.Colour.PINK, 17,150.00F, 179.99F, true, 1000, 6 );
    }

    @Test
    public void getHasMassage() throws Exception {
        assertEquals(true, ht.getHasMassage());
    }

    @Test
    public void setHasMassage() throws Exception {
        ht.setHasMassage(false);
        assertEquals(false, ht.getHasMassage());
    }

    @Test
    public void getCapacity() throws Exception {
        assertEquals(1000, ht.getCapacity());
    }

    @Test
    public void setCapacity() throws Exception {
        ht.setCapacity(561);
        assertEquals(561, ht.getCapacity());
    }

    @Test
    public void getNumPeople() throws Exception {
        assertEquals(6, ht.getNumPeople());
    }

    @Test
    public void setNumPeople() throws Exception {
        ht.setNumPeople(59);
        assertEquals(59, ht.getNumPeople());
    }

}