package commons.poll_wrapper;


public interface MultiPlayerPollObject {

    /**
     * Returns the body of this object, THIS IS USUALLY THE MOST IMPORTANT DATA.
     * What this will return depends on the implementation of MultiPlayerPollObject
     * and it will be CLEARLY MARKED UNDER JAVADOC
     *
     * @return The body of this object
     */
    Object getBody();
}
