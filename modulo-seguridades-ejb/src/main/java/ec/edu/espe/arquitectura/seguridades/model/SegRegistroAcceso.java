/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.model;

import ec.edu.espe.arquitectura.nosql.mongo.BaseEntity;
import java.util.Date;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

/**
 *
 * @author Andrés
 */
@Entity(noClassnameStored = true, value = "SegRegistroAcceso")
public class SegRegistroAcceso extends BaseEntity {
    
    @Indexed(options = @IndexOptions(name = "SegRegistroAcceso_codigoUIdx", unique = true))
    private Integer codigo;
    private String tipoAcceso;
    private String codigoUsuario;
    private String perfil;
    private String ip;
    private String funcionalidad;
    private String resultado;
    private Date fecha;

    public SegRegistroAcceso() {
    }

    public SegRegistroAcceso(String tipoAcceso, String codigoUsuario,String perfil, String ip, String funcionalidad, String resultado) {
        this.codigo=0;
        this.tipoAcceso = tipoAcceso;
        this.codigoUsuario = codigoUsuario;
        this.perfil=perfil;
        this.ip = ip;
        this.funcionalidad = funcionalidad;
        this.resultado = resultado;
        this.fecha=new Date();
    }
    

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(String tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFuncionalidad() {
        return funcionalidad;
    }

    public void setFuncionalidad(String funcionalidad) {
        this.funcionalidad = funcionalidad;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
        if (!(object instanceof SegRegistroAcceso)) {
            return false;
        }
        SegRegistroAcceso other = (SegRegistroAcceso) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.edu.espe.arquitectura.seguridades.model.SegRegistroAcceso[ segRegistroAcceso=" + codigo + " ]";
    }
    
}
