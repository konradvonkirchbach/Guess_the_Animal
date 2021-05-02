package animals.GameComponents;

import animals.GameComponents.Engine.UserInteraction;
import animals.GameInformation.InformationAccess;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Fact {
    private static final Pattern pattern = Pattern.compile(!"eo".equals(InformationAccess
            .getLocalizer()
            .getPatterns()
            .getLocale()
            .getLanguage()) ? "has|is|can" : "gesta|povas|havas|loĝas", Pattern.CASE_INSENSITIVE);

    public static final String EXAMPLE_STATEMENTS = InformationAccess.getMessagesKey("statement.error");

    private boolean isNegated;

    private String verb;
    private String object;

    public Fact() {
    }

    public Fact(Fact f) {
        this.verb = f.verb;
        this.object = f.object;
        this.isNegated = f.isNegated;
    }

    public boolean isNegated() {
        return isNegated;
    }

    public void setNegated(boolean negated) {
        isNegated = negated;
    }

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    @JsonIgnore
    public String getWithoutPronoun() {
        if (!"eo".equals(InformationAccess.getLocalizer().getMessages().getLocale().getLanguage())) {
            return !isNegated ? String.format("%s%s", verb, object) : String.format("%s%s", negate(), object);
        } else {
            return isNegated ? String.format("ne %s %s", verb, object) : String.format("%s %s", verb, object);
        }

    }

    @Override
    public String toString() {
        if (!"eo".equals(InformationAccess.getLocalizer().getMessages().getLocale().getLanguage())) {
            return !isNegated ? String.format("It %s%s", verb, object) : String.format("It %s%s", negate(), object);
        } else {
            return isNegated ? String.format("Ĝi ne %s %s", verb, object) : String.format("Ĝi %s %s", verb, object);
        }
    }

    @JsonIgnore
    public String turnIntoQuestion() {
        if (!"eo".equals(InformationAccess.getLocalizer().getMessages().getLocale().getLanguage())) {
            switch (verb) {
                case "has":
                    return String.format("- Does it have%s?", object);
                case "is":
                    return String.format("- Is it%s?", object);
                case "can":
                    return String.format("- Can it%s?", object);
                default:
                    throw new IllegalArgumentException(String.format("No action found for verb: %s", verb));
            }
        } else {
            return String.format("%s %s?", verb, object);
        }
    }

    private String negate() {
        switch (verb) {
            case "has":
                return "doesn't have";
            case "is":
                return "isn't";
            case "can":
                return "can't";
            default:
                throw new IllegalArgumentException(String.format("Verb %s could not be negated", verb));
        }
    }

    @JsonIgnore
    public void init() {
        String initialQuestion = MessageFormat.format(InformationAccess.getMessagesKey("statement.prompt"),
                InformationAccess.getGameInformation().getFirstAnimal(),
                InformationAccess.getGameInformation().getSecondAnimal());
        System.out.println(initialQuestion);
        while (true) {
            if (!"eo".equals(InformationAccess.getLocalizer().getMessages().getLocale().getLanguage())) {
                String answer = UserInteraction.getUserInput();
                answer = InformationAccess.getInterpreter().sanitize(answer);
                Matcher matcher = pattern.matcher(answer);
                if (matcher.find() && !InformationAccess.getInterpreter().isStatement(answer)) {
                    verb = matcher.group();
                    object = answer.substring(3 + verb.length());
                    break;
                } else {
                    System.out.println(String.format("%s\n%s", EXAMPLE_STATEMENTS, initialQuestion));
                }
            } else {
                String answer = UserInteraction.getUserInput();
                answer = InformationAccess.getInterpreter().sanitize(answer);
                Matcher matcher = pattern.matcher(answer);
                if (matcher.find() && !InformationAccess.getInterpreter().isStatement(answer)) {
                    verb = matcher.group();
                    object = answer.substring(3 + verb.length()).trim();
                    break;
                } else {
                    System.out.println(String.format("%s\n%s", EXAMPLE_STATEMENTS, initialQuestion));
                }
            }
        }
    }
}
