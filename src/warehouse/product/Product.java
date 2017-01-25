package warehouse.product;


import java.io.Serializable;

/**
 * Created by Alex on 03/01/2017.
 *
 * Abstract class - Can only instantiate a specific product for sale.
 */
abstract public class Product implements Serializable{

    private static int numProducts;

    private String name, description;
    private int productID;
    int numInStock;
    private float weight, price;
    private Colour colour;
    private Location location;

    public enum Colour{BLUE, BLACK, RED, GREEN, PINK, YELLOW, PURPLE, WHITE}

    public Product(String name, String description, Location location, Colour colour, int numInStock, float weight, float price) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.colour = colour;
        this.numInStock = numInStock;
        this.weight = weight;
        this.price = price;

        productID = ++numProducts;
    }

    public String getName() {
        return name;
    }

    public int getProductID(){return productID;}

    public String getDescription() {
        return description;
    }

    public Location getLocation() {
        return location;
    }

    public int getNumInStock() {
        return numInStock;
    }

    public float getWeight() {
        return weight;
    }

    public float getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void updateNumInStock(int newIn){
        this.numInStock += newIn;
    }

    @Override
    abstract public boolean equals(Object obj);
}
