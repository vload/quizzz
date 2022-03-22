package server.api;

import commons.Question;
import commons.poll_wrapper.MultiPlayerPollObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import server.services.MultiPlayerGameService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@RestController
@RequestMapping("/api/game/multiplayer")
public class MultiPlayerGameController {

    private final MultiPlayerGameService service;
    private Map<Long,Map<Object, Consumer<MultiPlayerPollObject>>> listeners = new ConcurrentHashMap<>();

    /**
     * Constructor for the MultiplayerGameController
     * 
     * @param service The service used to handle all business logic
     */
    @Autowired
    public MultiPlayerGameController(MultiPlayerGameService service) {
        this.service = service;
    }

    /**
     * Returns a list of REMAINING questions in the game
     *
     * @param id The id associated with the game session.
     * @return The list of remaining questions (won't include already removed ones)
     */
    @GetMapping(path="questions/{id}") // "/api/game/singleplayer/questions/{id}"
    public List<Question> getQuestions(@PathVariable("id") String id) {
        long gameID = Long.parseLong(id);
        if (id.length() == 0 || !service.isValidGame(gameID)) {
            return null;
        }
        return service.getQuestions(gameID);
    }

    /**
     * LONG POLLING ENDPOINT: Used for communicating updates of the server to the client
     *
     * @param id The ID of the game
     * @return The DeferredResult which will receive a MultiPlayerPollObject,
     * when new information is available.
     */
    @GetMapping(path="/update/{id}")
    public DeferredResult<ResponseEntity<MultiPlayerPollObject>> updater(@PathVariable("id") String id) {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<MultiPlayerPollObject>>(5000L,noContent);

        long gameID = Long.parseLong(id);
        if (id.length() == 0 || !service.isValidGame(gameID)) {
            res.setErrorResult(ResponseEntity.badRequest().build());
            return res;
        }

        if (listeners.get(gameID) == null) {
            listeners.put(gameID,new ConcurrentHashMap<>());
        }
        var key = new Object();
        listeners.get(gameID).put(key, pollObject -> {
            res.setResult(ResponseEntity.ok(pollObject));
        });
        res.onCompletion(() -> {
            listeners.get(gameID).remove(key);
        });
        return res;
    }









}
