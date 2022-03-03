package server.api;

import commons.Question;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/delegator")
public class UserDelegatorController {
    // class which seperates single player from multiplayer

    /**
     * default constructor
     */
    public UserDelegatorController() {

    }

    @GetMapping(path = "singleplayer/{name}")
    public ResponseEntity<SinglePlayerGame> startSinglePlayer(@PathVariable("name") String name) {
        if (name == null || name.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        // I DON'T THINK I NEED AN API CALL TO THE SERVER? CONSIDERING THIS IS THE SERVER!
        // THE PLAN HERE IS TO GET THE GAMECONTROLLER JUST TO START A NEW SINGLEPLAYERGAME
        return null; // temporary
    }

    @GetMapping(path = "multiplayer/{name}")
    public ResponseEntity<Integer> startMultiPlayer(@PathVariable("name") String name) {
        if (name == null || name.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        // I DON'T THINK I NEED AN API CALL TO THE SERVER? CONSIDERING THIS IS THE SERVER!
        // THE PLAN HERE IS TO GET THE GAMECONTROLLER JUST TO START A NEW MULTIPLAYERGAME
        // I NEED TO CHECK WHETHER THE NAME ALREADY EXISTS IN THE WAITING ROOM
        return null; // temporary
    }




}
