package mati.a7a.results;

import java.util.ArrayList;
import java.util.List;

public class FileValidationResult {

    private final List<FileValidationError> errors = new ArrayList<>();

    private String code = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void addError(FileValidationError error) {
        errors.add(error);
    }

    public List<FileValidationError> getErrors() {
        return errors;
    }

    public boolean isValid () {
        return errors.isEmpty();
    }


}
