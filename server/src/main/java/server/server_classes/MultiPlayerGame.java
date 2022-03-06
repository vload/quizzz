package server.server_classes;

import server.server_classes.Game;

public class MultiPlayerGame extends Game {
    /**
     * constructor for the game class
     *
     * @param gameID         The id for the class
     */
    public MultiPlayerGame(long gameID) {
        super(gameID, false);
    }
}
