package warehouse.product;

/**
 * Created by Alex on 03/01/2017.
 *
 */
public class HotTub extends Product {

    private boolean hasMassage;
    private int capacity = 0, numPeople;

    public HotTub(String name, String description, Location location, Product.Colour colour, int numInStock, float weight, float price, boolean hasMassage, int capacity, int numPeople) {
        super(name, description, location, colour, numInStock, weight, price);
        this.hasMassage = hasMassage;
        this.capacity = capacity;
        this.numPeople = numPeople;
    }

    /**
     *
     * @return true if the HotTub has massage functionality, false otherwise
     */
    public boolean getHasMassage() {
        return hasMassage;
    }

    public void setHasMassage(boolean hasMassage) {
        this.hasMassage = hasMassage;
    }

    /**
     *
     * @return the capacity of the hot tub in Litres
     */
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumPeople() {
        return numPeople;
    }

    public void setNumPeople(int numPeople) {
        this.numPeople = numPeople;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return getProductID() == ((HotTub) obj).getProductID();
        }catch (ClassCastException | NullPointerException e){
            return false;
        }
    }
}
