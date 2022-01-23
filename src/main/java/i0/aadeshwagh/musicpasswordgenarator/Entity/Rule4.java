package i0.aadeshwagh.Entity;

import java.util.Arrays;

//replace even characters with @
public class Rule4 implements Rule {
    String result = "";

    @Override
    public String operation(String passwordLine) {
        Arrays.stream(passwordLine.split(" ")).map(word -> {
            if (word.length() > 1) {
                return word = word.replace(word.charAt(1) + "", "@");
            }
            return word;
        }).forEach(word -> {
            result += word + " ";
        });
        return result.trim();
    }

}
