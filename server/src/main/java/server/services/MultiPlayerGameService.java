package server.services;

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


}
