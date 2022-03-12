package server.api;

import commons.Question;
import commons.Submission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;
import java.util.function.Consumer;

import org.springframework.web.context.request.async.DeferredResult;
import server.server_classes.*;


@RestController
@RequestMapping("/api/game")
public class GameController {

    private QuestionGenerator questionGenerator;
    private long idCounter;
    private final Map<Long, AbstractGame> games;
    private final Map<Object, Consumer<Long>> singlePlayerListeners = new HashMap<>();

    /**
     * Default constructor
     *
     * @param games map which maps the game ids to the game object
     * @param questionGenerator container component which is responsible for generating questions
     */
    @Autowired
    public GameController(Map<Long, AbstractGame> games, QuestionGenerator questionGenerator) {
        idCounter = 0;
        this.games = games;
        this.questionGenerator = questionGenerator;
    }

    /**
     * Creates an ID for a new game. Synchornization important here.
     *
     * @return A long containing a new ID.
     */
    private synchronized long createID() {
        return idCounter++;
    }


    /**
     * API endpoint for starting a singleplayer game
     *
     * All the user has to provide for a singleplayer game is their username.
     * The API will respond with the ID of the newly created game (game session).
     *
     * @param name The name of the user
     * @return the id of the newly created game. (now referred to as game session)
     */
    @PostMapping(path="singleplayer/create/")
    public ResponseEntity<Long> startSinglePlayer(@RequestBody String name) {
        if (name == null || name.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        SinglePlayerGame game = new SinglePlayerGame(createID(),name,questionGenerator.generate20Questions());
        games.put(game.gameID,game);
        return ResponseEntity.ok(game.gameID);
    }

    /**
     * Returns a list of REMAINING questions in the game
     *
     * @param id The id associated with the game session.
     * @return The list of remaining questions (won't include already removed ones)
     */
    @GetMapping(path="singleplayer/questions/{id}")
    public List<Question> getQuestions(@PathVariable("id") String id) {
        long gameID = Long.parseLong(id);
        if (id.length() == 0
                || games.get(gameID) == null
                || !(games.get(gameID) instanceof SinglePlayerGame)) {
            return null;
        }
        return games.get(gameID).getQuestions();
    }

    /**
     * API endpoint to get the next question
     *
     * @param id The id of the game session
     * @return A ResponseEntity containing the next question. A Bad Request if there is no next question
     */
    @GetMapping(path ="singleplayer/getquestion/{id}")
    public ResponseEntity<Question> getNextQuestion(@PathVariable("id") String id) {
        long gameID = Long.parseLong(id);
        if (id.length() == 0
                || games.get(gameID) == null
                || !(games.get(gameID) instanceof SinglePlayerGame)) {
            return ResponseEntity.badRequest().build();
        }

        AbstractGame game = games.get(gameID);
        Question nextQuestion = game.getNextQuestion();
        if (nextQuestion == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(nextQuestion);
    }

    /**
     * Validates the answer the user provided, and adds points based on whether the user got it correct.
     *
     * @param answerPair A submission object which contains both the answer of the user (which varies according
     *                   to the question type) and the time left on the timer at the time of submission.
     *                   These are the two question types:
     *                   MCQ: Should contain the ID of the activity that the user chose
     *                   Estimate: Should contain the energy usage that the user inputted
     * @param id the ID of the game session
     * @return An empty response, the updated score should be retrieved from scoreUpdater method
     */
    @PostMapping(path="singleplayer/validate/{id}")
    public ResponseEntity<Void> validateAnswer(@RequestBody Submission answerPair, @PathVariable("id") String id) {
        long gameID = Long.parseLong(id);
        if (answerPair == null
                || games.get(gameID) == null
                || !(games.get(gameID) instanceof SinglePlayerGame)) {
            return ResponseEntity.badRequest().build();
        }

        AbstractGame currentGame = games.get(gameID);
        long score = currentGame.getCurrentQuestion().getScore(answerPair.getAnswerVar(),answerPair.getTimerValue());

        if (score != 0L) {
            ((SinglePlayerGame) games.get(gameID)).increaseScore(score);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    /**
     * LONG POLLING METHOD TO CONSTANTLY GET THE SCORE OF THE USER
     *
     * @param id of the game session
     * @return the score of the player
     */
    @GetMapping(path="singleplayer/scoreupdater/{id}")
    public DeferredResult<ResponseEntity<Long>> scoreUpdater(@PathVariable String id) {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        DeferredResult<ResponseEntity<Long>> output = new DeferredResult<>(5000L,noContent);

        Object key = null;
        long gameID = Long.parseLong(id);
        try {
            SinglePlayerGame game = (SinglePlayerGame) games.get(gameID);
            key = new Object();
            singlePlayerListeners.put(key,g -> {
                output.setResult(ResponseEntity.ok(game.getScore()));
            });
            Object finalKey = key;
            output.onCompletion(() -> {
                singlePlayerListeners.remove(finalKey);
            });
        } catch (Exception e) {
            output.setErrorResult(ResponseEntity.ok(-1L));
            if (key!=null) {
                singlePlayerListeners.remove(key);
            }
        }
        return output;
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
        AbstractGame game = games.get(gameID);
        var bool = (game == null);
        if (game==null) {
            return ResponseEntity.badRequest().build();
        } else {
            games.remove(gameID);
            return ResponseEntity.ok(gameID);
        }
    }


    /**
     * Temporary multiplayer controller (template will be fixed)
     *
     * @param name checkstyle
     * @return checkstyle return
     */
    @GetMapping(path = "multiplayer/{name}")
    public ResponseEntity<MultiPlayerGame> startMultiPlayer(@PathVariable("name") String name) {
        if (name == null || name.length() == 0) {
            return ResponseEntity.badRequest().build();
        }
        return null; // temporary
    }









}
