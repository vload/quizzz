package commons.poll_wrapper;

import commons.PlayerData;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class TimeReductionPollObject implements MultiPlayerPollObject {

    private PlayerData whoInitiated;


    /**
     * Private Constructor for Object Mapper
     */
    private TimeReductionPollObject() {

    }

    /**
     * Constructor for TimeReductionPollObject
     *
     * @param whoInitiated The PlayerData object of the person who initiated this.
     */
    public TimeReductionPollObject(PlayerData whoInitiated) {
        this.whoInitiated = whoInitiated;
    }

    @Override
    public Object getBody() {
        return whoInitiated;
    }

    /**
     * Enhanced equals method
     *
     * @param o The object to be compared to
     * @return true if the two Objects have tested equals.
     */
    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    /**
     * Enhanced hashcode method
     *
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Enhanced toString
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }
}
