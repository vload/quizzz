package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.*;

import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@SuppressWarnings("unused")
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "question_id", nullable = false)
    public long id; // change access-modifiers if necessary? Template project doesn't do this

    @Column(name = "question", nullable = false)
    private String questionText;

    @Column(name = "type", nullable = false)
    private QuestionType type;

    @ManyToMany
    @JoinTable(
            name = "has_activities",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private Set<Activity> activities;

    /**
     *
     * default constructor for object mapper
     */
    public Question() {
        // for object mapper
    }

    /**
     * constructor for Question: For question, activities and type
     * @param questionText The textual representation of the question
     * @param activities The set of activities associated with said question
     * @param type The type of question it is
     */
    public Question(String questionText, Set<Activity> activities, QuestionType type) {
        this.questionText = questionText;
        this.activities = activities;
        this.type = type;
    }

    /**
     *
     * returns the textual representation of the question
     * @return A String containing the question
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     *
     * The set of activities
     * @return A set containing all the activities
     */
    public Set<Activity> getActivities() {
        return activities;
    }

    /**
     * The type of question
     * @return an enum with the question type
     */
    public QuestionType getType() {
        return type;
    }

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