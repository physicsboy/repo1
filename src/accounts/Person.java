package accounts;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Alex on 03/01/2017
 */
abstract class Person {

    private String name, email;
    private List<Address> addresses = new ArrayList<>();
    private Date dob;

    Person(String name, Date dob){
        this.name = name;
        this.dob = dob;

    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public String getName() {
        return name;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void addAddress(Address a) {
        addresses.add(a);
    }

    public void addAddress() {
        String line1, line2, line3, county, postcode;
        Scanner s = new Scanner(System.in);

        System.out.print("Please enter line 1: ");
        line1 = s.nextLine();
        System.out.print("Please enter line 2: ");
        line2 = s.nextLine();
        System.out.print("Please enter line 3: ");
        line3 = s.nextLine();
        System.out.print("Please enter county: ");
        county = s.next();
        System.out.print("Please enter postcode");
        postcode = s.nextLine();

        addresses.add(new Address(line1, line2, line3, county, postcode));
    }

    Date getDob() {
        return dob;
    }

}
