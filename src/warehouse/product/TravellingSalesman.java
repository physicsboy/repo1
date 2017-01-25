package warehouse.product;

import java.util.*;

/**
 * Created by Alex on 06/01/2017.
 * <p>
 * Generates all possible permutations of a list of locations using the Steinhaus–Johnson–Trotter algorithm
 * then uses a brute force approach to the problem to calculate the shortest possible route between locations,
 * starting and ending at the start point.
 */
public class TravellingSalesman {

    /*
       AN EXAMPLE OF THE LAYOUT OF THE WAREHOUSE


     COLUMNS

        9       .       .       .       .       .       .       .       .       .
                |       |       |       |       |       |       |       |       |
                |       |       |       |       |       |       |       |       |
        8       .       .       .       .       .       .       .       .       .
                |       |       |       |       |       |       |       |       |
                |       |       |       |       |       |       |       |       |
        7       .       .       .       .       .       .       .       .       .


        6       .       .       .       .       .       .       .       .       .
                |       |       |       |       |       |       |       |       |
                |       |       |       |       |       |       |       |       |
        5       .       .       .       .       .       .       .       .       .
                |       |       |       |       |       |       |       |       |
                |       |       |       |       |       |       |       |       |
        4       .       .       .       .       .       .       .       .       .


        3       .       .       .       .       .       .       .       .       .
                |       |       |       |       |       |       |       |       |
                |       |       |       |       |       |       |       |       |
        2       .       .       .       .       .       .       .       .       .
                |       |       |       |       |       |       |       |       |
                |       |       |       |       |       |       |       |       |
        1       .       .       .       .       .       .       .       .       .


        0                                       X

                1       2       3       4       5       6       7       8       9      ROWS



            KEY:
                . - represents possible product storage locations
                X - the start/end location ( and packing area in this case);


            -   THERE ARE GAPS IN THE SHELVING EVERY 3 COLUMNS
                    -    AN EMPLOYEE ONLY HAS TO WALK AT MOST 1 UNIT BEFORE FINDING A GAP IN SHELVES WITH THIS SETUP
            -   EMPLOYEES CANNOT TRAVEL THROUGH DASHED LINES AS THESE REPRESENT SHELVING UNITS IN THE WAREHOUSE.
            -   EMPLOYEES CAN TRAVEL ANYWHERE BLANK, INCLUDING ROUND THE TOP OF THE LAST COLUMN,
                LEFT OF ROW ONE, OR RIGHT OF ROW 9,  IF NEEDED.
     */


    private List<Location> locations;
    private final int MAX_ROW = 9;
    private int[][] distances;
    private Map<Location, Integer> locDistMap = new HashMap<>();

    private final Location START = new Location(MAX_ROW / 2 + 1, 0);


    /**
     * @param locations the locations to be ordered when findShortestRoute() is called
     */
    public TravellingSalesman(List<Location> locations) {
        this.locations = new ArrayList<>(locations);
        distances = calculateDistances();
    }

    /**
     * Generates all possible permutations of a list using the Steinhaus–Johnson–Trotter algorithm.
     * Uses a brute force approach to the problem to calculate the shortest possible route between locations,
     * starting and ending at the start.
     */
    public List<Location> findShortestRoute() {

        //set the shortestDistance to the first distance
        int shortestDistance = calculateRouteDistance(new ArrayList<>(locDistMap.values()));
        List<Integer> shortestRoute = new ArrayList<>(locDistMap.values());
        //remove the start value from the list
        shortestRoute.remove(Integer.valueOf(locations.size()));

        //create a new list of Integer values in the hashMap so find possible permutations
        List<Integer> permutation = new LinkedList<>(shortestRoute);

        if(locations.size() > 1) {
            for (int round = 1; round <= getFactorial(permutation.size()); round++) {
                //Move down the list on odd rounds
                //Move up the list on positive rounds
                int from, to;
                if (round % 2 != 0) {
                    from = permutation.size() - 1;
                    to = from - 1;
                } else {
                    from = 0;
                    to = 1;
                }


                for (int i = 0; i < permutation.size() - 1; i++) {
                    //move an item in the list
                    int numMoved = permutation.get(from);
                    permutation.remove(from);
                    permutation.add(to, numMoved);

                    //check new permutation for shorter distance
                    if (calculateRouteDistance(permutation) < shortestDistance) {
                        shortestDistance = calculateRouteDistance(permutation);
                        shortestRoute = new ArrayList<>(permutation);
                    }

                    //Move down the list on odd rounds
                    if (round % 2 != 0) {
                        from--;
                        to--;
                    } else {
                        from++;
                        to++;
                    }
                }

                //If moved down the list from the top, switch the top 2 elements
                //If moved up the list from the bottom, switch the bottom 2 elements
                if (round % 2 != 0) {

                    //moved down the list --> switch the top 2 elements
                    int fromIndex = permutation.size() - 1;
                    int toIndex = fromIndex--;
                    int toMove = permutation.get(fromIndex);
                    permutation.remove(fromIndex);
                    permutation.add(toIndex, toMove);

                } else {
                    //moved up the list --> switch the bottom 2 elements
                    int toMove = permutation.get(0);
                    permutation.remove(0);
                    permutation.add(1, toMove);
                }

                if (calculateRouteDistance(permutation) < shortestDistance) {
                    shortestDistance = calculateRouteDistance(permutation);
                    shortestRoute = new ArrayList<>(permutation);
                }

            }
        }



        List<Location> shortest = new LinkedList<>();
        for (int i : shortestRoute) {
            shortest.add(this.locations.get(i));
        }

        System.out.println("Shortest Distance: " + shortestDistance);

        return shortest;
    }


    /**
     * num! = num * (num - 1) * (num - 2) * ... * 1
     * @param num the number to get the factorial of.
     * @return num! (factorial of num)
     */
    private int getFactorial(int num) {
        if (num == 1 || num ==0) return 1;
        else return num * getFactorial(num - 1);
    }

    /**
     *
     * @param orderedLocations - the list of locations in the order of the route to be calculated
     * @return the distance of travelling this route, starting and ending at the start point
     */
    public int calculateRouteDistance(List<Integer> orderedLocations) {
        //Start and finish at the start Location
        List<Integer> places = new ArrayList<>(orderedLocations);
        places.add(0, locDistMap.get(START));
        places.add(locDistMap.get(START));

        int totalDistance = 0;
        for (int i = 1; i < places.size(); i++) {

            totalDistance += distances[places.get(i)][places.get(i-1)];
        }

        return totalDistance;
    }


    /**
     *
     * @return a 2 dimensional int array of distances from each point to every other point. The matrix is diagonal,
     * with all numbers on the diagonal being 0 because the distance between point A and B is the same as the distance
     * between point B and point A, and the distance between any point and itself is 0.
     */
    public int[][] calculateDistances() {
        //create the int[][] array to store the size of the list and the start position.
        int[][] distArray = new int[locations.size() + 1][locations.size() + 1];
        //add the start (and end) location to the end of the list
        locations.add(START);


        //max corresponding to start location
        for (int i = 0; i < locations.size(); i++) {

            //Setting the map Locations -> Integer for use later
            locDistMap.put(locations.get(i), i);

            for (int j = 0; j < locations.size(); j++) {

                //set all diagonals to 0 - no distance from location to itself
                if (i == j) distArray[i][j] = 0;
                else {
                    distArray[i][j] = Math.abs(locations.get(i).getColumnNum() - locations.get(j).getColumnNum())
                            + Math.abs(locations.get(i).getRowNum() - locations.get(j).getRowNum());


                    //in the case where the both items are on the same row we need to add 2 units because the employee
                    // cannot go directly through the shelving.
                    // They will instead either have to go 1 up one unit, along and down 1 unit,
                    // or down 1 unit, along and then up 1 unit to the product they are trying to find.
                    if (locations.get(i).getRowNum() == locations.get(j).getRowNum()) {
                        distArray[i][j] += 2;
                    }

                }
            }
        }

        //remove the start (and end) point from the list
        locations.remove(START);
        return distArray;
    }
}
