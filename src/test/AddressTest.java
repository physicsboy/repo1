package test;

import accounts.Address;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 03/01/2017.
 */
public class AddressTest {

    private Address address;

    @Before
    public void setUp() throws Exception {
        address = new Address("123", "A Road", "A place", "DEVON", "DV327PZ");
    }

    @Test
    public void getAddress() throws Exception {
        assertEquals("123, A Road, A place, DEVON, DV327PZ", address.getAddress());
    }

}