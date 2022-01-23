package i0.aadeshwagh.musicpasswordgenarator.Entity;

//Remove spaces
public class Rule1 implements Rule {
    @Override
    public String operation(String passwordLine) {
        return passwordLine.replace(" ", "");
    }
}
