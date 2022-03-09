package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Consumer;

import server.server_classes.*;


@RestController
@RequestMapping("/api/game")
public class GameController {

    private long idCounter;
    private final Map<Long, AbstractGame> games;
    private Map<Object, Consumer<Long>> singlePlayerListeners = new HashMap<>();




    /**
     * default constructor
     *
     * @param games map which maps the game ids to the game object
     */
    public GameController(Map<Long, AbstractGame> games) {
        idCounter = 0;
        this.games = games;
    }

    private synchronized long createID() {
        return idCounter++;
    }



    /**
     * API endpoint for starting a singleplayer game
     *
     * @param name The name of the user
     * @return the id of the newly created game.
     */
    @GetMapping(path = "singleplayer/{name}")
    public ResponseEntity<Long> startSinglePlayer(@PathVariable("name") String name) {
        if (name == null || name.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        SinglePlayerGame game = new SinglePlayerGame(createID(),name);
        games.put(game.gameID,game);


        return ResponseEntity.ok(game.gameID);
        // I DON'T THINK I NEED AN API CALL TO THE SERVER? CONSIDERING THIS IS THE SERVER!
        // THE PLAN HERE IS TO GET THE GAMECONTROLLER JUST TO START A NEW SINGLEPLAYERGAME
    }

    /**
     * API endpoint for deleting a game
     *
     * @param id The id of the game. Should not be {@literal null}
     * @return A ResponseEntity containing the ID of the game.
     */
    @GetMapping(path="/delete/{id}")
    public ResponseEntity<Long> deleteGame(@PathVariable("id") String id) {
        if (id==null || id.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        long gameID = Long.parseLong(id);
        AbstractGame game = games.get(gameID); // for extensibility
        var bool = (game == null);
        if (game==null) {
            return ResponseEntity.badRequest().build();
        } else {
            games.remove(gameID);
            return ResponseEntity.ok(gameID);
        }
    }


    /**
     * temporary multiplayer controller (template will be fixced)
     *
     * @param name checkstyle
     * @return checkstyle return
     */
    @GetMapping(path = "multiplayer/{name}")
    public ResponseEntity<MultiPlayerGame> startMultiPlayer(@PathVariable("name") String name) {
        if (name == null || name.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        // I DON'T THINK I NEED AN API CALL TO THE SERVER? CONSIDERING THIS IS THE SERVER!
        // THE PLAN HERE IS TO GET THE GAMECONTROLLER JUST TO START A NEW MULTIPLAYERGAME
        // I NEED TO CHECK WHETHER THE NAME ALREADY EXISTS IN THE WAITING ROOM
        return null; // temporary
    }
//
//    @GetMapping(path = "multiplayer/{name}/{ip}")
//    public ResponseEntity<MultiPlayerGame> startMultiPlayerIP(@PathVariable("name") String name,
//    @PathVariable("ip") String ip) {
//        return null;
//    }








}
