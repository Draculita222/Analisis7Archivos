package mati.a7a.columns;

import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

import java.util.ArrayList;

public class ReferentialPredefColumn extends PredefColumn {

    private final int maxLength;

    public ReferentialPredefColumn(String name, boolean isRequired, boolean isUnique, int maxLength) {
        super(name, isRequired, isUnique, new ArrayList<>());
        this.maxLength = maxLength;
    }

    @Override
    public ValidationResult validateData(String input) {
        ValidationResult validationResult = new ValidationResult();
        this.validateIsRequiered(validationResult, input);
        if(input.isEmpty()) {
        	return validationResult;
        }
        this.validateValueExists(validationResult, input, true);
        if(input.length() > maxLength) {
            validationResult.addError(new ValidationError(this, "Longitud demasiado larga (máximo: " + maxLength + ")"));
        }
        return validationResult;
    }
}
