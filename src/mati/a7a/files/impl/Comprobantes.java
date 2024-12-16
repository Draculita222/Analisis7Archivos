package mati.a7a.files.impl;

import mati.a7a.columns.IColumn;
import mati.a7a.files.AbstractFile;
import mati.a7a.files.FileStereotype;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

public class Comprobantes extends AbstractFile {

    private static final List<IColumn> columns =
            Arrays.asList(
            );

    public Comprobantes(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }

    @Override
    public ValidationResult customValidateFile() throws ProcessException {
        return null;
    }
}
