package animals.Actions.impl;

import animals.Actions.Actions;

public enum Question implements Actions {
    ASK_FIRST_ANIMAL,
    ASK_SECOND_ANIMAL,
    ASK_FOR_FACT,
    ASK_AGAIN,
    CONFIRM_POSITIV,
    CONFIRM_NEGATIV;


    @Override
    public boolean isQuestion() {
        return true;
    }
}
