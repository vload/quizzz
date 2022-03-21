package server.server_classes;

import commons.JokerType;
import commons.PlayerData;
import commons.Question;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.security.InvalidParameterException;
import java.util.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class MultiPlayerGame extends AbstractGame {
    private List<String> playerNames;
    private Map<String, PlayerData> playerData;

    /**
     * Constructor for a MultiPlayerGame
     *
     * @param gameID The ID of the game
     * @param playerNames A list containing all the players in the game. Must not be {@literal null}
     * @param questions The list of 20 pre-generated questions to be used in this game instance
     */
    public MultiPlayerGame(long gameID, List<String> playerNames, List<Question> questions) {
        super(gameID, questions);
        this.playerNames = new ArrayList<>(playerNames);
        playerData = new HashMap<>();
        playerNames.forEach(name -> playerData.put(name, new PlayerData(name)));
    }

    /**
     * Gets the player names in this multiplayer instance
     *
     * @return A list of player names
     */
    public List<String> getPlayerNames() {
        return playerNames;
    }

    /**
     * Gets the players and their scores in this multiplayer instance
     *
     * @return A map which contains key value pairs pertaining to names and scores
     */
    public Map<String, Long> getNameScorePairs() {
        Map<String, Long> nameScorePairs = new HashMap<>();

        playerData.forEach((name, data) -> nameScorePairs.put(name, data.getScore()));

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
        if (name==null || name.length() == 0 || playerData.get(name) == null) {
            throw new InvalidParameterException();
        } else {
            return playerData.get(name).getScore();
        }
    }


    /**
     * Gives the player points by increasing the corresponding value in the
     * map.
     *
     * @param name The name of the player
     * @param points The number of points you want to give the player
     * @return The new number of points the player has (old value + new value)
     * @throws InvalidParameterException if the name violates some existence properties
     */
    public long givePoints(String name, long points) {
        if (name==null || name.length() == 0 || playerData.get(name)==null) {
            throw new InvalidParameterException();
        }

        playerData.get(name).addScore(points);
        return playerData.get(name).getScore();
    }

    /**
     * Adds a player to the game.
     * This method is just for extensibility procedures, probably won't be used
     *
     * @param name The name of the player to be added
     * @param startingPoints The number of points the player has to start, will usually be 0
     * @return true iff the player was added successfully (not already existing in game) false otherwise
     */
    public boolean addPlayer(String name, long startingPoints) {
        if (Objects.equals(null,name) || name.length() == 0 || playerData.containsKey(name)) {
            return false;
        }

        playerNames.add(name);
        playerData.put(name, new PlayerData(name));
        playerData.get(name).addScore(startingPoints);
        return true;
    }


    /**
     * Deletes a player from the game (backend).
     *
     * @param name The name of the player to be deleted
     * @return true if the player was successfully deleted, false otherwise
     */
    public boolean deletePlayer(String name) {
        if (Objects.equals(null,name) || name.length() == 0 || !playerData.containsKey(name)) {
            return false;
        }

        playerNames.remove(name);
        playerData.remove(name);
        return true;
    }

    /**
     * Compares two objects based on equality
     *
     * @param obj The object to be tested for equality
     * @return true iff, o is an instanceof MultiplayerGame and has equivalent attributes, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     * Generates a hashcode for the object
     *
     * @return An integer containing the hashcode of this object
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Generates a string representation of this object
     *
     * @return A string which represents this instance of a MultiplayerGame
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

    /**
     * Uses the joker.
     *
     * @param playerName the name of the player
     * @param jokerType the type of joker he uses
     * @return true iff the joker was successfully used
     */
    public boolean useJoker(String playerName, JokerType jokerType) {
        if(!playerData.get(playerName).useJoker(jokerType)){
            return false;
        }

        // send data to all players.

        return true;
    }

    /**
     * getter for playerData
     * @return the playerData
     */
    public Map<String, PlayerData> getPlayerData() {
        return playerData;
    }
}
