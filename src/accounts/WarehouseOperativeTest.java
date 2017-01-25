package accounts;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 04/01/2017.
 */
public class WarehouseOperativeTest {

    private WarehouseOperative wh;

    @Before
    public void setup(){
        wh = new WarehouseOperative("Dave", new Date(5420000), 17, true, new Date(1762828222));
    }



    @Test
    public void isForkliftTrained() throws Exception {
        assertEquals(true, wh.isForkliftTrained());
    }

    @Test
    public void setForkliftTrained() throws Exception {
        wh.setForkliftTrained(false);
        assertEquals(false, wh.isForkliftTrained());
    }

    @Test
    public void getTrainingDate() throws Exception {
        assertEquals(new Date(1762828222) ,wh.getTrainingDate());
    }

    @Test
    public void setTrainingDate() throws Exception {
        wh.setTrainingDate(new Date(1234567890));
        assertEquals(new Date(1234567890), wh.getTrainingDate());
    }

    @Test
    public void getEmployeeID() throws Exception {
        assertEquals(5, wh.getEmployeeID());
    }

    @Test
    public void getPayrollNo() throws Exception {
        assertEquals(17, wh.getPayrollNo());
    }

    @Test
    public void setPayrollNo(){
        wh.setPayrollNo(10);
        assertEquals(10, wh.getPayrollNo());
    }

}