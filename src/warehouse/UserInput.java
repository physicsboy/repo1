package warehouse;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Administrator on 04/01/2017.
 *
 * Handles getting and returning User Input to the console for the textual interface
 */
public class UserInput {

    private String title, ending;
    private List<String> options;

    private Scanner scanner;

    public UserInput(String title, List<String> options, String ending){
        this.title = title;
        this.options = options;
        this.ending = ending;

        scanner = new Scanner(System.in);
    }

    /**
     * @return the user input if it is between 1 and the maximum inclusive or -1. -1 means 'go'back'
     */
    public int getUserInput(){

        while(true){
            System.out.println(title);
            for (String option : options) {
                System.out.println(option);
            }
            if(ending!=null) System.out.println(ending);

           return scanner.nextInt();
        }
    }
}
