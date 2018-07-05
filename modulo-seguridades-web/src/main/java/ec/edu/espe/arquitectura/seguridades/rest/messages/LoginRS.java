/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest.messages;


/**
 *
 * @author Andrés
 */
public class LoginRS {   
    
    private String cod_usuario;
    private String cod_persona;
    private String perfil;
    private String correo;
    private String nombre;
    
    public LoginRS() {
    }   

    public String getCod_usuario() {
        return cod_usuario;
    }

    public void setCod_usuario(String cod_usuario) {
        this.cod_usuario = cod_usuario;
    }

    public String getCod_persona() {
        return cod_persona;
    }

    public void setCod_persona(String cod_persona) {
        this.cod_persona = cod_persona;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   
    
}
