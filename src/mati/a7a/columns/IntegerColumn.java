package mati.a7a.columns;

import mati.a7a.main.Config;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

public class IntegerColumn extends AbstractColumn {

    private final int maxLength;

    public IntegerColumn(String name, boolean isRequired, boolean isUnique, int maxLength) {
        super(name, isRequired, isUnique);
        this.maxLength = maxLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public ValidationResult validateData(String input) {
        ValidationResult result = new ValidationResult();
        if(input.isEmpty()) {
        	return result;
        }
        if(input.contains(Config.DECIMAL_SEP)) {
            result.addError(new ValidationError(this, "Valor no entero"));
            return result;
        }

        try {
            long value = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            result.addError(new ValidationError(this, "No se puede convertir el dato ingresado a número entero"));
            return result;
        }

        if(input.length() > maxLength) {
            result.addError(new ValidationError(this, "Longitud demasiado larga (máximo: " + maxLength + ")"));
        }

        return result;
    }

}
