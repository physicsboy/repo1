package warehouse;

import com.google.gson.Gson;
import warehouse.product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Handles the warehouse database
 *
 * @author Alex, 12/01/2017
 */
public class Database {

    //creating variables for use
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/warehouse";
    private static final String USER = "newuser";
    private static final String PASS = "password";

    //creating final String variables for all table names and columns to prevent typos
    private final String PURCHASE_ORDERS = "orders",
            COLUMN_ORDER_ID = "orderID", SUPPLIER_ID = "supplier_id", DATE_PLACED = "datePlaced", STATUS = "status";
    private final String PRODUCTS = "products",
            COLUMN_PRODUCT_ID = "productID", COLUMN_PRODUCT_JSON = "jsonString";
    private final String ORDER_LINES = "order_line",
            COLUMN_ID = "order_line_id", ORDER_LINE_ORDER_ID = "order_id", ORDER_LINE_PRODUCT_ID = "product_id",
            ORDER_LINE_QUANTITY = "quantity", ORDER_LINE_POROUS = "porous";


    private Connection conn = null;
    private Statement stmt = null;
    private ResultSet resultSet = null;


    private void connectToDatabase() {
        try {
            //STEP 2: Register JDBC driver
            System.out.println("This");
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();

        } catch (Exception e) {
            System.out.println("EXCEPTION");
            System.out.println(e.getMessage());
        }
    }


    /**
     * @param o The order to be added to the database
     */
    public void addToDatabase(PurchaseOrder o) {
        int orderID = o.getOrderID();
        int supplierID = o.getSupplierID();
        java.sql.Date datePlaced = new java.sql.Date(o.getDatePlaced().getTime());
        String status = o.getOrderStatus().toString();
        List<OrderLine> lines = o.getOrder();

        String query = "INSERT INTO " + PURCHASE_ORDERS + " VALUES(" + orderID + ", " + supplierID + ", " + datePlaced + ", " + status + ");";
        try {
            connectToDatabase();
            stmt.executeQuery(query);

            for (OrderLine ol : lines) {
                int productID = ol.getProduct().getProductID();
                int qty = ol.getQuantity();
                boolean porous = ol.isPorousWareNeeded();

                String sql = "INSERT INTO " + ORDER_LINES + "(" + ORDER_LINE_ORDER_ID + ", " + ORDER_LINE_QUANTITY + ",  "
                        + ORDER_LINE_POROUS + ") VALUES (" + productID + ", " + qty + ", " + porous + ");";
                stmt.executeQuery(sql);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }finally {
            closeConnection();
        }

    }

    /**
     * Adds the product to the database
     *
     * @param p the product to be stored in the database
     */
    public void addToDatabase(Product p) {
        Gson gson = new Gson();
        String json = gson.toJson(p);

        String query = "INSERT INTO " + PRODUCTS + " VALUES(" + p.getProductID() + ", " + json + ");";
        try {
            connectToDatabase();
            stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        } finally {
            closeConnection();
        }
    }


    /**
     * @param showInProgress orders in progress are included if true
     * @param showOpen       open orders are included if true
     * @return a list of Purchase Orders matching the criteria given in the arguments
     */
    List<PurchaseOrder> getOrders(boolean showOpen, boolean showInProgress) {
        List<PurchaseOrder> orders = new LinkedList<>();

        try {
            connectToDatabase();
            String query = "SELECT * FROM " + PURCHASE_ORDERS;
            ResultSet rs = stmt.executeQuery(query);

            try {
                while (rs.next()) {
                    int orderID = rs.getInt(COLUMN_ORDER_ID);
                    int supplierID = rs.getInt(SUPPLIER_ID);
                    Date datePlaced = rs.getDate(DATE_PLACED);

                    Status.PO_STATUS status;
                    switch (rs.getString(STATUS).toLowerCase()) {
                        case ("ordered"):
                            status = Status.PO_STATUS.ORDERED;
                            break;
                        case ("arrived"):
                            status = Status.PO_STATUS.ARRIVED;
                            break;
                        case ("unpacked"):
                            status = Status.PO_STATUS.UNPACKED;
                            break;
                        case ("on_hold"):
                            status = Status.PO_STATUS.ON_HOLD;
                            break;
                        default:
                            status = Status.PO_STATUS.ORDERED;
                            break;
                    }


                    //pass in empty arrayList
                    PurchaseOrder po = new PurchaseOrder(supplierID, new ArrayList<>());
                    po.setStatus(status);
                    po.setDatePlaced(datePlaced);


                    //Get OrderLines for the PO
                    String sqlQuery = "SELECT * FROM " + ORDER_LINES + " JOIN " + PRODUCTS +
                            " ON " + ORDER_LINES + "." + ORDER_LINE_PRODUCT_ID + " = " +
                            PRODUCTS + "." + COLUMN_PRODUCT_ID + " WHERE " + ORDER_LINE_ORDER_ID + "=" + orderID;
                    ResultSet rs2 = stmt.executeQuery(sqlQuery);
                    //get all the order lines in the PO
                    while (rs2.next()) {
                        int qty = rs2.getInt(ORDER_LINE_QUANTITY);
                        boolean porous = rs2.getBoolean(ORDER_LINE_POROUS);
                        String productJson = rs2.getString(COLUMN_PRODUCT_JSON);
                        Product p = new Gson().fromJson(productJson, Product.class);
                        OrderLine ol = new OrderLine(p, qty, porous);
                        po.addNewOrderLine(ol);
                    }

                    if (po.getOrderStatus() == Status.PO_STATUS.ARRIVED && showOpen) {
                        orders.add(po);
                    } else if (showInProgress) {
                        orders.add(po);
                    }
                }

                return orders;

            } catch (SQLException | OrderLine.OutOfStockException e) {
                System.out.println("Failed...");
                System.out.print(e.getMessage());
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            closeConnection();
        }

        return null;
    }


    void updateStockLevels(int productID, int newIn) {
        try {
            connectToDatabase();


        } catch (Exception e) {

        } finally {
            closeConnection();
        }
    }


  /*  List<Order> getAllFromDatabase(){
        String query = "SELECT * FROM " + ORDER_TABLE + ";";
    }*/

    void removeFromDatabase(int orderID) {

    }


    private void closeConnection() {
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException se2) {

        }// nothing we can do
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }//end finally try
    }
}
