package server.server_classes;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class SinglePlayerGame extends Game {

    private String playerName;
    private long score;


    // how tf do I get it to serialise these private variables up here without
    // changing their access modifier
    // currently the REST API produces only the ID of the game

    /**
     * constructor for the SinglePlayerGame class
     *
     * @param gameID The id for the class
     * @param playerName The name of the player associated to the single-player instance
     */
    public SinglePlayerGame(long gameID, String playerName) {
        super(gameID,true);
        this.score = 0;
        this.playerName = playerName;
    }

    /**
     * getScore method
     * @return the score of the player
     */
    public long getScore() {
        return score;
    }

    /**
     * compares two objcts based on equality
     *
     * @param obj The object to be tested for equality
     * @return true iff, o is an instanceof SinglePlayerGame false otherwise
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
     * @return A string which represents this instance of a SinglePlayerGame
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }




}
