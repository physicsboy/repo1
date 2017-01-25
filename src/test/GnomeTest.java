package test;

import org.junit.Before;
import org.junit.Test;
import warehouse.product.Gnome;
import warehouse.product.Location;
import warehouse.product.Product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Administrator on 03/01/2017.
 */
public class GnomeTest {

    private Gnome gnome;


    @Before
    public void setUp() throws Exception {
        gnome = new Gnome("ABC", "ADE", new Location(7, 3), Product.Colour.BLUE,
                10,  3, 1.25F, 9.99F);
    }

    @Test
    public void setTheme() throws Exception {
        gnome.setTheme(Gnome.Theme.GERMAN);
        assertNotNull(gnome.getTheme());
    }

    @Test
    public void getTheme() throws Exception {
        gnome.setTheme(Gnome.Theme.GERMAN);
        assertEquals(Gnome.Theme.GERMAN, gnome.getTheme());
    }


    @Test
    public void getName() throws Exception {
        assertEquals("ABC", gnome.getName());
    }

    @Test
    public void getDescription() throws Exception {
        assertEquals("ADE", gnome.getDescription());
    }

    @Test
    public void getLocation() throws Exception {
        assertEquals(new Location(7, 3), gnome.getLocation());
    }

    @Test
    public void getNumInStock() throws Exception {
        assertEquals(10, gnome.getNumInStock());
    }

    @Test
    public void getNumInStockWithPorous() throws Exception {
        assertEquals(3, gnome.getNumInStockWithPorous());
    }

    @Test
    public void getNumWithoutPorous() throws Exception {
        assertEquals(7, gnome.getNumWithoutPorous());
    }

    @Test
    public void getWeight() throws Exception {
        assertEquals(1.25, gnome.getWeight(), 0.01);
    }

    @Test
    public void getPrice() throws Exception {
        assertEquals(9.99, gnome.getPrice(), 0.01);
    }

    @Test
    public void setDescription() throws Exception {
        gnome.setDescription("ABC");
        assertEquals("ABC", gnome.getDescription());
    }

    @Test
    public void setLocation() throws Exception {
        Location l = new Location(4,5);
        gnome.setLocation(l);
        assertEquals(l, gnome.getLocation());
    }

    @Test
    public void setWeight() throws Exception {
        gnome.setWeight(10.3F);
        assertEquals(10.3F, gnome.getWeight(), 0.01);
    }

    @Test
    public void setPrice() throws Exception {
        gnome.setPrice(13.29F);
        assertEquals(13.29F, gnome.getPrice(), 0.0001);
    }

    @Test
    public void updateNumInStock() throws Exception {
        gnome.updateNumInStock(100);
        assertEquals(110, gnome.getNumInStock());
    }

    @Test
    public void updateNumWithPorous() throws Exception {
        gnome.updateNumWithPorous(17);
        assertEquals(20, gnome.getNumInStockWithPorous());
    }

}