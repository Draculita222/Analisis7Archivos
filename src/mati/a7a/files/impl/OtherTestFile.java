package mati.a7a.files.impl;

import mati.a7a.columns.BooleanColumn;
import mati.a7a.columns.IColumn;
import mati.a7a.columns.ReferentialPredefColumn;
import mati.a7a.columns.TextColumn;
import mati.a7a.files.AbstractFile;
import mati.a7a.files.FileStereotype;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationResult;

import java.util.Arrays;
import java.util.List;

public class OtherTestFile  extends AbstractFile {

    private static final IColumn nameColumn = new TextColumn("Nombre", true, true, 10);
    private static final IColumn referenceColumn = new ReferentialPredefColumn("TestFile", true, false);

    private static final List<IColumn> columns =
            Arrays.asList(
                    nameColumn,
                    referenceColumn
            );
    public OtherTestFile(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }

    public void setReferenceColumnValues(List<String> values) {
        ((ReferentialPredefColumn) referenceColumn).overridePossibleValues(values);
    }

    @Override
    public ValidationResult customValidateFile() throws ProcessException {
        return new ValidationResult();
    }
}
