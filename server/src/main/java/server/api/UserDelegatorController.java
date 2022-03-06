package server.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import org.springframework.web.context.request.async.DeferredResult;
import server.server_classes.Game;
import server.server_classes.SinglePlayerGame;
import server.server_classes.MultiPlayerGame;



@RestController
@RequestMapping("/api/game")
public class UserDelegatorController {

    private long idCounter;
    private final Map<Long,Game> games;


    /**
     * default constructor
     */
    public UserDelegatorController() {
        idCounter = 0;
        games = new HashMap<>();
    }

    private synchronized long createID() {
        return idCounter++;
    }



    @GetMapping(path = "singleplayer/{name}")
    public ResponseEntity<SinglePlayerGame> startSinglePlayer(@PathVariable("name") String name) {
        if (name == null || name.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        SinglePlayerGame game = new SinglePlayerGame(createID(),name);
        games.put(game.gameID,game);


        return ResponseEntity.ok(game);
        // I DON'T THINK I NEED AN API CALL TO THE SERVER? CONSIDERING THIS IS THE SERVER!
        // THE PLAN HERE IS TO GET THE GAMECONTROLLER JUST TO START A NEW SINGLEPLAYERGAME
    }

    @GetMapping(path="singleplayer/scoreUpdater/{id}")
    public DeferredResult<Long> scoreUpdater(@PathVariable String id) {
        long gameID = Long.parseLong(id);
        DeferredResult<Long> output = new DeferredResult<>(5000L);
        try {
            SinglePlayerGame game = (SinglePlayerGame) games.get(gameID);
            output.setResult(game.getScore());
        } catch (Exception e) {
            output.setErrorResult(-1);
        }
        return output;
    }


    @GetMapping(path="/delete/{id}")
    public ResponseEntity<Game> deleteGame(@PathVariable("id") String id) {
        long gameID = Long.parseLong(id);
        Game game = games.get(gameID); // for extensibility
        if (game==null) {
            return ResponseEntity.badRequest().build();
        } else return ResponseEntity.ok(game);
    }




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

    @GetMapping(path = "multiplayer/{name}/{ip}")
    public ResponseEntity<MultiPlayerGame> startMultiPlayer(@PathVariable("name") String name, @PathVariable("ip") String ip) {
        return null;

    }



//    @GetMapping(path = "multiplayer/submit/{gameID}/{name}")
//    public




}
