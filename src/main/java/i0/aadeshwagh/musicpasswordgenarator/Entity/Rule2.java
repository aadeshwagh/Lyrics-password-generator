package i0.aadeshwagh.musicpasswordgenarator.Entity;

import java.util.Arrays;

//capatalize start of each word
public class Rule2 implements Rule {

    String result = "";

    @Override
    public String operation(String passwordLine) {

        Arrays.stream(passwordLine.split(" ")).map(word -> {
            return Character.toUpperCase(word.charAt(0)) + word.substring(1);
        }).forEach(word -> {
            result += word + " ";
        });
        return result.trim();
    }
}
