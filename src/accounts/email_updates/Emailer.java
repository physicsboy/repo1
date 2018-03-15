package accounts.email_updates; /**
 * Code was seen at the following link, Questioned by "Bill the Lizard" and answered by "jodonnel"
 * http://stackoverflow.com/questions/46663/how-can-i-send-an-email-by-java-application-using-gmail-yahoo-or-hotmail
 *
 * Code was retrofitted by Adam on 05/01/2017
 */

import accounts.Customer;
import warehouse.CustomerOrder;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Emailer {

    final String USER_NAME = "nbgardengnome";  // GMail user name (just the part before "@gmail.com)
    final String PASSWORD = "nbgardens1"; // GMail password
    private  String[] RECIPIENTS = {"someEmail@gmail.com"}; // Can add multiple email addresses, separated by a comma as to import into the Recipient array

    private final String SUBJECT = "An update on your order"; //"Purchase Order (" + po.poNumber() + ") Update"; // Used when updating Accounts on the progress of the PO

    /**
     * Emails a customer to notify them of the status of their order
     * @param order The order to notify the customer about
     */
    public void notifyCustomer(CustomerOrder order){
        StringBuilder sb = new StringBuilder();
        sb.append("You orderID: ").append(order.getOrderID()).append(" has been ").append(order.getOrderStatus());
        sb.append("\n\n\nThank you for shopping with NBGardens");

        if(order.getCustomer().getEmail()!=null) {
            RECIPIENTS = new String[]{order.getCustomer().getEmail()};
            sendFromGMail(USER_NAME, PASSWORD, RECIPIENTS, SUBJECT, sb.toString());
        }
    }

    /**
     * Emails a customer to notify them of a change of status of their account
     * @param c The customer to be notified
     */
    public void notifyCustomer(Customer c){
        String body = "Your status has been set to " + c.getStatus();

        if(c.getEmail()!=null) {
            RECIPIENTS = new String[]{c.getEmail()};
            sendFromGMail(USER_NAME, PASSWORD, RECIPIENTS, SUBJECT, body);
        }
    }

    /**
     * Sends an email
     *
     * @param from the email adddress to send from (from@gmail.com)
     * @param pass the password of the from email address
     * @param to the email addresses to send the email to
     * @param Subject the subject of the email
     * @param Body the body of the email
     */
    private void sendFromGMail(String from, String pass, String[] to, String Subject, String Body) {
        Properties props = System.getProperties(); // uses java.lang.System.getProperties()
        String host = "smtp.gmail.com";

        // send through all details needed to interface with Gmail properly
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props); // uses javax.mail.jar
        MimeMessage message = new MimeMessage(session); // uses javax.mail.jar

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (InternetAddress address : toAddress) {
                message.addRecipient(Message.RecipientType.TO, address);
            }

            message.setSubject(Subject);
            message.setText(Body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
