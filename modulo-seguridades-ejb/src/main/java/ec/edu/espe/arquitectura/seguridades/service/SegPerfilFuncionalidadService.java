/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.service;

import ec.edu.espe.arquitectura.nosql.mongo.MongoPersistence;
import ec.edu.espe.arquitectura.seguridades.dao.SegPerfilFuncionalidadDAO;
import ec.edu.espe.arquitectura.seguridades.model.SegPerfilFuncionalidad;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Andrés
 */
@Stateless
@LocalBean
public class SegPerfilFuncionalidadService {
    
    @EJB
    private MongoPersistence mp;
    private SegPerfilFuncionalidadDAO segPerfilFuncionalidadFacade;
    
    @PostConstruct
    public void init() {
        this.segPerfilFuncionalidadFacade = new SegPerfilFuncionalidadDAO(SegPerfilFuncionalidad.class, mp.context());
    }
    
    public List<SegPerfilFuncionalidad> obtenerTodos() {
        return this.segPerfilFuncionalidadFacade.find().asList();
    }
    
    public SegPerfilFuncionalidad obtenerPorCodigo(String codigo) {
        return this.segPerfilFuncionalidadFacade.findOne("codigo",codigo);
    }
    
    public void crear(SegPerfilFuncionalidad segPerfilFuncionalidad) {
        List<SegPerfilFuncionalidad> aux = this.segPerfilFuncionalidadFacade.find().asList();
        Integer codigo;
        if (aux.isEmpty()) {
            codigo = 1;
        } else {
            Integer count = aux.size();
            SegPerfilFuncionalidad last = aux.get(count - 1);
            codigo = last.getCodigo() + 1;
        }
        segPerfilFuncionalidad.setCodigo(codigo);
        this.segPerfilFuncionalidadFacade.save(segPerfilFuncionalidad);
    }
    
    public void modificar(SegPerfilFuncionalidad segPerfilFuncionalidad) {
        SegPerfilFuncionalidad aux = this.segPerfilFuncionalidadFacade.findOne("codigo", segPerfilFuncionalidad.getCodigo());
        segPerfilFuncionalidad.setId(aux.getId());
        this.segPerfilFuncionalidadFacade.save(segPerfilFuncionalidad);
    }
    
    public void eliminar(String codigo) {
        SegPerfilFuncionalidad segPerfilFuncionalidad = this.segPerfilFuncionalidadFacade.findOne("codigo", codigo);
        this.segPerfilFuncionalidadFacade.delete(segPerfilFuncionalidad);
    }
    
}