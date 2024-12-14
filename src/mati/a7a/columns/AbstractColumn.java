package mati.a7a.columns;

import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

import java.util.List;

public abstract class AbstractColumn implements IColumn {

    private final String name;
    private final boolean isRequired;
    private final boolean isUnique;

    public AbstractColumn(String name, boolean isRequired, boolean isUnique) {
        this.name = name;
        this.isRequired = isRequired;
        this.isUnique = isUnique;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isRequired() {
        return isRequired;
    }

    @Override
    public boolean isUnique() {
        return isUnique;
    }

    protected void validateIsRequiered(ValidationResult result, String input) {
        if(isRequired && (input.isBlank() || input.isEmpty())) {
            result.addError(new ValidationError(this, "Columna requerida sin valor"));;
        }
    }

    public void validateUniqueness(ValidationResult result, List<String> takenValues, String input) {
        for(String other : takenValues) {
            if(other.toLowerCase().equals(input.toLowerCase())) {
                result.addError(new ValidationError(this, "Dato duplicado"));
            }
        }
    }

}
