package mati.a7a.columns;

import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

public class TextColumn extends AbstractColumn {

    private final int maxLength;

    public TextColumn(String name, boolean isRequired, boolean isUnique, int maxLength) {
        super(name, isRequired, isUnique);
        this.maxLength = maxLength;
    }

    @Override
    public ValidationResult validateData(String input) {
        ValidationResult validationResult = new ValidationResult();
        this.validateIsRequiered(validationResult, input);
        if(input.length() > maxLength) {
            validationResult.addError(new ValidationError(this, "Texto demasiado largo (máximo: " + maxLength + ")"));
        }
        return validationResult;
    }
}
