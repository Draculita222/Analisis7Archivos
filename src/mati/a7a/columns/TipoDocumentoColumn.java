package mati.a7a.columns;

import mati.a7a.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

public class TipoDocumentoColumn extends PredefColumn {
    private final static List<String> validValues = Arrays.asList("80", "86", "87", "89", "90", "96");

    public TipoDocumentoColumn(String name, boolean isRequired, boolean isUnique) {
        super(name, isRequired, isUnique, validValues);
    }

    @Override
    public ValidationResult validateData(String input) {
        ValidationResult validationResult = new ValidationResult();
        this.validateIsRequiered(validationResult, input);
        if(input.isEmpty()) {
        	return validationResult;
        }
        this.validateValueExists(validationResult, input, true);
        return validationResult;
    }
}
