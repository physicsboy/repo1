package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class OrderOptions {
    private String orderType;
    private JFrame frame;

    OrderOptions(String orderType) {
        this.orderType = orderType;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    initialize();
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
        frame.setTitle("OrderOptions");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblCustomerOrderOptions = new JLabel(orderType + " order options...");
        lblCustomerOrderOptions.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblCustomerOrderOptions.setBounds(10, 11, 239, 23);
        frame.getContentPane().add(lblCustomerOrderOptions);

        JButton btnNewButton = new JButton("Currently open " + orderType + " orders");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show currently open orders
                if (orderType.toLowerCase().equals("customer")) {
                    OrderShower os = new OrderShower(btnNewButton.getText());
                    os.showOrders(true, false, true, false);
                } else if (orderType.toLowerCase().equals("purchase")) {
                    OrderShower os = new OrderShower(btnNewButton.getText());
                    os.showOrders(false, true, true, false);
                }
                frame.dispose();
            }
        });
        btnNewButton.setBounds(10, 45, 414, 23);
        frame.getContentPane().add(btnNewButton);

        JButton btnCustomerOrdersCurrently = new JButton(orderType + " orders currently in progress");
        btnCustomerOrdersCurrently.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //show orders currently in progress
                if (orderType.toLowerCase().equals("customer")) {
                    OrderShower os = new OrderShower(btnCustomerOrdersCurrently.getText());
                    os.showOrders(true, false, false, true);
                } else if (orderType.toLowerCase().equals("purchase")) {
                    OrderShower os = new OrderShower(btnCustomerOrdersCurrently.getText());
                    os.showOrders(false, true, false, true);
                }
                frame.dispose();
            }
        });
        btnCustomerOrdersCurrently.setBounds(10, 79, 414, 23);
        frame.getContentPane().add(btnCustomerOrdersCurrently);

        JButton button = new JButton("...Back");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu.MainMenuScreen();
            }
        });
        button.setBounds(335, 13, 89, 23);
        frame.getContentPane().add(button);

        frame.setVisible(true);
    }

}
