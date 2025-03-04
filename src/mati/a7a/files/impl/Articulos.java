package mati.a7a.files.impl;

import mati.a7a.columns.*;
import mati.a7a.files.AbstractFile;
import mati.a7a.files.FileStereotype;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Articulos extends AbstractFile {

    public static final IColumn codigoArticulo = new IntegerColumn("CodigoArticulo", true, true, 10);
    public static final IColumn descripcionArticulo = new TextColumn("DescripcionArticulo", true, false, 50);
    public static final IColumn anulado = new BooleanColumn("Anulado", true, false);
    public static final IColumn unidadesXBulto = new IntegerColumn("UnidadesXBulto", true, false, 10);
    public static final IColumn valorUMedida = new FloatColumn("ValorUMedida", true, false, 8, 4);
    private static final List<IColumn> columns =
            Arrays.asList(
                    codigoArticulo,
                    descripcionArticulo,
                    anulado,
                    unidadesXBulto,
                    valorUMedida
            );

    public Articulos(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }

    public Map<String, String> getUnidadesXBulto() {
        Map<String, String> result = new HashMap<>();
        List<String> articulos = this.getAllValuesForColumn(codigoArticulo);
        List<String> uxb = this.getAllValuesForColumn(unidadesXBulto);
        for(int i = 0; i < articulos.size(); i++) {
            result.put(articulos.get(i), uxb.get(i));
        }
        return result;
    }

    @Override
    public ValidationResult customValidateFile() {
        return new ValidationResult();
    }
}
