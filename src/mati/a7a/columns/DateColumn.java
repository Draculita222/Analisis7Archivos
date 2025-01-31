package mati.a7a.columns;

import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

public class DateColumn extends AbstractColumn {

    public DateColumn(String name, boolean isRequired, boolean isUnique) {
        super(name, isRequired, isUnique);
    }

    @Override
    public ValidationResult validateData(String input) {
        ValidationResult validationResult = new ValidationResult();
        this.validateIsRequiered(validationResult, input);
        String[] split = input.split("/");
        if(split.length != 3) {
            validationResult.addError(new ValidationError(this, "Formato de fecha incorrecto. Debe ser DD/MM/AAAA"));
        } else {
            // DIA
            String first = split[0];
            if (first.length() > 2) {
                validationResult.addError(new ValidationError(this, "Formato de DIA incorrecto"));
            } else {
                try {
                    int value = Integer.parseInt(first);
                    if (value > 31) {
                        validationResult.addError(new ValidationError(this, "Número invalido en campo DIA"));
                    }
                } catch (NumberFormatException e) {
                    validationResult.addError(new ValidationError(this, "Número invalido en campo DIA"));
                }
            }
            // MES
            String month = split[1];
            if (month.length() > 2) {
                validationResult.addError(new ValidationError(this, "Formato de MES incorrecto"));
            } else {
                try {
                    int value = Integer.parseInt(month);
                    if (value > 12) {
                        validationResult.addError(new ValidationError(this, "Número invalido en campo MES"));
                    }
                } catch (NumberFormatException e) {
                    validationResult.addError(new ValidationError(this, "Número invalido en campo MES"));
                }
            }
            // AÑO
            String year = split[2];
            if (year.length() > 4) {
                validationResult.addError(new ValidationError(this, "Formato de AÑO incorrecto"));
            } else {
                try {
                    int value = Integer.parseInt(year);
                    if (value > 12) {
                        validationResult.addError(new ValidationError(this, "Número invalido en campo AÑO"));
                    }
                } catch (NumberFormatException e) {
                    validationResult.addError(new ValidationError(this, "Número invalido en campo AÑO"));
                }
            }
        }
        return validationResult;
    }
}
