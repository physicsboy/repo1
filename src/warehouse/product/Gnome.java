package warehouse.product;

/**
 * Created by Alex on 03/01/2017.
 *
 */
public class Gnome extends Product {

    private Theme theme = null;
    private int numWithPorous;

    public enum Theme{GARDEN, ZOMBIE, GERMAN, HISTORICAL}

    public Gnome(String name, String description, Location location, Product.Colour colour, int numInStock,
                 int numWithPorous, float weight, float price  ) {
        super(name, description, location, colour, numInStock, weight, price);
        this.numWithPorous = numWithPorous;
    }

    /**
     *
     * @return the theme of the gnome, one of either GARDEN, ZOMBIE, GERMAN, HISTORICAL or null;
     */
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public int getNumInStockWithPorous() {
        return numWithPorous;
    }

    public int getNumWithoutPorous(){
        return numInStock - numWithPorous;
    }

    public void updateNumWithPorous(int newIn){
        this.numWithPorous += newIn;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            return getProductID() == ((Gnome) obj).getProductID();
        }catch (ClassCastException | NullPointerException e){
            return false;
        }
    }
}
