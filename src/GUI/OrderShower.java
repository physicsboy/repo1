package GUI;

import warehouse.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Adam on 09/01/2017.
 *
 * Shows the Orders on the screen using a JTable
 */
class OrderShower extends JFrame {

    OrderShower(String title){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    setTitle(title);
                    initialize();

                    pack();
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
        //setTitle("AllOpenOrders");
        setSize(100, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());


        JLabel lblAllOpenOrders = new JLabel("All open orders...");
        lblAllOpenOrders.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblAllOpenOrders.setBounds(10, 11, 310, 23);


        JButton btnback = new JButton("...Back");
        btnback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainMenu.MainMenuScreen();
                dispose();
            }
        });
        //btnback.setBounds(335, 13, 89, 23);
        btnback.setHorizontalAlignment(JButton.NORTH_EAST);

        JPanel labelButton = new JPanel();
        labelButton.add(lblAllOpenOrders);
        labelButton.add(Box.createRigidArea(new Dimension(200, 0)));
        labelButton.add(btnback);
        labelButton.setLayout(new BoxLayout(labelButton, BoxLayout.X_AXIS));
        add(labelButton, BorderLayout.NORTH);

    }



    private boolean showCO, showPO, showOpenOrders, showInProgress;

    /**
     *
     * @param showCO flag for showing CustomerOrders
     * @param showPO flag for Showing PurchaseOrders
     * @param showOpenOrders flag for showing currently Open Orders
     * @param showInProgressOrders flag for showing orders which are currently in progress
     */
    void showOrders(boolean showCO, boolean showPO, boolean showOpenOrders, boolean showInProgressOrders) {
        this.showCO = showCO;
        this.showPO = showPO;
        this.showOpenOrders = showOpenOrders;
        this.showInProgress = showInProgressOrders;


        //creating the headings and data for the table
        String[] columnNames = {"ID", "Type", "Date", "State"};
        Warehouse wh = Warehouse.getInstance();

        ArrayList<Order> orders = new ArrayList<>(wh.showOrderMenu(showCO, showPO, showOpenOrders, showInProgressOrders));
        if(orders.size() == 0){
            JOptionPane.showMessageDialog(null, "No Orders found!");
            MainMenu.MainMenuScreen();
        }
        else {
            String[][] strings = new String[orders.size()][];
            for (int i = 0; i < orders.size(); i++) {
                strings[i] = orders.get(i).getTableArray();
            }

            JTable table = new JTable(); // source of info for the table - need data + column headers
            table.setModel(new DefaultTableModel(strings, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

                @Override
                public void fireTableDataChanged() {
                    super.fireTableDataChanged();
                    showOrders(showCO, showPO, showOpenOrders, showInProgressOrders);
                    System.out.println("DATA CHANGED!");
                }
            });
            //adding a mouse listener to listen for double clicks.
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //show detailed Order information on double click
                    if (e.getClickCount() == 2) {
                        JTable target = (JTable) e.getSource();
                        int row = target.getSelectedRow();
                        showSingleOrder(orders.get(row));
                    }
                }
            });


            JScrollPane scrollPane = new JScrollPane(table); // make table scrollable
            scrollPane.setBounds(10, 45, 311, 206); // set size of pane
            //getContentPane().add(scrollPane); // add table to pane
            scrollPane.setViewportView(table);
            add(scrollPane);
            pack();
            setVisible(true);
        }
    }

    /**
     * Shows detailed information for a single order. The Items in the order are presented in the optimized order
     * for shortest collection route
     * @param order The order to show detailed information about
     */
    void showSingleOrder(Order order) {

        if(order == null){
            JOptionPane.showMessageDialog(null, "No Order Found!");
            MainMenu.MainMenuScreen();
        }
        else {
            JFrame frame = new JFrame("Order Number: " + order.getOrderID());
            frame.setLayout(new BorderLayout());


            JTable table = new JTable(); // source of info for the table - need data + column headers
            String[] columnNames = new String[]{"ID", "Name", "Location", "Quantity", "PorousWare"};
            String[][] orderLines = new String[order.getOrder().size()][];

            for (int i = 0; i < order.getOrder().size(); i++) {
                orderLines[i] = order.getOrder().get(i).getTableArray();
            }

            table.setModel(new DefaultTableModel(orderLines, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            JPanel panel = new JPanel();
            JScrollPane scrollPane = new JScrollPane(table); // make table scrollable
            scrollPane.setViewportView(table);
            panel.add(scrollPane);


            //get the status of the current order
            JLabel title = new JLabel();
            Status.CO_STATUS cStatus = null;
            Status.PO_STATUS pStatus = null;
            if (order instanceof CustomerOrder) {
                cStatus = ((CustomerOrder) order).getOrderStatus();
                title = new JLabel(String.format("Order ID: %d \t\tStatus: %S\t\tForklift Needed:%s", order.getOrderID(), ((CustomerOrder) order).getOrderStatus(), order.isForkliftNeeded()));
            } else if (order instanceof PurchaseOrder) {
                pStatus = ((PurchaseOrder) order).getOrderStatus();
                title = new JLabel(String.format("Order ID: %d \t\tStatus: %S\t\tForklift Needed:%s", order.getOrderID(), ((PurchaseOrder) order).getOrderStatus(), order.isForkliftNeeded()));
            }

            //getting the next status which the order can be set to
            String setToNext = "Set Status to ";
            if (cStatus != null) {
                cStatus = cStatus.getNext();
                setToNext += cStatus;
            } else if (pStatus != null) {
                pStatus = pStatus.getNext();
                setToNext += pStatus;
            }

            JButton setToNextButton = new JButton(setToNext);
            setToNextButton.addActionListener(e -> {
                //update the order when the user clicks on 'set status to XXX'
                if (order instanceof CustomerOrder) {
                    ((CustomerOrder) order).updateOrder();
                } else if (order instanceof PurchaseOrder) {
                    ((PurchaseOrder) order).updateOrder();
                }

                //show a JOptionPane to notify the user that the status of the order has been updated
                JOptionPane.showMessageDialog(null, "Order Updated");
                frame.dispose();
                this.dispose();
                new OrderShower(getTitle()).showOrders(showCO, showPO, showOpenOrders, showInProgress);

            });


            frame.add(title, BorderLayout.NORTH);
            frame.add(panel, BorderLayout.CENTER);
            frame.add(setToNextButton, BorderLayout.SOUTH);
            frame.pack();
            frame.setVisible(true);
        }

    }


}

