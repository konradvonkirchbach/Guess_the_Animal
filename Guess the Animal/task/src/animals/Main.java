package animals;

import animals.GameComponents.Engine.GameMaster;

import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        GameMaster gameMaster = new GameMaster(validateArgumentsReturnFormat(args).orElse("json"));
        gameMaster.menu();
    }

    private static Optional<String> validateArgumentsReturnFormat(String[] args) {
        if (args.length == 0) {
            return Optional.empty();
        }
        assert args.length == 2;
        if (!"-type".equals(args[0])) {
            throw new IllegalArgumentException("Arguments: -type [json, xml, yaml]");
        }
        if (!"json".equals(args[1]) && !"xml".equals(args[1]) && !"yaml".equals(args[1])) {
            throw new IllegalArgumentException("Arguments: -type [json, xml, yaml]");
        }
        return Optional.of(args[1]);
    }

}