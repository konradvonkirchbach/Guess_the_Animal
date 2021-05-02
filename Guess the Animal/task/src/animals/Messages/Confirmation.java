package animals.Messages;

public class Confirmation implements Message {
    private String confirmation;

    public Confirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    @java.lang.Override
    public String getMessage() {
        return String.format("You answered: %s", confirmation);
    }
}
