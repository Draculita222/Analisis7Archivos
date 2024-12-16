package mati.a7a.files;

import mati.a7a.columns.IColumn;
import mati.a7a.main.ProcessException;
import mati.a7a.results.FileValidationError;
import mati.a7a.results.ValidationResult;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface IFile {

    public FileStereotype getStereotype();
    public void load(File f) throws ProcessException;

    public String getName();
    public String getBaseName();
    public List<IColumn> getColums();
    public List<Row> getRows();

    public Optional<FileValidationError> validateFileName(String code);
    public List<FileValidationError> getLoadingErrors();
    public abstract ValidationResult validateFile() throws ProcessException;


}
