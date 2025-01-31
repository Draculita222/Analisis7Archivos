package mati.a7a.columns;

import mati.a7a.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

public class TipoContibuyenteColumn extends PredefColumn {

    private final static List<String> validValues = Arrays.asList("CF", "EX", "RI", "MT", "AU", "NC", "RN");

    public TipoContibuyenteColumn(String name, boolean isRequired, boolean isUnique) {
        super(name, isRequired, isUnique, validValues);
    }

    @Override
    public ValidationResult validateData(String input) {
        ValidationResult validationResult = new ValidationResult();
        this.validateIsRequiered(validationResult, input);
        if(input.isEmpty()) {
        	return validationResult;
        }
        this.validateValueExists(validationResult, input, false);
        return validationResult;
    }
}
