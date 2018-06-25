/*
 * Hades Cruise
 * Aplicaciones Distribuidas
 * NRC: 2434 
 * Tutor: HENRY RAMIRO CORAL CORAL 
 * 2017 (c) Hades Cruise Corp.
 */
package ec.edu.espe.arquitectura.seguridades.enums;

/**
 *
 * @author Hades Cruise Corp.
 */
public enum EstadoUsuarioEnum {
    
    ACT("ACTIVO"),
    INA("INACTIVO"),
    SUS("SUSPENDIDO"),
    BLO("BLOQUEADO"),
    PEN("PENDIENTE");

    String texto;

    private EstadoUsuarioEnum(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
