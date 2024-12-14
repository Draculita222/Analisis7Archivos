package mati.a7a.columns;

import mati.a7a.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

public class BooleanColumn extends PredefColumn {

    private final static List<String> validValues = Arrays.asList("yes", "no", "si", "1", "0");

    public BooleanColumn(String name, boolean isRequired, boolean isUnique) {
        super(name, isRequired, isUnique, validValues);
    }

    @Override
    public ValidationResult validateData(String input) {
        ValidationResult validationResult = new ValidationResult();
        this.validateIsRequiered(validationResult, input);
        this.validateValueExists(validationResult, input);
        return validationResult;
    }
}
