/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.model;

import ec.edu.espe.arquitectura.nosql.mongo.BaseEntity;
import ec.edu.espe.arquitectura.seguridades.enums.EstadoPerfilFuncionalidadEnum;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author Andrés
 */
@Entity(noClassnameStored = true, value = "SegPerfilFuncionalidad")
public class SegPerfilFuncionalidad extends BaseEntity {
    
    @Indexed(options = @IndexOptions(name = "SegPerfilFuncionalidad_codigoUIdx", unique = true))
    private Integer codigo;
    @Reference
    private SegFuncionalidad segFuncionalidad;
    @Reference
    private SegPerfil perfil;
    private EstadoPerfilFuncionalidadEnum estado;

    public SegPerfilFuncionalidad() {
    }

    public SegPerfilFuncionalidad(SegFuncionalidad segFuncionalidad, SegPerfil perfil) {
        this.segFuncionalidad = segFuncionalidad;
        this.perfil = perfil;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    
    
    public SegFuncionalidad getSegFuncionalidad() {
        return segFuncionalidad;
    }

    public void setSegFuncionalidad(SegFuncionalidad segFuncionalidad) {
        this.segFuncionalidad = segFuncionalidad;
    }

    public SegPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(SegPerfil perfil) {
        this.perfil = perfil;
    }

    public EstadoPerfilFuncionalidadEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoPerfilFuncionalidadEnum estado) {
        this.estado = estado;
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
        SegPerfilFuncionalidad other = (SegPerfilFuncionalidad) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arquitectura.seguridades.model.SegPerfilFuncionalidad[ segPerfilFuncionalidad=" + codigo + " ]";
    }
    
    
}
