package server.services;

import commons.PlayerData;
import commons.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.QuestionGenerator;
import server.server_classes.AbstractGame;
import server.server_classes.IdGenerator;
import server.server_classes.MultiPlayerGame;

import java.util.*;

@Service
public class MultiPlayerGameService extends AbstractGameService {

    /**
     *MultiplayerGameService Constructor
     *
     * @param idGenerator The idGenerator used to generate ids
     * @param gameMap The gamemap that will be used
     * @param questionGenerator The auto-injected spring component corresponding to the repo
     */
    @Autowired
    public MultiPlayerGameService(IdGenerator idGenerator,
                                  Map<Long, AbstractGame> gameMap,
                                  QuestionGenerator questionGenerator) {
        super(idGenerator,gameMap,questionGenerator);
    }

    /**
     * Method to create a new MultiPlayerGame
     *
     * @param playerDataList A list containing all of the players associated with this game instance
     * @return The ID of this game instance
     */
    public long createMultiplayerGame(List<PlayerData> playerDataList) {
        List<Question> pregenQuestions = questionGenerator.generate20Questions();
        MultiPlayerGame game;
        if (pregenQuestions.get(0) == null) {
            game = new MultiPlayerGame(createID(),playerDataList,new ArrayList<>());
        } else {
            game = new MultiPlayerGame(createID(),playerDataList,pregenQuestions);
        }
        gameMap.put(game.gameID,game);
        return game.gameID;
    }

    public Question getNextQuestion(long gameID, String name) {
        if (!isValidGame(gameID)) {
            return null;
        }
        MultiPlayerGame game = (MultiPlayerGame) gameMap.get(gameID);
        return game.getNextQuestion(name);
    }

    /**
     * Adds a String to the message box of a MultiPlayerGame instance
     *
     * @param gameID The ID of the game
     * @param message The message to be added
     * @return null if there was an error finding the game, the message if it was successful
     */
    public String addMessageToInformationBox(long gameID, String message) {
        if (!isValidGame(gameID)) {
            return null;
        }
        MultiPlayerGame game = (MultiPlayerGame) gameMap.get(gameID);
        game.addMesageToInformationBox(message);
        return message;
    }

    /**
     * Getter for the Information Box
     *
     * @param gameID The ID of the game
     * @return The information box of the respective game, as a list
     * null if there was an error finding the game
     */
    public List<String> getInformationBox(long gameID) {
        if (!isValidGame(gameID)) {
            return null;
        }
        MultiPlayerGame game = (MultiPlayerGame) gameMap.get(gameID);
        return game.getInformationBox();
    }

    /**
     * Checks that the multiplayergame exists.
     *
     * @param gameID The gameID to be checked for existence
     * @return true iff the game exists, and is of instance MultiPlayerGame
     */
    @Override
    public boolean isValidGame(long gameID) {
        return gameMap.get(gameID) != null &&
                gameMap.get(gameID) instanceof MultiPlayerGame;
    }

    /**
     * Gets the names and scores of people in a certain game
     *
     * @param gameID The ID of the multiplayer game instance
     * @return The map containing the name of people and their respective score.
     */
    public Map<String,Long> getPlayerScores(long gameID) {
        MultiPlayerGame game = (MultiPlayerGame) gameMap.get(gameID);
        return game.getNameScorePairs();
    }


}
