package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
public class Ticket {

    private String token;
    private Seat ticket;

    public Ticket(Seat seat) {
        this.ticket = seat;
        this.token = UUID.randomUUID().toString();
    }

    public String getToken() {
        return token;
    }

    public Seat getTicket() {
        return ticket;
    }

}
