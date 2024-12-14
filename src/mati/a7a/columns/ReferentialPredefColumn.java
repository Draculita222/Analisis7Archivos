package mati.a7a.columns;

import mati.a7a.results.ValidationResult;

import java.util.ArrayList;

public class ReferentialPredefColumn extends PredefColumn {

    public ReferentialPredefColumn(String name, boolean isRequired, boolean isUnique) {
        super(name, isRequired, isUnique, new ArrayList<>());
    }

    @Override
    public ValidationResult validateData(String input) {
        ValidationResult validationResult = new ValidationResult();
        this.validateIsRequiered(validationResult, input);
        this.validateValueExists(validationResult, input);
        return validationResult;
    }
}
