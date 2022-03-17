package server.server_classes;

import commons.Activity;
import commons.jokers.Joker;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class PlayerData {
    private long score;
    private Map<Joker, Boolean> jokers;

    /**
     * Constructor for PlayerData.
     */
    public PlayerData() {
        Set<Joker> jokerSet = Set.of(new Joker(1), new Joker(2), new Joker(3));
        this.score = 0;

        this.jokers = new HashMap<>();

        jokerSet.forEach(joker -> this.jokers.put(joker, true));
    }

    /**
     * Adds to the player's score.
     */
    public void addScore(long amount){
        score += amount;
    }

    /**
     * Getter for score
     *
     * @return the score
     */
    public long getScore(){
        return score;
    }

    /**
     * Checks if the joker has been used.
     *
     * @param joker the joker to be checked
     * @return true iff joker has been used
     */
    public boolean jokerHasBeenUsed(Joker joker){
        return jokers.get(joker);
    }

    /**
     * Marks the joker as used
     *  //TODO: actually use the joker to change the game state.
     *
     * @param joker the joker to be used.
     */
    public void useJoker(Joker joker){
        if(!jokerHasBeenUsed(joker)){
            jokers.put(joker, false);
//            joker.use(game, etc..);
        }
    }

    /**
     * Compares two objects based on equality
     *
     * @param obj The object to be tested for equality
     * @return true iff, o is an instanceof PlayerData and has equivalent attributes, false otherwise
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
     * @return A string which represents this instance of PlayerData
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
