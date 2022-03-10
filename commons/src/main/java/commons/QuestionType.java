package commons;

public enum QuestionType {
    MC,
    ESTIMATE;

    /**
     * This method tells us how many activities a question type requires.
     * @param type the type of question
     * @return the amount of activities required for said question
     */
    static public int getAmountOfActivities(QuestionType type) {
        switch (type) {
            case MC:
                return 3;
            case ESTIMATE:
                return 1;
            default:
                return 0;
        }
    }
}
