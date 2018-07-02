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
    
    @Indexed(options = @IndexOptions(name = "SegUsuario_codigoUIdx", unique = true))
    private String codigo;
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

    public SegUsuario(String codigo, SegPerfil perfil, String correo, String nombre, String clave, String estado) {
        this.codigo = codigo;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
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
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegRol)) {
            return false;
        }
        SegUsuario other = (SegUsuario) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arquitectura.seguridades.model.SegModulo[ codModulo=" + codigo + " ]";
    }
    
}
