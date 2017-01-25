package warehouse;

import warehouse.product.Gnome;
import warehouse.product.Product;

/**
 * Created by Alex on 03/01/2017.
 *
 * Single part of each order. If some of the same product requires porous-ware and some doesn't then use separate OrderLines.
 */
public class OrderLine {


    private transient Product product;
    private int quantity;
    private float subtotal, price;
    private boolean porousWareNeeded;

    public OrderLine(Product product, int quantity, boolean porousWareNeeded) throws OutOfStockException {
        this.product = product;
        this.quantity = quantity;
        this.porousWareNeeded = porousWareNeeded;

        if(!checkItemInStock(product)) throw new OutOfStockException(product);

        price = product.getPrice();
        subtotal = price * quantity;
    }

    private boolean checkItemInStock(Product p)  {
        if(quantity > p.getNumInStock()) return false;
        if(p instanceof Gnome){
            if(porousWareNeeded){
                return (((Gnome) p).getNumInStockWithPorous() >= quantity);
            }
            else{
                return (((Gnome) p).getNumWithoutPorous() >= quantity);
            }
        }
        else{
            return true;
        }
    }

    /**
     * @return the subtotal for the whole order line calculated as price * quantity
     */
    public float getSubtotal() {
        return subtotal;
    }

    /**
     *
     * @return the price of the individual product
     */
    public float getPrice() {
        return price;
    }

    public void setProduct(Product newProduct) throws OutOfStockException {
        if(!checkItemInStock(newProduct)) {
            throw new OutOfStockException(newProduct);
        }
        else{
            product = newProduct;
            this.price = product.getPrice();
            this.subtotal = price * quantity;
        }

    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subtotal = quantity * price;
    }

    public void setPorousWareNeeded(boolean porousWareNeeded) {
        this.porousWareNeeded = porousWareNeeded;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isPorousWareNeeded() {
        return porousWareNeeded;
    }


    public String[] getTableArray(){
        return new String[]{String.valueOf(product.getProductID()), product.getName(), product.getLocation().toString(),
                String.valueOf(quantity), String.valueOf(isPorousWareNeeded())};
    }


    /**
     * Exception class that is thrown when the desired items are not currently available in stock
     */
    public class OutOfStockException extends Exception{

        OutOfStockException(Product p){
            super();
            if(p instanceof Gnome) {
                Gnome gnome = (Gnome) p;
                System.out.println(String.format("This item has not got enough stock, there are %d " +
                        "of this item in stock with porous and %d in stock without porous.",
                        gnome.getNumInStockWithPorous(), gnome.getNumWithoutPorous()));
            }
            else{
                System.out.println(String.format("This item has not got enough stock, there are %d" +
                        " of this item in stock", p.getNumInStock()));
            }
        }

        public OutOfStockException(String message) {
            super(message);
        }

        public OutOfStockException(String message, Throwable cause) {
            super(message, cause);
        }

        public OutOfStockException(Throwable cause) {
            super(cause);
        }

        protected OutOfStockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
        }
    }

}

