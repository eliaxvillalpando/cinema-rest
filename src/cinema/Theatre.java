package cinema;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    private final int total_rows;
    private final int total_columns;
    private List<Seat> available_seats;

    public Theatre(int total_rows, int total_columns) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        this.available_seats = new ArrayList<>();

        for (int row = 1; row <= total_rows; row++) {
            for (int column = 1; column <= total_columns; column++) {
                this.available_seats.add(new Seat(row, column));
            }
        }
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public List<Seat> getAvailable_seats() {
        return available_seats;
    }
}

