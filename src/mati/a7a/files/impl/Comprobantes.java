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

public class Comprobantes extends AbstractFile {

    public class UniqueKey {
    	String tipoComprobante;
    
                             String letraComprobante;
                             String serieComprobante;
                             String numeroComprobante;
                             String numeroLinea;
                             
        public UniqueKey() {}
        public UniqueKey(String tipoComprobante,
									String letraComprobante,
									String serieComprobante,
									String numeroComprobante, String numeroLinea) {
								super();
								this.tipoComprobante = tipoComprobante;
								this.letraComprobante = letraComprobante;
								this.serieComprobante = serieComprobante;
								this.numeroComprobante = numeroComprobante;
								this.numeroLinea = numeroLinea;
							}

		public UniqueKey create(Row row) {
            Map<IColumn, String> map = row.map;
            return new UniqueKey(
                    map.get(Comprobantes.tipoComprobante),
                    map.get(Comprobantes.letraComprobante),
                    map.get(Comprobantes.serieComprobante),
                    map.get(Comprobantes.numeroComprobante),
                    map.get(Comprobantes.numeroLinea)
                    );
        }

        @Override
        public String toString() {
            return tipoComprobante + ", " + letraComprobante + ", " + serieComprobante + ", " + numeroComprobante + ", " + numeroLinea;
        }
    }

    public static final IColumn codigoEmpresaFactura = new IntegerColumn("CodigoEmpresaFactura", true, false, 10);
    public static final IColumn tipoComprobante = new TextColumn("TipoComprobante", true, false, 6);
    public static final IColumn letraComprobante = new TextColumn("LetraComprobante", true, false, 10);
    public static final IColumn serieComprobante = new IntegerColumn("SerieComprobante", true, false, 10);
    public static final IColumn numeroComprobante = new IntegerColumn("NumeroComprobante", true, false, 10);
    public static final IColumn numeroLinea = new IntegerColumn("NumeroLinea", true, false, 10);
    public static final IColumn codigoFuerza = new ReferentialPredefColumn("CodigoFuerza", true, false, 10);
    public static final IColumn esVenta = new BooleanColumn("EsVenta", true, false);
    public static final IColumn codigoArticulo = new ReferentialPredefColumn("CodigoArticulo", true, false, 10);
    public static final IColumn descripcionArticulo = new TextColumn("DescripcionArticulo", true, false, 50);
    public static final IColumn unidadesPorBulto = new IntegerColumn("UnidadesPorBulto", true, false, 10);
    public static final IColumn cantidadDecimal = new FloatColumn("CantidadDecimal", true, false, 9, 6);
    public static final IColumn precioUnitarioBruto = new FloatColumn("PrecioUnitarioBruto", true, false, 12, 6);
    public static final IColumn bonificacion = new FloatColumn("Bonificacion", true, false, 15, 3);
    public static final IColumn fechaPedido = new DateColumn("FechaPedido", true, false);
    public static final IColumn fechaComprobante = new DateColumn("FechaComprobante", true, false);
    public static final IColumn codigoPersonal = new ReferentialPredefColumn("CodigoPersonal", true, false, 10);
    public static final IColumn codigoCliente = new ReferentialPredefColumn("CodigoCliente", true, false, 50);
    public static final IColumn nombreCliente = new TextColumn("NombreCliente", true, false, 100);
    public static final IColumn codigoSucursal = new ReferentialPredefColumn("CodigoSucursal", true, false, 10);
    public static final IColumn tipoContribuyente = new TipoContibuyenteColumn("TipoContribuyente", true, false);
    public static final IColumn anulado = new BooleanColumn("Anulado", true, false);

    private static final List<IColumn> columns =
            Arrays.asList(
                    codigoEmpresaFactura,
                    tipoComprobante,
                    letraComprobante,
                    serieComprobante,
                    numeroComprobante,
                    numeroLinea,
                    codigoFuerza,
                    esVenta,
                    codigoArticulo,
                    descripcionArticulo,
                    unidadesPorBulto,
                    cantidadDecimal,
                    precioUnitarioBruto,
                    bonificacion,
                    fechaPedido,
                    fechaComprobante,
                    codigoPersonal,
                    codigoCliente,
                    nombreCliente,
                    codigoSucursal,
                    tipoContribuyente,
                    anulado
            );

    public Comprobantes(FileStereotype stereotype, String name) {
        super(stereotype, name, columns);
    }

    public void setFuerzas(List<String> fuerzas) {
        ((ReferentialPredefColumn) codigoFuerza).overridePossibleValues(fuerzas);
    }

    public void setArticulos(List<String> articulos) {
        ((ReferentialPredefColumn) codigoArticulo).overridePossibleValues(articulos);
    }

    public void setPersonal(List<String> personal) {
        ((ReferentialPredefColumn) codigoPersonal).overridePossibleValues(personal);
    }

    public void setClientes(List<String> clientes) {
        ((ReferentialPredefColumn) codigoCliente).overridePossibleValues(clientes);
    }

    public void setSucursales(List<String> sucursales) {
        ((ReferentialPredefColumn) codigoSucursal).overridePossibleValues(sucursales);
    }

    @Override
    public ValidationResult customValidateFile() throws ProcessException {
        ValidationResult result = new ValidationResult();

        Map<UniqueKey, Integer> keyCount = new HashMap<>();
        for(Row r : getRows()) {
            UniqueKey uniqueKey = new UniqueKey().create(r);
            if(!keyCount.containsKey(uniqueKey)) {
                keyCount.put(uniqueKey, 1);
            } else {
                keyCount.put(uniqueKey, keyCount.get(uniqueKey) + 1);
            }
        }

        for(Map.Entry<UniqueKey, Integer> entry : keyCount.entrySet()) {
            if(entry.getValue() > 1) {
                result.addError(new ValidationError(numeroComprobante, "Clave repetida: " + entry.getKey()));
            }
        }

        return result;
    }
}





















