package mati.a7a.main;

import mati.a7a.files.FileStereotype;
import mati.a7a.files.IFile;
import mati.a7a.files.impl.*;
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
    private JLabel codeLabel;
    private JTextField codeField;

    public A7A() {
        window = new JFrame("Análisis 7 archivos");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(new Dimension(300, 100));
        window.setResizable(false);

        window.setLayout(new FlowLayout());
        codeLabel = new JLabel("Código de empresa: ");
        codeField = new JTextField("Ingrese código de empresa");
        codeField.setSize(new Dimension(50, 20));
        chooseFolderButton = new JButton("Elegir carpeta...");

        chooseFolderButton.addActionListener(this);

        window.add(codeLabel);
        window.add(codeField);
        window.add(chooseFolderButton);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(chooseFolderButton)) {

            String code = codeField.getText();
            if(code.isEmpty() || code.isBlank()) {
                showError("Código de empresa vacio");
                return;
            }

            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Elegir carpeta");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(window) == JFileChooser.APPROVE_OPTION) {
                File selectedDirectory = chooser.getSelectedFile();
                try {
                    Map<FileStereotype, File> loadedFiles = FileLoader.load(selectedDirectory);
                    processFiles(code, selectedDirectory, loadedFiles);
                    System.exit(0);
                } catch (ProcessException processException) {
                    showError(processException.getMessage());
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
            }
            else {
                System.out.println("No Selection ");
            }
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(window,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    private void processFiles(String code, File selectedDirectory, Map<FileStereotype, File> loadedFiles) throws ProcessException {
        ReportBuilder reportBuilder = new ReportBuilder();

        Map<FileStereotype, IFile> alreadyProcessedFiles = new HashMap<>();

        FileStereotype[] processingOrder = FileStereotype.getProcessingOrder();

        List<String> clientCodes = null;
        List<String> codigosArticulos = null;
        List<String> sucursales = null;
        List<String> personal = null;
        List<String> fuerzas = null;
        List<String> rutas = null;

        for(FileStereotype stereotype : processingOrder) {
            File rawFile = loadedFiles.get(stereotype);
            String name = rawFile.getName();
            IFile oneFile = null;
            switch (stereotype) {
                case ARTICULO -> {
                    oneFile = new Articulos(stereotype, name);
                    oneFile.load(rawFile);
                    codigosArticulos = ((Articulos) oneFile).getAllValuesForColumn(Articulos.codigoArticulo);
                }
                case CLIENTES -> {
                    oneFile = new Clientes(stereotype, name);
                    oneFile.load(rawFile);
                    clientCodes = ((Clientes) oneFile).getAllValuesForColumn(Clientes.codigoCliente);
                    sucursales = ((Clientes) oneFile).getAllValuesForColumn(Clientes.codigoSucursal);
                }
                case PERSONAL_COMERCIAL -> {
                    oneFile = new PersonalComercial(stereotype, name);
                    oneFile.load(rawFile);
                    ((PersonalComercial) oneFile).setSucursales(sucursales);
                    fuerzas = ((PersonalComercial) oneFile).getAllValuesForColumn(PersonalComercial.codigoFuerza);
                    personal = ((PersonalComercial) oneFile).getAllValuesForColumn(PersonalComercial.codigoPersonal);
                }
                case CLIENTES_EN_RUTA -> {
                    oneFile = new ClientesRuta(stereotype, name);
                    oneFile.load(rawFile);
                    ((ClientesRuta) oneFile).setClientCodes(clientCodes);
                    ((ClientesRuta) oneFile).setSucursales(sucursales);
                    ((ClientesRuta) oneFile).setCodigoFuerza(fuerzas);
                }
                case RUTAS_DE_VENTA -> {
                    oneFile = new RutasDeVenta(stereotype, name);
                    oneFile.load(rawFile);
                    rutas = ((RutasDeVenta) oneFile).getAllValuesForColumn(RutasDeVenta.codigoRuta);
                }
                case STOCK_FISICO -> {
                    oneFile = new StockFisico(stereotype, name);
                    oneFile.load(rawFile);
                    ((StockFisico) oneFile).setCodigosArticulos(codigosArticulos);
                    ((StockFisico) oneFile).setCodigosArticulos(codigosArticulos);
                }
                case COMPROBANTES -> {
                    oneFile = new Comprobantes(stereotype, name);
                    ((Comprobantes) oneFile).setArticulos(codigosArticulos);
                    ((Comprobantes) oneFile).setClientes(clientCodes);
                    ((Comprobantes) oneFile).setPersonal(personal);
                    ((Comprobantes) oneFile).setSucursales(sucursales);
                    ((Comprobantes) oneFile).setFuerzas(fuerzas);
                }
            }

            Optional<FileValidationError> maybeNameValidationError = oneFile.validateFileName(code);
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
