package animals.Messages;

import animals.GameInformation.InformationAccess;

import java.util.List;
import java.util.Random;

public class Clarification implements Message {

    @Override
    public String getMessage() {
        return InformationAccess.getRandomMessagesKey("ask.again");
    }

}
