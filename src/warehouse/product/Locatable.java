package warehouse.product;

/**
 * Created by Alex on 05/01/2017.
 *
 * Locatable interface for objects which have a location
 */
public interface Locatable {

    int getRowNum();
    int getColumnNum();
    @Override
    String toString();

}
