package mati.a7a.main;

import mati.a7a.files.FileStereotype;
import mati.a7a.files.IFile;
import mati.a7a.files.impl.OtherTestFile;
import mati.a7a.files.impl.TestFile;
import mati.a7a.results.FileValidationError;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class A7A implements ActionListener {

    private JFrame window;
    private JButton chooseFolderButton;

    public A7A() {
        window = new JFrame("Análisis 7 archivos");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(new Dimension(300, 100));
        window.setResizable(false);

        window.setLayout(new FlowLayout());
        chooseFolderButton = new JButton("Elegir carpeta...");

        chooseFolderButton.addActionListener(this);

        window.add(chooseFolderButton);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(chooseFolderButton)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Elegir carpeta");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = chooser.getSelectedFile();
                try {
                    Map<FileStereotype, File> loadedFiles = FileLoader.load(selectedDirectory);
                    processFiles(selectedDirectory, loadedFiles);
                    System.exit(0);
                } catch (ProcessException processException) {
                    JOptionPane.showMessageDialog(window,
                            processException.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                System.out.println("No Selection ");
            }

        }
    }

    private void processFiles(File selectedDirectory, Map<FileStereotype, File> loadedFiles) throws ProcessException {
        ReportBuilder reportBuilder = new ReportBuilder();

        Map<FileStereotype, IFile> alreadyProcessedFiles = new HashMap<>();

        FileStereotype[] processingOrder = FileStereotype.getProcessingOrder();
        for(FileStereotype stereotype : processingOrder) {
            File rawFile = loadedFiles.get(stereotype);
            IFile oneFile = null;
            switch (stereotype) {
                case A -> {
                    oneFile = new TestFile(stereotype, rawFile.getName());
                }
                case B -> {
                    oneFile = new OtherTestFile(stereotype, rawFile.getName());
                    TestFile aFile = (TestFile) alreadyProcessedFiles.get(FileStereotype.A);
                    ((OtherTestFile) oneFile).setReferenceColumnValues(aFile.getAllValuesForColumn(TestFile.nameColumn));
                }
                case C -> {
                }
                case D -> {
                }
                case E -> {
                }
                case F -> {
                }
                case G -> {
                }
            }

            // MMEROI quitar
            if (oneFile == null) {
                break;
            }

            oneFile.load(rawFile);

            Optional<FileValidationError> maybeNameValidationError = oneFile.validateFileName();
            if (maybeNameValidationError.isPresent()) {
                reportBuilder.addError(oneFile, maybeNameValidationError.get());
            }

            List<FileValidationError> loadingErrors = oneFile.getLoadingErrors();
            for (FileValidationError err : loadingErrors) {
                reportBuilder.addError(oneFile, err);
            }

            ValidationResult result = oneFile.validateFile();
            for (ValidationError err : result.getErrors()) {
                reportBuilder.addError(oneFile, err);
            }

            alreadyProcessedFiles.put(stereotype, oneFile);
        }

        try {
            reportBuilder.saveReport(selectedDirectory);
        } catch (IOException e) {
            throw new ProcessException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new A7A();
    }

}
