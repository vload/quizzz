package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@Entity
@Table(name = "activities")
public class Activity {
    @Id
    @Column(name = "activity_id", nullable = false)
    @JsonProperty("id")
    public String id;

    @Column(name="image_path")
    @JsonProperty("image_path")
    private String imagePath;

    @Column(name = "title", nullable = false)
    @JsonProperty("title")
    private String title;

    @Column(name="energy_consumption")
    @JsonProperty("consumption_in_wh")
    private double energyConsumption;

    @Column(name="source_url")
    @JsonProperty("source")
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
     * gets the image path
     * @return the image path
     */
    public String getImagePath() {
        return imagePath;
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
     * getter for energyConsumption
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
        if(! (obj instanceof Activity)) {
            return false;
        }
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