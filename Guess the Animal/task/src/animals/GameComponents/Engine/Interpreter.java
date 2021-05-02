package animals.GameComponents.Engine;

import animals.Actions.Actions;
import animals.Actions.impl.Question;
import animals.GameInformation.InformationAccess;
import animals.Messages.Clarification;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpreter {
    private final List<String> POSITIVE_RESPONSES = List.of(InformationAccess
            .getMessagesKey("answer.yes").split("\\f+"));
    private final List<String> NEGATIV_ANSWERS = List.of(InformationAccess
            .getMessagesKey("answer.no").split("\\f+"));

    public Actions interpretInput(String userInput) {
        String cleanedInput = sanitize(userInput);
        if (POSITIVE_RESPONSES.contains(cleanedInput)) {
            return Question.CONFIRM_POSITIV;
        } else if (NEGATIV_ANSWERS.contains(cleanedInput)) {
            return Question.CONFIRM_NEGATIV;
        } else {
            return Question.ASK_AGAIN;
        }
    }

    public boolean answerIsYes(String userInput) {
        if (POSITIVE_RESPONSES.contains(sanitize(userInput))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean answerIsNo(String userInput) {
        if (NEGATIV_ANSWERS.contains(sanitize(userInput))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isStatement(String userInput) {
        if (!"eo".equals(InformationAccess.getLocalizer().getPatterns().getLocale().getLanguage())) {
            Pattern pattern = Pattern.compile("it can|it has|it is", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(userInput);
            return !matcher.find();
        } else {
            Pattern pattern = Pattern.compile("ĝi", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(userInput);
            return !matcher.find();
        }
    }

    public String sanitize(String input) {
        String sanitized = input.trim();
        Pattern pattern = Pattern.compile("\\.{1}|!{1}|\\?{1}");
        Matcher matcher = pattern.matcher(sanitized);
        if (matcher.find()) {
            String punctuations = matcher.group();
            return sanitized.substring(0, sanitized.length() - punctuations.length());
        }
        return  sanitized;
    }

    public boolean isAnswerYes() {
        while (true) {
            String answer = UserInteraction.getUserInput();
            if (InformationAccess.getInterpreter().answerIsYes(answer)) {
                return true;
            } else if (InformationAccess.getInterpreter().answerIsNo(answer)) {
                return false;
            } else {
                System.out.println(new Clarification().getMessage());
            }
        }
    }

}
