package animals.GameInformation;

import animals.Actions.Actions;
import animals.GameComponents.Animal;
import animals.GameComponents.Engine.BinaryTree.GameTree;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.File;
import java.io.IOException;

public class GameInformation {
    private GameTree gameTree;
    private Actions action;
    private Animal firstAnimal;
    private Animal secondAnimal;
    private String fileFormat;

    public GameInformation() {
    }

    public GameTree getGameTree() {
        return gameTree;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    public Animal getFirstAnimal() {
        return firstAnimal;
    }

    public void setFirstAnimal(Animal firstAnimal) {
        this.firstAnimal = firstAnimal;
    }

    public Animal getSecondAnimal() {
        return secondAnimal;
    }

    public void setSecondAnimal(Animal secondAnimal) {
        this.secondAnimal = secondAnimal;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public void saveDate() {
        File pathToData = new File(getFileName());
        try {
            getMapper().writerWithDefaultPrettyPrinter()
                    .writeValue(pathToData, gameTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initTree() {
        File pathToData = new File(getFileName());
        ObjectMapper mapper = getMapper();
        if (pathToData.exists()) {
            try {
                gameTree = mapper.readValue(pathToData, GameTree.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            gameTree = new GameTree();
        }
    }

    private ObjectMapper getMapper() {
        switch (fileFormat) {
            case "json":
                return new JsonMapper();
            case "xml":
                return new XmlMapper();
            case "yaml":
                return new YAMLMapper();
            default:
                throw new IllegalArgumentException("No proper mapper selected. One of [json, yaml, xml]");
        }
    }

    private String getFileName() {
        String language = InformationAccess.getLocalizer().getMessages().getLocale().getLanguage();
        String postfix = !"eo".equals(language) ? "." : "_" + language + ".";
        return "animals" + postfix + fileFormat;
    }

}
