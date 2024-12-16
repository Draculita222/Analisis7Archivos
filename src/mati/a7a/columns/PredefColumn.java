package mati.a7a.columns;

import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

import java.util.List;

public abstract class PredefColumn extends AbstractColumn {

    private final List<String> possibleValues;

    public PredefColumn(String name, boolean isRequired, boolean isUnique, List<String> possibleValues) {
        super(name, isRequired, isUnique);
        this.possibleValues = possibleValues;
    }

    public List<String> getPossibleValues() {
        return possibleValues;
    }

    public void overridePossibleValues(List<String> newValues) {
        possibleValues.clear();
        possibleValues.addAll(newValues);
    }

    public void validateValueExists(ValidationResult result, String input, boolean anyCase) {
        boolean exists = false;
        for(String ok : possibleValues) {
            if(anyCase && ok.toLowerCase().equals(input.toLowerCase())) {
                exists = true;
                break;
            }
            if(!anyCase && ok.toLowerCase().equals(input)) {
                exists = true;
                break;
            }
        }
        if(!exists) {
            result.addError(new ValidationError(this, "Valor no válido <" + input + ">"));
        }
    }

}
