package animals.GameComponents;

import animals.GameInformation.InformationAccess;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Animal {
    private static final String VOWELS = "aeiou";
    private static final List<String> ARTICLES = List.of("a ", "an ");

    private String article;
    private String name;
    private Fact fact;

    public Animal() {
    }

    public Animal(String input) {
        init(input);
    }

    public String getArticle() {
        return article;
    }

    public String getName() {
        return name;
    }

    public Fact getFact() {
        return fact;
    }

    public void setFact(Fact fact) {
        this.fact = fact;
    }

    public String withIndefiniteArticle() {
        if (!"eo".equals(InformationAccess.getLocalizer().getPatterns().getLocale().getLanguage())) {
            return String.format("The %s", name);
        } else {
            return "La " + name;
        }
    }

    @Override
    public String toString() {
        if (!"eo".equals(InformationAccess.getLocalizer().getPatterns().getLocale().getLanguage())) {
            return String.format("%s %s", article, name);
        } else {
            return name;
        }
    }

    private void init(String userInput) {
        if (!"eo".equals(InformationAccess.getLocalizer().getPatterns().getLocale().getLanguage())) {
            if (containsArticle(userInput)) {
                int firstWhiteSpace = userInput.indexOf(" ");
                article = userInput.substring(0, firstWhiteSpace);
                name = userInput.substring(firstWhiteSpace + 1);
            } else {
                name = userInput;
                if (VOWELS.contains(name.substring(0, 1))) {
                    article = "an";
                } else {
                    article = "a";
                }
            }
        } else {
            name = userInput.trim();
            article = "";
        }
    }

    private boolean containsArticle(String text) {
        boolean containsArticle = false;
        for (String article : ARTICLES) {
            containsArticle |= text.startsWith(article);
        }
        return containsArticle;
    }
}
