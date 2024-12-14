package mati.a7a.files;

import mati.a7a.columns.IColumn;
import mati.a7a.main.ProcessException;
import mati.a7a.results.*;
import mati.a7a.unique.UniqueA;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class AbstractFile implements IFile {

    private final FileStereotype stereotype;
    private final String name;
    private final List<IColumn> columns;
    private final List<FileValidationError> loadingErrors = new ArrayList<>();
    private List<Row> rows = new ArrayList<>();

    public AbstractFile(FileStereotype stereotype, String name, List<IColumn> columns) {
        this.stereotype = stereotype;
        this.name = name;
        this.columns = columns;
    }

    @Override
    public FileStereotype getStereotype() {
        return stereotype;
    }

    @Override
    public List<FileValidationError> getLoadingErrors() {
        return loadingErrors;
    }

    @Override
    public Optional<FileValidationError> validateFileName() {
        if(!name.startsWith(getBaseName())) {
            return Optional.of(new FileValidationError(this, "Nombre de archivo inválido", Optional.empty()));
        }
        return Optional.empty();
    }

    @Override
    public void load(File f) throws ProcessException {
        try {
            Scanner scanner = new Scanner(f);

            if(!scanner.hasNextLine()) {
                loadingErrors.add(new FileValidationError(this, "Archivo sin cabecera", Optional.empty()));
                return;
            }

            String header = scanner.nextLine();
            String[] headerTokens = header.split(",");
            validateHeader(headerTokens);

            if(!scanner.hasNextLine()) {
                loadingErrors.add(new FileValidationError(this, "Archivo vacio",Optional.empty()));
            }

            int lineNumber = 1;
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] rowTokens = line.split(",");
                if(rowTokens.length != columns.size()) {
                    loadingErrors.add(new FileValidationError(this,
                            "Cantidad inválida de columnas", Optional.of(lineNumber)));
                    continue;
                }

                Map<IColumn, String> rowMap = new HashMap<>();
                int tokenIdx = 0;
                for(IColumn column : columns) {
                    rowMap.put(column, rowTokens[tokenIdx]);
                    tokenIdx++;
                }
                rows.add(new Row(rowMap));
            }
        } catch (FileNotFoundException e) {
            loadingErrors.add(new FileValidationError(this, "Archivo no encontrado", Optional.empty()));
        }
    }

    @Override
    public ValidationResult validateFile() throws ProcessException {
        ValidationResult outer = new ValidationResult();
        List<Row> rows = getRows();
        for(int i = 0; i < rows.size(); i++) {
            Map<IColumn, String> map = rows.get(i).map();
            for(Map.Entry<IColumn, String> entry : map.entrySet()) {
                ValidationResult result = entry.getKey().validateData(entry.getValue());

                if(!result.isValid()) {;
                    for(ValidationError err : result.getErrors()) {
                        outer.addError(new ValidationError(err.column(), "Linea " + (i + 1) + " -> " + err.message()));
                    }
                }
            }

        }
        //
        for(IColumn col : columns) {
            if(col.isUnique()) {
                UniqueA uniqueA = new UniqueA();
                Map<String, List<Integer>> analyze = uniqueA.analyze(col, getAllValuesForColumn(col));
                if(!analyze.isEmpty()) {
                    String message = uniqueA.createMessage(col, analyze);
                    outer.addError(new ValidationError(col, message));
                }
            }
        }
        //
        ValidationResult customResults = customValidateFile();
        if(!customResults.isValid()) {
            for(ValidationError err : customResults.getErrors()) {
                outer.addError(new ValidationError(err.column(), err.message()));
            }
        }
        return outer;
    }

    public abstract ValidationResult customValidateFile() throws ProcessException;

    private void validateHeader(String[] headerTokens) throws ProcessException {
        if(headerTokens.length != columns.size()) {
            throw new ProcessException("Columnas en archivo " + getBaseName() + ": "+ headerTokens.length + ". Esperadas: " + columns.size());
        }
        int i = 0;
        for(IColumn column : columns) {
            String columnName = column.getName();
            if(!columnName.equals(headerTokens[i])) {
                loadingErrors.add(
                        new FileValidationError(this,
                                "Nombre de columna incorrecta. Recibido: " + headerTokens[i] + ". Esperado:" + columnName, Optional.empty()));
            }
            i++;
        }
    }

    public List<String> getAllValuesForColumn(IColumn column) {
        return getAllValuesForColumn(column, Optional.empty());
    }

    public List<String> getAllValuesForColumn(IColumn column, Optional<Integer> excludedRow) {
        List<Row> rows = getRows();
        List<String> result = new ArrayList<>();
        for(int i = 0; i < rows.size(); i++) {
            if(excludedRow.isPresent() && excludedRow.get().equals(i)) {
                continue;
            }
            result.add(rows.get(i).map().get(column));
        }
        return result;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<IColumn> getColums() {
        return columns;
    }

    @Override
    public List<Row> getRows() {
        return rows;
    }

    @Override
    public String getBaseName() {
        return stereotype.baseName;
    }
}
