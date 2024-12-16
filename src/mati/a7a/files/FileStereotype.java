package mati.a7a.files;

public enum FileStereotype {
    ARTICULO("Articulos"),
    CLIENTES("Clientes"),
    PERSONAL_COMERCIAL("PersonalComercial"),
    RUTAS_DE_VENTA("RutasDeVenta"),
    CLIENTES_EN_RUTA("ClientesRuta"),
    STOCK_FISICO("StockFisico"),
    COMPROBANTES("Comprobantes");

    public final String baseName;

    FileStereotype(String baseName) {
        this.baseName = baseName;
    }

    public static final FileStereotype[] getProcessingOrder() {
        return new FileStereotype[]
                { ARTICULO, CLIENTES, PERSONAL_COMERCIAL, CLIENTES_EN_RUTA, RUTAS_DE_VENTA, STOCK_FISICO, COMPROBANTES };
    }
}
