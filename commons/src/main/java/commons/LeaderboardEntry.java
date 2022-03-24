package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import javax.persistence.*;
import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;

@SuppressWarnings("unused")
@Entity
@Table(name = "leaderbord")
public class LeaderboardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "leaderboardEntry_id", nullable = false)
    public long id; // change access-modifiers if necessary? Template project doesn't do this :shrug:

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name="score")
    private long score;


    /**
     *
     * constructor for object mapper
     */
    private LeaderboardEntry() {
        // for object mapper
    }

    /**
     *
     * constructor for Leaderboard
     * @param id ID of the entry
     * @param name The name of the player
     * @param score The score of the player
     */
    public LeaderboardEntry(long id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    /**
     *
     * getter for name
     * @return The name of the player
     */
    public String getName() {
        return name;
    }

    /**
     *
     * getter for score
     * @return The score of the player
     */
    public long getScore() {
        return score;
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

