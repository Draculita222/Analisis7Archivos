package mati.a7a.columns;

import mati.a7a.main.Config;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

import javax.swing.*;

public class FloatColumn extends AbstractColumn {

    private final int integerLength;
    private final int decimalLength;

    public FloatColumn(String name, boolean isRequired, boolean isUnique, int integerLength, int decimalLength) {
        super(name, isRequired, isUnique);
        this.integerLength = integerLength;
        this.decimalLength = decimalLength;
    }

    public int getIntegerLength() {
        return integerLength;
    }

    public int getDecimalLength() {
        return decimalLength;
    }

    @Override
    public ValidationResult validateData(String input) {
        ValidationResult result = new ValidationResult();
        if(input.isEmpty()) {
        	return result;
        }
        boolean hasDecimals = false;
        String ints = "";
        String decimals = "";
        if(input.contains(Config.DECIMAL_SEP)) {
            String[] tokens = input.split(".");
            if(tokens.length != 2) {
                result.addError(new ValidationError(this, "Formato de decimal inválido"));
                return result;
            }
            ints = tokens[1];
            decimals = tokens[2];
            hasDecimals = true;
        }

        try {
            float value = Float.parseFloat(input);
        } catch (NumberFormatException nfe) {
            result.addError(new ValidationError(this, "No se puede convertir el dato ingresado a número decimal"));
            return result;
        }

        if((!ints.contains(Config.INTEGER_SEP) && ints.length() > integerLength)
                || ((ints.contains(Config.INTEGER_SEP) && ints.length() - 1 > integerLength))) {
            result.addError(new ValidationError(this, "Cantidad de enteros inválida (máximo: " + integerLength + ")"));
        }

        if(hasDecimals && decimals.length() > decimalLength) {
            result.addError(new ValidationError(this, "Cantidad de decimales inválida (máximo: " + decimalLength + ")"));
        }
        return result;
    }
}
