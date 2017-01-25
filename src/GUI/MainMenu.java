package GUI;
import warehouse.Warehouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Adam on 07/01/2017
 */
class MainMenu {

    private JFrame frame;

    /**
     * Create the application.
     */
    private MainMenu() {
        initialize();
        frame.setVisible(true);
    }

    /**
     * Launch the application.
     */
    static void MainMenuScreen() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainMenu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel label = new JLabel("Please select an option to start a process...");
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        label.setBounds(10, 11, 414, 23);
        frame.getContentPane().add(label);

        JButton button = new JButton("Show all open orders ");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show all open orders
                OrderShower show = new OrderShower("All Open Orders");
                show.showOrders(true, true, true, false);
                frame.dispose();
            }
        });
        button.setBounds(10, 45, 414, 23);
        frame.getContentPane().add(button);

        JButton button_1 = new JButton("Show next order");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show the next order
               OrderShower os = new OrderShower("Next Order");
               os.showSingleOrder(Warehouse.getInstance().getNextOrder());
            }
        });
        button_1.setBounds(10, 76, 414, 23);
        frame.getContentPane().add(button_1);

        JLabel lblOrSelectFrom = new JLabel("... or select from the following catagories ...");
        lblOrSelectFrom.setHorizontalAlignment(SwingConstants.CENTER);
        lblOrSelectFrom.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblOrSelectFrom.setBounds(10, 144, 414, 23);
        frame.getContentPane().add(lblOrSelectFrom);


        JButton btnCustomerOrders = new JButton("Customer order options...");
        btnCustomerOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show customer order options
                new OrderOptions("Customer");
                frame.dispose();
            }
        });
        btnCustomerOrders.setBounds(10, 178, 414, 23);
        frame.getContentPane().add(btnCustomerOrders);

        JButton btnPurchaseOrders = new JButton("Purchase order options...");
        btnPurchaseOrders.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show purchase order options
                new OrderOptions("Purchase");
                frame.dispose();
            }
        });
        btnPurchaseOrders.setBounds(10, 212, 414, 23);
        frame.getContentPane().add(btnPurchaseOrders);

        JButton showOrderInProgressBtn = new JButton("All orders currently in progress");
        showOrderInProgressBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show all orders currently in progress
                OrderShower show = new OrderShower("In Progress");
                show.showOrders(true, true, false, true);
                frame.dispose();
            }
        });
        showOrderInProgressBtn.doLayout();
        showOrderInProgressBtn.setBounds(10, 110, 414, 23);

        frame.getContentPane().add(showOrderInProgressBtn);
    }

}
