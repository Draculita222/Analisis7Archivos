package mati.a7a.files.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mati.a7a.columns.DateColumn;
import mati.a7a.columns.IColumn;
import mati.a7a.columns.IntegerColumn;
import mati.a7a.columns.TextColumn;
import mati.a7a.files.AbstractFile;
import mati.a7a.files.FileStereotype;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;
import mati.a7a.validations.UniqueA2UniqueB;

public class RutasDeVenta extends AbstractFile {

    public static final IColumn codigoSucursal = new IntegerColumn("CodigoSucursal", true, false, 10);
    public static final IColumn codigoFuerza = new IntegerColumn("CodigoFuerza", true, false, 10);
    public static final IColumn codigoModoAtencion = new TextColumn("CodigoModoAtencion", true, false, 5);
    public static final IColumn codigoRuta = new TextColumn("CodigoRuta", true, true, 50);
    public static final IColumn descripcionRuta = new TextColumn("DescripcionRuta", false, false, 50);
    public static final IColumn codigoPersonal = new IntegerColumn("CodigoPersonal", true, false, 10);
    public static final IColumn fechaDesde = new DateColumn("FechaDesde", true, false);


    private static final List<IColumn> columns =
            Arrays.asList(
                    codigoSucursal,
                    codigoFuerza,
                    codigoModoAtencion,
                    codigoRuta,
                    descripcionRuta,
                    codigoPersonal,
                    fechaDesde
            );

    public RutasDeVenta(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }

    @Override
    public ValidationResult customValidateFile(){
        ValidationResult validation = new ValidationResult();

        UniqueA2UniqueB uniqueA2UniqueB = new UniqueA2UniqueB();
        Map<String, List<String>> result = new HashMap<>();
		try {
			result = uniqueA2UniqueB.analize(codigoRuta, codigoPersonal, getAllValuesForColumnWithIndex(codigoRuta), getAllValuesForColumnWithIndex(codigoPersonal));
		} catch (ProcessException e) {
			validation.addError(new ValidationError(codigoRuta, e.getMessage()));
		}
        if(!result.isEmpty()) {
            String message = uniqueA2UniqueB.createMessage(codigoRuta, codigoPersonal, result);
            validation.addError(new ValidationError(codigoRuta, message));
        }

        return validation;


    }
}
