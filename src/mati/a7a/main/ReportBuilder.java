package mati.a7a.main;

import mati.a7a.files.FileStereotype;
import mati.a7a.files.IFile;
import mati.a7a.results.FileValidationError;
import mati.a7a.results.ValidationError;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportBuilder {

    private final Map<FileStereotype, List<FileValidationError>> fileValidationErrors = new HashMap<>();
    private final Map<FileStereotype, List<ValidationError>> validationErrors = new HashMap<>();

    public void addError(IFile file, FileValidationError value) {
        FileStereotype key = file.getStereotype();
        if(!fileValidationErrors.containsKey(key)) {
            fileValidationErrors.put(key, new ArrayList<>());
        }
        fileValidationErrors.get(key).add(value);
    }

    public void addError(IFile file, ValidationError value) {
        FileStereotype key = file.getStereotype();
        if (!validationErrors.containsKey(key)) {
            validationErrors.put(key, new ArrayList<>());
        }
        validationErrors.get(key).add(value);
    }

    public void saveReport(File directory) throws IOException {
        File reportFile = new File(directory + "/reporte.txt");

        String result = "Reporte\n\n";
        String pe = "\n\t";

        FileStereotype[] processingOrder = FileStereotype.getProcessingOrder();
        for(FileStereotype stereotype : processingOrder) {
            String baseName = stereotype.baseName;

            result += "\n" + baseName;

            List<FileValidationError> fileErrors = fileValidationErrors.get(stereotype);
            if(fileErrors == null) {
                fileErrors = new ArrayList<>();
            }
            for(FileValidationError error : fileErrors) {
                result += pe + error.message() + (error.line().isPresent() ? error.line().get() : "");
            }

            List<ValidationError> columnsErrors = validationErrors.get(stereotype);
            if(columnsErrors == null) {
                columnsErrors = new ArrayList<>();
            }
            for(ValidationError error : columnsErrors) {
                result += pe + error.message() + " en columna " + error.column().getName();
            }

            if(fileErrors.isEmpty() && columnsErrors.isEmpty()) {
                result += pe + "El archivo está ok";
            }
        }
        FileWriter fileWriter = new FileWriter(reportFile);
        fileWriter.append(result);
        fileWriter.flush();
    }

}
