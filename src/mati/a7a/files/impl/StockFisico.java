package mati.a7a.files.impl;

import mati.a7a.columns.*;
import mati.a7a.files.AbstractFile;
import mati.a7a.files.FileStereotype;
import mati.a7a.files.Row;
import mati.a7a.main.ProcessException;
import mati.a7a.results.ValidationError;
import mati.a7a.results.ValidationResult;

import java.util.*;

public class StockFisico extends AbstractFile {

    public static final IColumn codigoDeposito = new IntegerColumn("CodigoDeposito", true, true, 10);
    public static final IColumn codigoArticulo = new ReferentialPredefColumn("CodigoArticulo", true, true, 10);
    public static final IColumn vencimientoLote = new DateColumn("VencimientoLote", true, false);
    public static final IColumn cantidadDecimal = new FloatColumn("CantidadDecimal", true, false, 9, 6);
    public static final IColumn fechaStock = new DateColumn("FechaStock", true, false);

    private static final List<IColumn> columns =
            Arrays.asList(
                    codigoDeposito,
                    codigoArticulo,
                    vencimientoLote,
                    cantidadDecimal,
                    fechaStock
            );

    private List<String> codigosArticulos;

    public StockFisico(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }

    public void setCodigosArticulos(List<String> codigosArticulos) {
        this.codigosArticulos = new ArrayList<>();
        for (String c : codigosArticulos) {
            if (!codigosArticulos.contains(c)) {
                this.codigosArticulos.add(c);
            }
        }
        ((ReferentialPredefColumn) codigoArticulo).overridePossibleValues(this.codigosArticulos);
    }

    @Override
    public ValidationResult customValidateFile() throws ProcessException {
        ValidationResult result = new ValidationResult();

        int nArticulosPosibles = ((ReferentialPredefColumn) codigoArticulo).getPossibleValues().size();

        Map<String, List<String>> articulosPorDeposito = new HashMap<>();

        for(Row r : getRows()) {
            String codigoDeposito = r.map.get(StockFisico.codigoDeposito);
            if(!articulosPorDeposito.containsKey(codigoDeposito)) {
                articulosPorDeposito.put(codigoDeposito, new ArrayList<>());
            }
            articulosPorDeposito.get(codigoDeposito).add(r.map.get(codigoArticulo));
        }

        for(Map.Entry<String, List<String>> entry : articulosPorDeposito.entrySet()) {
            if(entry.getValue().size() != nArticulosPosibles) {
                result.addError(new ValidationError(codigoDeposito, "La cantidad de articulos para el depósito " + entry.getKey() + " debería ser " + nArticulosPosibles));
            }
        }

        return result;
    }


}




