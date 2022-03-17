package commons.jokers;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class Joker{
    public int type;

    public Joker(int type) {
        this.type = type;
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

}
