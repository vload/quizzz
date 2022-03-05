package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.*;

import java.util.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@SuppressWarnings("unused")

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "activity_id", nullable = false)
    public long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="energy_consumption")
    private double energyConsumption;

    @Column(name="source_url")
    private String source;

    @ManyToMany(mappedBy = "activities")
    private Set<Question> questions;

    /**
     * constructor for object mapper
     */
    private Activity() {
        // for object mapper
    }

    /**
     * constructor for Activity: (V1) without questions
     *
     * @param title The title of this activity
     * @param energyConsumption The energy consumption (kwh) of this activity
     * @param source The URL where the question comes from.
     */
    public Activity(String title, double energyConsumption, String source) {
        this.title = title;
        this.energyConsumption = energyConsumption;
        this.source = source;
    }

    /**
     * constructor for Activity: (V2) with the set of questions!
     *
     * @param title The title of this activity
     * @param energyConsumption The energy consumption (kwh) of this activity
     * @param source The URL where the question comes from.
     * @param questions The set of questions the activity is associated with
     */
    public Activity(String title, double energyConsumption, String source, Set<Question> questions) {
        this.title = title;
        this.energyConsumption = energyConsumption;
        this.source = source;
        this.questions = questions;
    }

    /**
     * getter for title
     *
     * @return the title of the activity
     */
    public String getTitle() {
        return title;
    }

    /**
     * getter for energyconsumption
     *
     * @return The amount of energy the activity uses
     */
    public double getEnergyConsumption() {
        return energyConsumption;
    }

    /**
     * getter for the source
     *
     * @return The source of the question
     */
    public String getSource() {
        return source;
    }

    /**
     * getter for the questions
     *
     * @return The questions associated with this activity modelled in the many-to-many relationship
     */
    public Set<Question> getQuestions() {
        return questions;
    }

    /**
     * enhanced equals method
     *
     * @param obj The object to be compared to
     * @return true if the two Objects have tested equals.
     */
    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    /**
     *enhanced hashcode method
     *
     * @return the hashcode
     */
    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * enhanced toString
     *
     * @return A string representation of this object
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, MULTI_LINE_STYLE);
    }








}