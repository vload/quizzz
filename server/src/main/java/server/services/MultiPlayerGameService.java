package server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.api.QuestionGenerator;
import server.server_classes.AbstractGame;
import server.server_classes.MultiPlayerGame;

import java.util.*;

@Service
public class MultiPlayerGameService extends AbstractGameService {

    /**
     *MultiplayerGameService Constructor
     * @param games The gamemap that will be used
     * @param questionGenerator The auto-injected spring component corresponding to the repo
     */
    @Autowired
    public MultiPlayerGameService(Map<Long, AbstractGame> games, QuestionGenerator questionGenerator) {
        super(games, questionGenerator);
    }

    /**
     * Checks that the multiplayergame exists.
     *
     * @param gameID The gameID to be checked for existence
     * @return true iff the game exists, and is of instance MultiPlayerGame
     */
    @Override
    public boolean isValidGame(long gameID) {
        return games.get(gameID) != null &&
                games.get(gameID) instanceof MultiPlayerGame;
    }


}
