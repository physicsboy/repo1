package warehouse;

/**
 * Created by Alex on 05/01/2017.
 *
 * Acts as a placeholder for the possible statuses of {@link CustomerOrder}, {@link PurchaseOrder} and {@link accounts.Customer}
 */
public interface Status {

    enum CO_STATUS{
        PLACED, PICKING, PICKED, PACKING, PACKED, DESPATCHED, ON_HOLD;

        public CO_STATUS getNext(){
            return this.ordinal() < CO_STATUS.values().length - 1 ? CO_STATUS.values()[this.ordinal() + 1] : null;
        }

    }

    enum PO_STATUS{
        ORDERED, ARRIVED, UNPACKED, ON_HOLD;

        public PO_STATUS getNext(){
            return this.ordinal() < PO_STATUS.values().length - 1 ? PO_STATUS.values()[this.ordinal() + 1] : null;
        }

    }

    enum CUST_STATUS{ACTIVE, DORMANT, ON_HOLD, BARRED}
}
