package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public long id; // change access-modifiers if necessary? Template project doesn't do this :shrug:

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="energy_consumption")
    private double energyConsumption;

    @Column(name="source_url")
    private String source; // I copied the activity format from JSON, don't know if necessary

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name="question_id",nullable = false)
    private Question question;

    /**
     *
     * enhanced equals method
     * @param obj The object to be compared to
     * @return true if the two Objects have tested equals.
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     *
     * enhanced hashcode method
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     *
     * enhanced toString
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }








}