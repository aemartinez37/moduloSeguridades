/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.model;

import ec.edu.espe.arquitectura.nosql.mongo.BaseEntity;
import ec.edu.espe.arquitectura.seguridades.enums.EstadoUsuarioEnum;
import java.util.Date;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author Andrés
 */
@Entity(noClassnameStored = true, value = "SegUsuario")
public class SegUsuario extends BaseEntity {
    
    @Indexed(options = @IndexOptions(name = "SegUsuario_cod_usuarioUIdx", unique = true))
    private String cod_usuario;
    private String cod_persona;
    @Reference
    private SegPerfil perfil;
    private String correo;
    private String nombre;
    private String clave;
    private EstadoUsuarioEnum estado;
    private Date fechaCreacion;
    private Integer intentosErroneos;
    private Date fechaUltimoAcceso;

    public SegUsuario() {
    }

    public SegUsuario(String codigo, String cod_per, SegPerfil perfil, String correo, String nombre, String clave, String estado) {
        this.cod_usuario = codigo;
        this.cod_persona=cod_per;
        this.perfil = perfil;
        this.correo = correo;
        this.nombre = nombre;
        this.clave = clave;
        this.estado = EstadoUsuarioEnum.valueOf(estado);
        this.fechaCreacion=new Date();
        this.intentosErroneos=0;
        this.fechaUltimoAcceso=null;
    }
    
    

    public SegUsuario(SegPerfil perfil) {
        this.perfil = perfil;
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

    public SegPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(SegPerfil perfil) {
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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public EstadoUsuarioEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuarioEnum estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIntentosErroneos() {
        return intentosErroneos;
    }

    public void setIntentosErroneos(Integer intentosErroneos) {
        this.intentosErroneos = intentosErroneos;
    }

    public Date getFechaUltimoAcceso() {
        return fechaUltimoAcceso;
    }

    public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
        this.fechaUltimoAcceso = fechaUltimoAcceso;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cod_usuario != null ? cod_usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUsuario)) {
            return false;
        }
        SegUsuario other = (SegUsuario) object;
        if ((this.cod_usuario == null && other.cod_usuario != null) || (this.cod_usuario != null && !this.cod_usuario.equals(other.cod_usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arquitectura.seguridades.model.SegModulo[ codModulo=" + cod_usuario + " ]";
    }
    
}
