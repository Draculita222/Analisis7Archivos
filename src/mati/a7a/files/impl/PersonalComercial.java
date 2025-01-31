package mati.a7a.files.impl;

import mati.a7a.columns.*;
import mati.a7a.files.AbstractFile;
import mati.a7a.files.FileStereotype;
import mati.a7a.files.Row;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonalComercial extends AbstractFile {

    public static final IColumn codigoSucursal = new ReferentialPredefColumn("CodigoSucursal", true, false, 10);
    public static final IColumn codigoPersonal = new IntegerColumn("CodigoPersonal", true, true, 10);
    public static final IColumn descripcion = new TextColumn("Descripcion", true, false, 50);
    public static final IColumn cargo = new CargoColumn("Cargo", true, false);
    public static final IColumn anulado = new BooleanColumn("Anulado", true, false);
    public static final IColumn codigoPersonalSuperior = new IntegerColumn("CodigoPersonalSuperior", true, false, 10);
    public static final IColumn codigoFuerza = new IntegerColumn("CodigoFuerza", true, false, 10);

    private static final List<IColumn> columns =
            Arrays.asList(
                    codigoSucursal,
                    codigoPersonal,
                    descripcion,
                    cargo,
                    anulado,
                    codigoPersonalSuperior,
                    codigoFuerza
            );

    public PersonalComercial(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }
    
    public void setSucursales(List<String> sucursales) {
        ((ReferentialPredefColumn) PersonalComercial.codigoSucursal).overridePossibleValues(sucursales);
    }
    

    @Override
    public ValidationResult customValidateFile() {
        ValidationResult result = new ValidationResult();

        Map<String, String> persona2Cargo = new HashMap<>();
        for(Row r : getRows()) {
            String vendedor = r.map.get(codigoPersonal);
            String cargo = r.map.get(PersonalComercial.cargo);
            persona2Cargo.put(vendedor, cargo);
        }

        for(Row r : getRows()) {
            String cargoActual = r.map.get(PersonalComercial.cargo);
            String refSuperior = r.map.get(codigoPersonalSuperior);
            if(refSuperior != null && !persona2Cargo.containsKey(refSuperior)) {
                result.addError(
                        new ValidationError(codigoPersonal,
                                "El personal con código " + r.map.get(codigoPersonal) + " apunta a un superior inexistente"));
                continue;
            }
            if(refSuperior == null) {
            	continue;
            }
            String cargoSuperior = persona2Cargo.get(refSuperior).toLowerCase();
            boolean invalid =
                    (cargoActual.equals("G") && (cargoSuperior.equals("S") || cargoSuperior.equals("V"))) ||
                            (cargoActual.equals("S") && cargoSuperior.equals("V"));
            if(invalid) {
                result.addError(
                        new ValidationError(codigoPersonal,
                                "El personal con código " + r.map.get(codigoPersonal)
                                        + " no puede ser supervisado por " + refSuperior));
            }
        }

        return result;
    }
}










