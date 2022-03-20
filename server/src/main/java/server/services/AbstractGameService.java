package server.services;

import commons.Question;
import server.api.QuestionGenerator;
import server.server_classes.AbstractGame;

import java.util.*;

public abstract class AbstractGameService {

    protected QuestionGenerator questionGenerator;
    protected long idCounter;
    protected final Map<Long, AbstractGame> games;

    /**
     * Constructor for GameService
     *
     * @param games map which maps the game ids to the game object
     * @param questionGenerator container component which is responsible for generating questions
     */
    public AbstractGameService(Map<Long, AbstractGame> games, QuestionGenerator questionGenerator) {
        this.idCounter = 0L;
        this.games = games;
        this.questionGenerator = questionGenerator;
    }

    /**
     * Creates an ID for a new game. Synchronization important here.
     *
     * @return A long containing a new ID.
     */
    protected synchronized long createID() {
        return idCounter++;
    }

    /**
     * Inserts a game into the map of games
     *
     * @param game The game instance to be inserted.
     * @return true, iff it the game was inserted successfully, false otherwise
     */
    public boolean insertGame(AbstractGame game) {
        if (games.get(game.gameID) != null) {
            return false;
        }
        games.put(game.gameID,game);
        return true;
    }


    /**
     * Returns a list of the REMAINING questions in the game
     *
     * @param gameID The ID associated with the game session.
     * @return The list of remaining questions (won't include any removed ones)
     */
    public List<Question> getQuestions(long gameID) {
        return games.get(gameID).getQuestions();
    }

    /**
     * Gets the next question associated with the game instance
     *
     * @param gameID The ID of the game instance
     * @return The next question
     */
    public Question getNextQuestion(long gameID) {
        AbstractGame game = games.get(gameID);
        return game.getNextQuestion();
    }

    /**
     * Deletes a game from the game map.
     *
     * @param gameID The game ID of the game instance to be deleted
     * @return the ID of the game to be delted, -1 if the game was not found.
     */
    public long deleteGame(long gameID) {
        if (games.get(gameID) == null) {
            return -1L;
        }
        games.remove(gameID);
        return gameID;
    }

    /**
     * Abstract method to see if the game instance actually exists.
     *
     * @param gameID The gameID to be checked for existence
     * @return true, iff the game exists, false otherwise
     */
    public abstract boolean isValidGame(long gameID);




}
