package server.api;


import commons.Question;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.server_classes.AbstractGame;
import server.server_classes.SinglePlayerGame;

@RestController
@RequestMapping("/api/joker/")
public class JokerController {


    public JokerController() {

    }

    /**
     * API endpoint to get the next question
     *
     * @param id The id of the game session
     * @return A ResponseEntity containing the next question. A Bad Request if there is no next question
     */
    @PostMapping(path ="multiplayer/use/{gameId}/{jokerType}")
    public ResponseEntity<Question> useJoker (@PathVariable("gameId") String gameId, @PathVariable("jokerType") String jokerType) {
        long gameID = Long.parseLong(gameId);



        if (id.length() == 0
                || games.get(gameID) == null
                || !(games.get(gameID) instanceof SinglePlayerGame)) {
            return ResponseEntity.badRequest().build();
        }

        AbstractGame game = games.get(gameID);
        Question nextQuestion = game.getNextQuestion();
        return ResponseEntity.ok(nextQuestion);
    }
}
