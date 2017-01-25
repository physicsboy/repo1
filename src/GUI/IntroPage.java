package GUI;

import accounts.AccountsTeam;
import accounts.Address;
import accounts.Customer;
import warehouse.*;
import warehouse.product.Gnome;
import warehouse.product.HotTub;
import warehouse.product.Location;
import warehouse.product.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Adam on 04/01/2017.
 * <p>
 * Creates initial Objects for use.
 * Shows the log on screen to access the warehouse inventory system
 */
public class IntroPage {
    private JTextField idField;
    private JPasswordField passField;
    private JFrame frmWelcomePage;

    public static AccountsTeam accountsTeam = new AccountsTeam();

    /**
     * Runs the whole application
     * @param args
     */
    public static void main(String[] args) {

        //create the db and get an instance of the warehouse so that it can listen for changes in status orders
        Database db = new Database();
        Warehouse.getInstance();

        //creating the data
        createDummyData();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IntroPage window = new IntroPage();
                    window.frmWelcomePage.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    private IntroPage() {
        initialize();
    }




    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        // define general frame where everything will be displayed
        frmWelcomePage = new JFrame();
        frmWelcomePage.setTitle("IntroPage");
        frmWelcomePage.setBounds(100, 100, 450, 300);
        frmWelcomePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmWelcomePage.getContentPane().setLayout(null);

        // define dark rectangle to differentiate welcome page from rest of interface
        JPanel panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setBounds(10, 11, 414, 240);
        frmWelcomePage.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel lblWelcomeToThe = new JLabel("... Welcome to the NB Gardens Warehouse System ...");
        lblWelcomeToThe.setBounds(11, 5, 392, 19);
        lblWelcomeToThe.setForeground(Color.WHITE);
        lblWelcomeToThe.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
        panel.add(lblWelcomeToThe);

        JButton btnNewButton = new JButton("Log in...");

        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logIn(e);
            }
        });
        btnNewButton.setBounds(162, 174, 89, 23);
        panel.add(btnNewButton);

        JLabel lblEmployeeid = new JLabel("Employee_ID");
        lblEmployeeid.setBackground(Color.LIGHT_GRAY);
        lblEmployeeid.setForeground(Color.WHITE);
        lblEmployeeid.setBounds(92, 91, 89, 14);
        panel.add(lblEmployeeid);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setHorizontalAlignment(SwingConstants.LEFT);
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBackground(Color.LIGHT_GRAY);
        lblPassword.setBounds(92, 116, 89, 14);
        panel.add(lblPassword);

        /*
         * Each employee will have their own ID to log on with, stored within
         * a database or arraylist of associated IDs and passwords
         */
        idField = new JTextField();

        idField.addKeyListener(new KeyAdapter() {
            // add function of pressing return key to submit the inputted details
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    logIn(e);
                }
            }
        });
        idField.setBounds(183, 88, 127, 20);
        panel.add(idField);
        idField.setColumns(10);

        /*
         * Password field rather than another text field as to highlight each
         * character input with "*" as to be more secure for user
         */
        passField = new JPasswordField();
        passField.addKeyListener(new KeyAdapter() {
            // add function of pressing return key to submit the inputted details
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    logIn(e);
                }
            }
        });
        passField.setBounds(183, 113, 127, 20);
        panel.add(passField);
    }

    private void logIn(AWTEvent e) {

        String id = idField.getText();
        String password = passField.getText();
        // Implementing log in system for warehouse system
        if (id.equals("") & password.equals("")) {
            // Successful log in attempt (EmployeeID = 001 | Password = password)
            JOptionPane.showMessageDialog(frmWelcomePage, "Log in successful");
            // If logged in correctly, take you to the interface main screen
            MainMenu.MainMenuScreen();
            frmWelcomePage.dispose();
        }
        // Incorrect log in attempts
        else {
            if (!id.equals("001") & !password.equals("password")) {
                JOptionPane.showMessageDialog(frmWelcomePage, "Incorrect details entered: \n"
                        + "------------------------------------ \n Employee ID & Password");
            } else if (!id.equalsIgnoreCase("001")) {
                JOptionPane.showMessageDialog(frmWelcomePage, "Incorrect details entered: \n"
                        + "------------------------------------ \n\t Employee ID");
            } else {
                JOptionPane.showMessageDialog(frmWelcomePage, "Incorrect details entered: \n"
                        + "------------------------------------ \n\t Password");
            }
        }
    }


    /**
     * creates the dummy data. The warehouse and accounts team have status listeners so these are automatically
     * added to the relevant lists/databases
     */
    private static void createDummyData() {
        Customer[] customers = new Customer[4];
        Calendar cal = Calendar.getInstance();
        cal.set(1973, 7, 23);
        customers[0] = new Customer("Dirty Dave", cal.getTime());
        cal.set(1954, 9, 11);
        customers[1] = new Customer("Saucy Steve", cal.getTime());
        cal.set(1993, 12, 7);
        customers[2] = new Customer("Busty Barbara", cal.getTime());
        cal.set(1968, 1, 1);
        customers[3] = new Customer("Lusty Lucy", cal.getTime());


        Address[] addresses = new Address[5];
        addresses[0] = new Address("123", "FakeStreet", "FakePlace", "FakeLand", "FakeVille");
        addresses[1] = new Address("123", "Bob", "The", "Builder", "WinningPlace");
        addresses[2] = new Address("17", "HolyLand", "HolyPlace", "Somewhere", "A Postcode");
        addresses[3] = new Address("A random house name", "A random street", "A random town", "A random county", "A random postcode");
        addresses[4] = new Address("Cool Place", "Awesome Lane", "Sick Village", "MURRICCAAAAA!!!", "PlaceNameHere....");

        customers[0].addAddress(addresses[0]);
        customers[0].addAddress(addresses[1]);
        customers[1].addAddress(addresses[2]);
        customers[2].addAddress(addresses[3]);
        customers[3].addAddress(addresses[4]);

        customers[3].setEmail("AliceThomas77@hotmail.co.uk");
        customers[1].setEmail("alex_newton@btinternet.com");
        customers[2].setEmail("amhighton@gmail.com");
        customers[0].setEmail("nikesh.dhokia@gmail.com");


        Product[] products = new Product[6];
        products[0] = new Gnome("Dilbert", "A Gnome",
                new Location(1, 1), Product.Colour.BLACK, 12,
                3, 1.25F, 2.99F);
        products[1] = new Gnome("Piglet", "Miss Piggy",
                new Location(5, 4), Product.Colour.BLUE, 100,
                12, 1.01F, 6.99F);
        products[2] = new Gnome("Gandelf", "Saviour of the universe",
                new Location(1, 2), Product.Colour.WHITE, 29,
                7, 3.45F, 9.99F);
        products[3] = new Gnome("Bruce", "A living legend",
                new Location(7, 4), Product.Colour.PURPLE, 233,
                94, 0.52F, 12.99F);
        products[4] = new HotTub("WhirlMaster", "King of the Whirld",
                new Location(1, 5), Product.Colour.PINK, 12,
                15.99F, 189.99F, true, 245, 6);
        products[5] = new HotTub("PleasureLand", "Where all your dreams come true...",
                new Location(2, 9), Product.Colour.GREEN, 23,
                26.99F, 229.99F, true, 132, 2);

        OrderLine[] orderLines = new OrderLine[8];
        try {
            orderLines[0] = new OrderLine(products[0], 9, false);
            orderLines[1] = new OrderLine(products[1], 11, true);
            orderLines[2] = new OrderLine(products[1], 23, false);
            orderLines[3] = new OrderLine(products[2], 17, false);
            orderLines[4] = new OrderLine(products[3], 1, false);
            orderLines[5] = new OrderLine(products[3], 4, true);
            orderLines[6] = new OrderLine(products[4], 2, false);
            orderLines[7] = new OrderLine(products[5], 17, true);
        } catch (OrderLine.OutOfStockException e) {
            System.out.println("Error during setUp!");
        }


        CustomerOrder[] customerOrders = new CustomerOrder[7];

        List<OrderLine> myOrder = new ArrayList<>();

        myOrder.add(orderLines[0]);
        myOrder.add(orderLines[1]);
        customerOrders[0] = new CustomerOrder(myOrder, customers[0], customers[0].getAddresses().get(1));

        myOrder.add(orderLines[3]);
        myOrder.add(orderLines[5]);
        myOrder.add(orderLines[6]);
        myOrder.add(orderLines[7]);
        customerOrders[1] = new CustomerOrder(myOrder, customers[1], customers[1].getAddresses().get(0));

        myOrder.clear();
        myOrder.add(orderLines[3]);
        customerOrders[2] = new CustomerOrder(myOrder, customers[2], customers[2].getAddresses().get(0));

        myOrder.clear();
        myOrder.add(orderLines[3]);
        myOrder.add(orderLines[2]);
        customerOrders[3] = new CustomerOrder(myOrder, customers[3], customers[3].getAddresses().get(0));

        myOrder.clear();
        myOrder.add(orderLines[6]);
        customerOrders[4] = new CustomerOrder(myOrder, customers[0], customers[0].getAddresses().get(0));

        myOrder.clear();
        myOrder.add(orderLines[7]);
        customerOrders[5] = new CustomerOrder(myOrder, customers[2], customers[2].getAddresses().get(0));

        myOrder.clear();
        myOrder.add(orderLines[3]);
        customerOrders[6] = new CustomerOrder(myOrder, customers[1], customers[1].getAddresses().get(0));


        PurchaseOrder[] purchaseOrders = new PurchaseOrder[2];
        myOrder.clear();
        myOrder.add(orderLines[0]);
        myOrder.add(orderLines[2]);
        purchaseOrders[0] = new PurchaseOrder(123, myOrder);

        myOrder.clear();
        myOrder.add(orderLines[3]);

        purchaseOrders[1] = new PurchaseOrder(111, myOrder);

        //This bit is currently causing a null pointer exception within the database because the Statement is null, even after attempting to connect to the database
        Database db = new Database();
        for(PurchaseOrder po : purchaseOrders){
            db.addToDatabase(po);
        }
        for(Product p : products){
            db.addToDatabase(p);
        }


    }

}
