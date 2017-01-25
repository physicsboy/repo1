package test;

import org.junit.Before;
import org.junit.Test;
import warehouse.product.Location;
import warehouse.product.TravellingSalesman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 06/01/2017.
 */
public class TravellingSalesmanTest {

    TravellingSalesman tsp;
    Map<Location, Integer> myMap;


    @Before
    public void setUp() {
        List<Location> myList = new ArrayList<>();


        Location l1 = new Location(1, 3);
        Location l2 = new Location(2, 4);
        Location l3 = new Location(5, 1);

        myList.add(l1);
        myList.add(l2);
        myList.add(l3);

        myMap = new HashMap<>();
        myMap.put(l1, 0);
        myMap.put(l2, 1);
        myMap.put(l3, 2);
        myMap.put(new Location(5, 0), 3);

        tsp = new TravellingSalesman(myList);
    }

    @Test
    public void findShortestRoute() throws Exception {
        tsp.findShortestRoute();
    }

    @Test
    public void calculateRouteDistance() throws Exception {
        assertEquals(8, tsp.calculateRouteDistance(new ArrayList<>(myMap.values())));
    }

    @Test
    public void calculateDistances() throws Exception {
        int[][] myArray = tsp.calculateDistances();

        for (int i = 0; i < myArray.length; i++) {
            for (int j = 0; j < myArray[0].length; j++) {
                System.out.print(myArray[i][j] + "\t");
            }
            System.out.println();
        }
    }

}