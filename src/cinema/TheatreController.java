package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TheatreController {
    private Theatre theatre = new Theatre(9, 9);
    private HashMap<String, Seat> purchasedSeats = new HashMap<>();  // map token to seat
    private int currentIncome = 0;  // total income of sold tickets

    @GetMapping("/seats")
    public Theatre getSeats() {
        return theatre;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat(@RequestBody Seat requestSeat) {
        for (Seat seat : theatre.getAvailable_seats()) {
            if (seat.getRow() == requestSeat.getRow() && seat.getColumn() == requestSeat.getColumn()) {
                // check if the seat is available
                if (seat.isStatus()) {
                    seat.setStatus(false);
                    Ticket ticket = new Ticket(seat);
                    purchasedSeats.put(ticket.getToken(), seat);  // add to map
                    currentIncome += seat.getPrice();  // add price to total income
                    return ResponseEntity.ok(ticket);
                } else {
                    // the seat is already booked
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"The ticket has been already purchased!\"}");
                }
            }
        }

        // the seat doesn't exist
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"The number of a row or a column is out of bounds!\"}");
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnSeat(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        Seat seat = purchasedSeats.get(token);
        if (seat != null) {
            seat.setStatus(true);  // make the seat available again
            purchasedSeats.remove(token);  // remove from map
            currentIncome -= seat.getPrice();  // deduct price from total income
            return ResponseEntity.ok(Map.of("returned_ticket", seat));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Wrong token!\"}");
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(@RequestParam Map<String,String> allParams) {
        String password = allParams.get("password");
        if (password == null || !password.equals("super_secret")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"The password is wrong!\"}");
        }

        int numberOfAvailableSeats = (int) theatre.getAvailable_seats().stream().filter(Seat::isStatus).count();
        int numberOfPurchasedTickets = purchasedSeats.size();

        Map<String, Integer> stats = new HashMap<>();
        stats.put("current_income", currentIncome);
        stats.put("number_of_available_seats", numberOfAvailableSeats);
        stats.put("number_of_purchased_tickets", numberOfPurchasedTickets);

        return ResponseEntity.ok(stats);
    }

}
