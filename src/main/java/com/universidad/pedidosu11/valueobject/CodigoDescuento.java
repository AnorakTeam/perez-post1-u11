package com.universidad.pedidosu11.valueobject;

public class CodigoDescuento {
    private final String codigo;
    private final double porcentaje;
    
    public CodigoDescuento(String codigo) {
        if (codigo == null) {
            this.codigo = null;
            this.porcentaje = 0.0;
            return;
        }
        
        this.codigo = codigo;
        this.porcentaje = switch (codigo) {
            case "VIP10" -> 0.10;
            case "NEW20" -> 0.20;
            default -> 0.0;
        };
    }
    
    public String getCodigo() { return codigo; }
    public double getPorcentaje() { return porcentaje; }
    public boolean esValido() { return codigo != null && porcentaje > 0; }
}