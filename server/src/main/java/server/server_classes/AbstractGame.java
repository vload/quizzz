package server.server_classes;


import java.io.*;

public abstract class AbstractGame implements Serializable {
    public final long gameID;
    private final boolean singlePlayer;


    /**
     * constructor for the game class
     * @param gameID The id for the class
     * @param isSinglePlayer true iff the game is singleplayer, false otherwise
     */
    public AbstractGame(long gameID, boolean isSinglePlayer) {
        this.gameID = gameID;
        this.singlePlayer = isSinglePlayer;
    }

    /**
     * checks whether a game is singleplayer or not
     *
     * @return true iff the game is a singleplayer instance, false otherwise
     */
    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    /**
     * abstract equals method
     *
     * @param obj The object to be compared for equality
     * @return true iff obj is of the same type as this object and has equal attributes, false otherwise
     */
    public abstract boolean equals(Object obj);

    /**
     * abstract hashCode method
     * generates the hashcode of any child instance of this object
     *
     * @return An integer contianing the hashcode of this object
     */
    public abstract int hashCode();

    /**
     * Abstract toString method
     * generates a human readable representation of any sub-class instance.
     *
     * @return A string containing information about this
     */
    public abstract String toString();















}
