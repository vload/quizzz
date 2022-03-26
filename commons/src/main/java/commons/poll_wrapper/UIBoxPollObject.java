package commons.poll_wrapper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

public class UIBoxPollObject implements MultiPlayerPollObject {

    private List<String> uiMessages;

    /**
     * Private Constructor for Object Mapper
     */
    private UIBoxPollObject() {}

    /**
     *
     * @param uiMessages The List of UIMessages that are to be transferred between server and client.
     */
    public UIBoxPollObject(List<String> uiMessages) {
        this.uiMessages = uiMessages;
    }

    /**
     * Getter for UI messages
     *
     * @return The list containing the UI messages
     */
    @Override
    public List<String> getBody() {
        return uiMessages;
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
