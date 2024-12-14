package mati.a7a.columns;

import mati.a7a.results.ValidationResult;

public interface IColumn {

    public String getName();
    public boolean isRequired();
    public boolean isUnique();

    public ValidationResult validateData(String input);
}
