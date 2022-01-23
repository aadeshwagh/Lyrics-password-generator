package i0.aadeshwagh.musicpasswordgenarator.Entity;

import java.util.Arrays;

//change end of each word to aski equivalent
public class Rule3 implements Rule {
    String result = "";

    @Override
    public String operation(String passwordLine) {
        Arrays.stream(passwordLine.split(" ")).map(word -> {
            return word.substring(0, word.length() - 1) + (int) word.charAt(word.length() - 1);

        }).forEach(word -> {
            result += word + " ";
        });
        return result.trim();
    }
}
