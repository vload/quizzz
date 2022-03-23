package commons.poll_wrapper;

public class TimeReductionPollObject implements MultiPlayerPollObject {

    private Long reductionWeight;


    /**
     * Private Constructor for Object Mapper
     */
    private TimeReductionPollObject() {

    }

    /**
     * Constructor for TimeReductionPollObject
     *
     * @param reductionWeight The factor that the timers of other clients will be reduced by
     */
    public TimeReductionPollObject(Long reductionWeight) {
        this.reductionWeight = reductionWeight;
    }

    @Override
    public Object getBody() {
        return null;
    }
}
