package mati.a7a.files.impl;

import mati.a7a.columns.*;
import mati.a7a.files.AbstractFile;
import mati.a7a.files.FileStereotype;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

import java.util.*;

public class Clientes extends AbstractFile {

    public static final IColumn codigoSucursal = new IntegerColumn("CodigoSucursal", true, false, 10);
    public static final IColumn codigoCliente = new TextColumn("CodigoCliente", true, true, 50);
    public static final IColumn nombre = new TextColumn("Nombre", true, false, 100);
    public static final IColumn domicilio = new TextColumn("Domicilio", true, false, 100);
    public static final IColumn numeroCuit = new TextColumn("NumeroCuit", true, false, 50);
    public static final IColumn idCanalAgrupa =  new IntegerColumn("IdCanalAgrupa", true, false, 6);
    public static final IColumn descCanalAgrupa = new TextColumn("DescCanalAgrupa", true, false, 100);
    public static final IColumn idSubCanalAgrupa =  new IntegerColumn("IdSubCanalAgrupa", true, false, 6);
    public static final IColumn descSubCanalAgrupa = new TextColumn("DescSubCanalAgrupa", true, false, 100);
    public static final IColumn fechaAlta = new DateColumn("FechaAlta", true, false);
    public static final IColumn anulado = new BooleanColumn("Anulado", true, false);
    public static final IColumn fechaAnulacion = new DateColumn("FechaAnulacion", false, false);
    public static final IColumn longitudCoord = new FloatColumn("LongitudCoord", false, false, 6, 10);
    public static final IColumn latitudCoord = new FloatColumn("LatitudCoord", false, false, 6, 10);
    public static final IColumn tipoContribuyente = new TipoContibuyenteColumn("TipoContribuyente", true, false);
    public static final IColumn codListaPrecio = new IntegerColumn("CodListaPrecio", true, false, 10);
    public static final IColumn idTipoDocumentoCliente = new TipoDocumentoColumn("IdTipoDocumentoCliente", true, false);
    public static final IColumn codigoLocalidad = new TextColumn("CodigoLocalidad", true, false, 20);
    public static final IColumn descripcionLocalidad = new TextColumn("DescripcionLocalidad", false, false, 100);
    public static final IColumn codigoProvincia = new TextColumn("CodigoProvincia", true, false, 50);
    public static final IColumn descProvincia = new TextColumn("DescProvincia", true, false, 50);

    private static final List<IColumn> columns =
            Arrays.asList(
                    codigoSucursal,
                    codigoCliente,
                    nombre,
                    domicilio,
                    numeroCuit,
                    idCanalAgrupa,
                    descCanalAgrupa,
                    idSubCanalAgrupa,
                    descSubCanalAgrupa,
                    fechaAlta,
                    anulado,
                    fechaAnulacion,
                    longitudCoord,
                    latitudCoord,
                    tipoContribuyente,
                    codListaPrecio,
                    idTipoDocumentoCliente,
                    codigoLocalidad,
                    descripcionLocalidad,
                    codigoProvincia,
                    descProvincia
            );

    public Clientes(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }

    @Override
    public ValidationResult customValidateFile() {
        ValidationResult validationResult = new ValidationResult();
        List<String> local = this.getAllValuesForColumn(codigoLocalidad);
        List<String> desc = this.getAllValuesForColumn(descripcionLocalidad);
        Map<String, String> keys = new HashMap<>();
        Set<String> invalidLocals = new HashSet<>();
        int idx = 0;
        for(String l : local) {
            if(!keys.containsKey(l)) {
                keys.put(l, desc.get(idx));
            } else if(!keys.get(l).equals(desc.get(idx))) {
                invalidLocals.add(l);
            }
            idx++;
        }
        for(String l : invalidLocals) {
            validationResult.addError(new ValidationError(codigoLocalidad, "La localidad " + l + " tiene distintas descripciones asociadas"));
        }
        return validationResult;
    }
}
