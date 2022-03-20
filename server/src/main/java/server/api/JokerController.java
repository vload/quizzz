package server.api;


import commons.JokerType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import server.server_classes.AbstractGame;
import server.server_classes.MultiPlayerGame;
import server.server_classes.SinglePlayerGame;

import java.util.Map;

@RestController
@RequestMapping("/api/joker/")
public class JokerController {
    private final Map<Long, AbstractGame> games;

    /**
     * Constructor for JokerController
     * @param gameController the game controller.
     */
    public JokerController(GameController gameController) {
        this.games = gameController.getGames();
    }

    /**
     * API endpoint to get the next question
     *
     * @param gameId_ The id of the game session
     * @param playerName The name of the player that used the joker
     * @param jokerType_ The type of joker it is
     * @return A ResponseEntity containing the next question. A Bad Request if there is no next question
     */
    @PostMapping(path ="multiplayer/use/{gameId}/{playerName}/{jokerType}")
    public HttpStatus useJokerMultiplayer (
            @PathVariable("gameId") String gameId_,
            @PathVariable("playerName") String playerName,
            @PathVariable("jokerType") String jokerType_) {

        long gameId;
        JokerType jokerType;

        try {
            gameId = Long.parseLong(gameId_);
            jokerType = JokerType.valueOf(jokerType_);

            if (games.get(gameId) == null ||
                    !(games.get(gameId) instanceof MultiPlayerGame) ||
                    !((MultiPlayerGame) games.get(gameId)).getPlayerNames().contains(playerName) ||
                    ((MultiPlayerGame) games.get(gameId)).getPlayerData().get(playerName).jokerHasBeenUsed(jokerType)) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            return HttpStatus.BAD_REQUEST;
        }


        if (!((MultiPlayerGame) games.get(gameId)).useJoker(playerName, jokerType)){
            return HttpStatus.FORBIDDEN;
        }

        return HttpStatus.OK;
    }

    /**
     * API endpoint to use a joker
     *
     * @param gameId_ The id of the game session
     * @param jokerType_ The type of joker it is
     * @return HttpStatus according to the result of the joker use
     */
    @PostMapping(path ="singleplayer/use/{gameId}/{jokerType}")
    public HttpStatus useJokerSingleplayer (
            @PathVariable("gameId") String gameId_,
            @PathVariable("jokerType") String jokerType_) {

        long gameId;
        JokerType jokerType;

        try {
            gameId = Long.parseLong(gameId_);
            jokerType = JokerType.valueOf(jokerType_);

            if (games.get(gameId) == null ||
                    !(games.get(gameId) instanceof SinglePlayerGame) ||
                    !((SinglePlayerGame) games.get(gameId)).getPlayerData().jokerHasBeenUsed(jokerType)) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException | NullPointerException e){
            return HttpStatus.BAD_REQUEST;
        }


        if(!((SinglePlayerGame) games.get(gameId)).useJoker(gameId, jokerType)){
            return HttpStatus.FORBIDDEN;
        }

        return HttpStatus.OK;
    }
}
