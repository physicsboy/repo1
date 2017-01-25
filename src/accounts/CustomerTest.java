package accounts;

import org.junit.Before;
import org.junit.Test;
import warehouse.Status;

import java.util.Date;


import static org.junit.Assert.assertEquals;
import static warehouse.Status.CUST_STATUS.DORMANT;

/**
 * Created by Administrator on 03/01/2017.
 */
public class CustomerTest {

    Customer c;
    Date dob = new Date(300000000);

    @Before
    public void setUp() throws Exception {
        c = new Customer("Joe", dob);
    }

    @Test
    public void getCustomerID() throws Exception {
        assertEquals(1, c.getCustomerID());
    }

    @Test
    public void getBalance() throws Exception {
        assertEquals(0, c.getBalance(), 0.01);
    }

    @Test
    public void getStatus() throws Exception {
        assertEquals(Status.CUST_STATUS.ACTIVE, c.getStatus());
    }

    @Test
    public void getLastModified() throws Exception {
        assertEquals(new Date(), c.getLastModified());
    }

    @Test
    public void chargeOrder() throws Exception {
        c.chargeOrder(9.99F);
        assertEquals(9.99F, c.getBalance(), 0.000001);
    }

    @Test
    public void payForItems() throws Exception {
        c.chargeOrder(9.99F);
        c.payForItems(8.99F);
        assertEquals(1.00, c.getBalance(), 0.001);
    }

    @Test
    public void setStatus() throws Exception {
        c.setStatus(DORMANT);
        assertEquals(DORMANT, c.getStatus());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("Joe", c.getName());
    }

    @Test
    public void getAddresses() throws Exception {
        assertEquals(0, c.getAddresses().size());
    }

    @Test
    public void addAddress() throws Exception {
        Address a = new Address("A", "b", "c", "d", "e");
        c.addAddress(a);

        assertEquals(1, c.getAddresses().size());
        assertEquals("A, b, c, d, e", c.getAddresses().get(0).getAddress());
    }


    @Test
    public void getDob() throws Exception {
        assertEquals(dob, c.getDob());
    }

}