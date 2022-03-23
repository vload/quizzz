package commons.poll_wrapper;

import java.util.List;

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
}
