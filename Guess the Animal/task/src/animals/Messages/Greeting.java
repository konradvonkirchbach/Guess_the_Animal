package animals.Messages;

import animals.GameInformation.InformationAccess;

import java.time.LocalTime;

public class Greeting implements Message {
    private static final LocalTime MORNING = LocalTime.parse(InformationAccess.getMessagesKey("morning.time.after"));
    private static final LocalTime AFTERNOON = LocalTime.parse(InformationAccess.getMessagesKey("afternoon.time.after"));
    private static final LocalTime EVENING = LocalTime.parse(InformationAccess.getMessagesKey("evening.time.after"));
    private static final LocalTime EARLY = LocalTime.parse(InformationAccess.getMessagesKey("early.time.after"));

    @Override
    public String getMessage() {
        LocalTime t = LocalTime.now();
        if (t.isBefore(EARLY)) {
            return InformationAccess.getMessagesKey("greeting.night");
        }else if (t.isBefore(MORNING)) {
            return InformationAccess.getMessagesKey("greeting.early");
        } else if (t.isBefore(AFTERNOON)) {
            return InformationAccess.getMessagesKey("greeting.morning");
        } else if (t.isBefore(EVENING)) {
            return InformationAccess.getMessagesKey("greeting.afternoon");
        } else {
            return InformationAccess.getMessagesKey("greeting.evening");
        }
    }
}
