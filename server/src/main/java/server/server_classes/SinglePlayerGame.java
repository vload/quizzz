package server.server_classes;

import commons.Question;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class SinglePlayerGame extends AbstractGame {

    private final String playerName;
    private long score;

    /**
     * Constructor for the SinglePlayerGame class
     *
     * @param gameID The id for the class
     * @param playerName The name of the player associated to the single-player instance
     * @param questions The pre-generated 20 questions to be used in this game instance
     */
    public SinglePlayerGame(long gameID,String playerName,List<Question> questions) {
        super(gameID,questions);
        this.score = 0L;
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
     * getPlayerName method
     * @return the name of the player associated to this single-player instance
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Method to increase the score of the player in the current game session
     *
     * @param inc A long representing how much you want to increase the score by.
     * @return The (new) cumulative score
     */
    public long increaseScore(long inc) {
        score += inc;
        return score;
    }

    /**
     * Compares two objects based on equality
     *
     * @param obj The object to be tested for equality
     * @return true iff, o is an instanceof SinglePlayerGame and has equivalent attributes.
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
     * @return A string which represents this instance of a SinglePlayerGame
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }

}
