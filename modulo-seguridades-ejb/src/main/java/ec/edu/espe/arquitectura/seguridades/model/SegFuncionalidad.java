/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.model;

import ec.edu.espe.arquitectura.nosql.mongo.BaseEntity;
import ec.edu.espe.arquitectura.seguridades.enums.EstadoFuncionalidadEnum;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;

/**
 *
 * @author Andrés
 */
@Entity(noClassnameStored = true, value = "SegFuncionalidad")
public class SegFuncionalidad extends BaseEntity {
    
    @Indexed(options = @IndexOptions(name = "SegFuncionalidad_codigoUIdx", unique = true))
    private Integer codigo;
    @Reference
    private SegModulo modulo;
    private String nombre;
    private String url;
    private EstadoFuncionalidadEnum estado;

    public SegFuncionalidad() {
    }

    public SegFuncionalidad(SegModulo modulo) {
        this.modulo = modulo;
    }

    public Integer getSec_funcionalidad() {
        return codigo;
    }

    public void setSec_funcionalidad(Integer sec_funcionalidad) {
        this.codigo = sec_funcionalidad;
    }

    public SegModulo getModulo() {
        return modulo;
    }

    public void setModulo(SegModulo modulo) {
        this.modulo = modulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public EstadoFuncionalidadEnum getEstado() {
        return estado;
    }

    public void setEstado(EstadoFuncionalidadEnum estado) {
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
        SegFuncionalidad other = (SegFuncionalidad) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arquitectura.seguridades.model.SegFuncionalidad[ segFuncionalidad=" + codigo + " ]";
    }
    
}
