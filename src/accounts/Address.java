package accounts;

/**
 * Created by Alex on 03/01/2017.
 *
 * Stores address info
 */
public class Address {

    private String line1, line2, line3, county, postcode;

    public Address(String line1, String line2, String line3, String county, String postcode) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.county = county;
        this.postcode = postcode;
    }

    public String getAddress(){
        return String.format("%s, %s, %s, %s, %s", line1, line2, line3, county, postcode);
    }
}
