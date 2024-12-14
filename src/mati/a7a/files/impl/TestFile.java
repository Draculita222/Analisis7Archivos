package mati.a7a.files.impl;

import mati.a7a.columns.BooleanColumn;
import mati.a7a.columns.IColumn;
import mati.a7a.columns.TextColumn;
import mati.a7a.files.AbstractFile;
import mati.a7a.files.FileStereotype;
import mati.a7a.files.Row;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;
import mati.a7a.unique.UniqueA2UniqueB;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TestFile extends AbstractFile {

    public static final IColumn nameColumn = new TextColumn("Nombre", true, true, 10);
    public static final IColumn lastNameColumn = new TextColumn("Apellido", true, true, 10);
    private static final List<IColumn> columns =
            Arrays.asList(
                    nameColumn,
                    lastNameColumn,
                    new BooleanColumn("Vivo", false, false)
            );

    public TestFile(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }

    @Override
    public ValidationResult customValidateFile() throws ProcessException {
        ValidationResult result = new ValidationResult();
        UniqueA2UniqueB uniqueA2UniqueB = new UniqueA2UniqueB();
        Map<String, List<String>> duplicates = uniqueA2UniqueB.analize(nameColumn, lastNameColumn, getAllValuesForColumn(nameColumn), getAllValuesForColumn(lastNameColumn));
        if(!duplicates.isEmpty()) {
            String message = uniqueA2UniqueB.createMessage(nameColumn, lastNameColumn, duplicates);
            result.addError(new ValidationError(nameColumn, message));
        }
        return result;
    }
}
