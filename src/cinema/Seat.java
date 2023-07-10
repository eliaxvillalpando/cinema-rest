package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Seat {
    private final int row;
    private final int column;
    @JsonIgnore
    private boolean status;
    private int price;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.status = true; // initially all seats are available
        this.price = row <= 4 ? 10 : 8;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
    @JsonIgnore
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }
}