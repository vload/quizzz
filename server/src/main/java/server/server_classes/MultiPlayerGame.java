package server.server_classes;

import commons.Question;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.security.InvalidParameterException;
import java.util.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class MultiPlayerGame extends AbstractGame {

    private List<String> playerNames;
    private Map<String,Long> nameScorePairs;


    /**
     * Constructor for a MultiPlayerGame
     *
     * @param gameID The ID of the game
     * @param playerNames A list containing all the players in the game. Must not be {@literal null}
     * @param questions The list of 20 pregenerated questions to be used in this game instance
     */
    public MultiPlayerGame(long gameID, List<String> playerNames, List<Question> questions) {
        super(gameID,false, questions);
        Map<String,Long> nameScorePairs = new HashMap<>();
        playerNames.forEach(x -> nameScorePairs.put(x,0L));
        this.playerNames = new ArrayList<>(playerNames);
        this.nameScorePairs = nameScorePairs;
    }

    /**
     * gets the player names in this multiplayer instance
     *
     * @return A list of player names
     */
    public List<String> getPlayerNames() {
        return playerNames;
    }

    /**
     * gets the players and their scores in this multiplayer instance
     *
     * @return A map which contains key value pairs pertaining to names and scores
     */
    public Map<String, Long> getNameScorePairs() {
        return nameScorePairs;
    }

    /**
     * Gets the score of the player in this multiplayer instance
     *
     * @param name The name of the player
     * @return The score of the player in this multiplayer instance
     * @throws InvalidParameterException if the player is not in the lobby or the name violates
     * naming conventions
     */
    public long getPlayerScore(String name) {
        if (name==null || name.length() == 0 || nameScorePairs.get(name) == null) {
            throw new InvalidParameterException();
        } else {
            return nameScorePairs.get(name);
        }
    }


    /**
     * gives the player points by increasing the corresponding value in the
     * map.
     *
     * @param name The name of the player
     * @param points The number of points you want to give the player
     * @return The new number of points the player has (old value + new value)
     * @throws InvalidParameterException if the name violates some existence properties
     */
    public long givePoints(String name, long points) {
        if (name==null || name.length() == 0 || nameScorePairs.get(name)==null) {
            throw new InvalidParameterException();
        }

        nameScorePairs.put(name,nameScorePairs.get(name) + points);
        return nameScorePairs.get(name);
    }

    /**
     * Adds a player to the game.
     * This method is just for extensibility procedures, probably wont't be used
     *
     * @param name The name of the player to be added
     * @param startingPoints The number of points the player has to start, will usually be 0
     * @return true iff the player was added successfully (not already existing in game) false otherwise
     */
    public boolean addPlayer(String name, long startingPoints) {
        if (Objects.equals(null,name) || name.length() == 0 || nameScorePairs.containsKey(name)) {
            return false;
        }

        playerNames.add(name);
        nameScorePairs.put(name,startingPoints);
        return true;
    }


    /**
     * Deletes a player from the game (backend).
     *
     * @param name The name of the player to be deleted
     * @return true if the player was successfully deleted, false otherwise
     */
    public boolean deletePlayer(String name) {
        if (Objects.equals(null,name) || name.length() == 0 || !nameScorePairs.containsKey(name)) {
            return false;
        }

        playerNames.remove(name);
        nameScorePairs.remove(name);
        return true;
    }




    /**
     * compares two objcts based on equality
     *
     * @param obj The object to be tested for equality
     * @return true iff, o is an instanceof Multiplayergame and has equivalent attributes, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * generates a hashcode for the object
     *
     * @return An integer containing the hashcode of this object
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * generates a string representation of this object
     *
     * @return A string which represents this instance of a MultiplayerGame
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }







}
