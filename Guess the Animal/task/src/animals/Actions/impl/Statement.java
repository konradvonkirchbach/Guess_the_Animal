package animals.Actions.impl;

import animals.Actions.Actions;

public enum Statement implements Actions {
    GREET,
    GOODBYE,
    FACT;

    @Override
    public boolean isQuestion() {
        return false;
    }
}
