package server.server_classes;


import java.io.*;
import java.util.*;

public abstract class Game implements Serializable {
    public long gameID;
    private boolean isSinglePlayer;


    /**
     * constructor for the game class
     * @param gameID The id for the class
     * @param isSinglePlayer true iff the game is singleplayer, false otherwise
     */
    public Game(long gameID, boolean isSinglePlayer) {
        this.gameID = gameID;
        this.isSinglePlayer = isSinglePlayer;
    }











}
