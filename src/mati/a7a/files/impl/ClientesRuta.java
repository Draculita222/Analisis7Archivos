package mati.a7a.files.impl;

import mati.a7a.columns.*;
import mati.a7a.files.AbstractFile;
import mati.a7a.files.FileStereotype;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;
import mati.a7a.validations.AtLeastOneEntry;

import java.util.Arrays;
import java.util.List;

public class ClientesRuta extends AbstractFile {

    public static final IColumn codigoSucursal = new ReferentialPredefColumn("CodigoSucursal", true, false, 10);
    public static final IColumn codigoFuerza = new ReferentialPredefColumn("CodigoFuerza", true, false, 10);
    public static final IColumn codigoModoAtencion = new TextColumn("CodigoModoAtencion", true, false, 5);
    public static final IColumn codigoCliente = new TextColumn("CodigoCliente", true, false, 50);
    public static final IColumn codigoRuta = new IntegerColumn("CodigoRuta", true, false, 10);
    public static final IColumn fechaDesde = new DateColumn("FechaDesde", true, false);
    public static final IColumn fechaHasta = new DateColumn("FechaHasta", true, false);

    private static final List<IColumn> columns =
            Arrays.asList(
                    codigoSucursal,
                    codigoFuerza,
                    codigoModoAtencion,
                    codigoCliente,
                    codigoRuta,
                    fechaDesde,
                    fechaHasta
            );

    private List<String> clientCodes;

    public ClientesRuta(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }

    public void setSucursales(List<String> sucursales) {
        ((ReferentialPredefColumn) ClientesRuta.codigoSucursal).overridePossibleValues(sucursales);
    }

    public void setCodigoFuerza(List<String> fuerzas) {
        ((ReferentialPredefColumn) ClientesRuta.codigoFuerza).overridePossibleValues(fuerzas);
    }

    public void setClientCodes(List<String> clientCodes) {
        this.clientCodes = clientCodes;
    }

    @Override
    public ValidationResult customValidateFile() {
        ValidationResult validation = new ValidationResult();

        AtLeastOneEntry atLeastOneEntry = new AtLeastOneEntry();
        List<String> result = atLeastOneEntry.oneEntryForAllItems(clientCodes, getAllValuesForColumn(codigoCliente));
        if(!result.isEmpty()) {
            validation.addError(new ValidationError(codigoCliente, atLeastOneEntry.createMessage(codigoCliente, result)));
        }
        return validation;
    }
}
