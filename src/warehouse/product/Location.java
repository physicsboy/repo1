package warehouse.product;

/**
 * Created by Administrator on 05/01/2017.
 *
 * Stores Location within the warehouse in a integer row & column form corresponding to a 2D floor plan of the warehouse
 */
public class Location implements Locatable {

    private int row;
    private int column;

    public Location(int row, int column){
        this.row = row;
        this.column = column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public int getRowNum() {
        return row;
    }

    @Override
    public int getColumnNum() {
        return column;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
