package animals.Messages;

import animals.GameInformation.InformationAccess;

import java.util.Random;
import java.util.List;

public class Goodbye implements Message {

    @Override
    public String getMessage() {
        return InformationAccess.getRandomMessagesKey("farewell");
    }
}
