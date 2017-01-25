package test;

import org.junit.Before;
import org.junit.Test;
import warehouse.product.Location;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 05/01/2017.
 */
public class LocationTest {

    Location l;

    @Before
    public void setUp() throws Exception {
        l = new Location(13, 5);
    }

    @Test
    public void setRow() throws Exception {
        l.setRow(7);
        assertEquals(7, l.getRowNum());
    }

    @Test
    public void setColumn() throws Exception {
        l.setColumn(5);
        assertEquals(5, l.getColumnNum());
    }

    @Test
    public void getRowNum() throws Exception {
        assertEquals(13, l.getRowNum());
    }

    @Test
    public void getColumnNum() throws Exception {
        assertEquals(5, l.getColumnNum());
    }

}