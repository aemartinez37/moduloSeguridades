/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.service;

import ec.edu.espe.arquitectura.nosql.mongo.MongoPersistence;
import ec.edu.espe.arquitectura.seguridades.dao.SegFuncionalidadDAO;
import ec.edu.espe.arquitectura.seguridades.model.SegFuncionalidad;
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
public class SegFuncionalidadService {
    
    @EJB
    private MongoPersistence mp;
    private SegFuncionalidadDAO segFuncionalidadFacade;
    
    @PostConstruct
    public void init() {
        this.segFuncionalidadFacade = new SegFuncionalidadDAO(SegFuncionalidad.class, mp.context());
    }
    
    public List<SegFuncionalidad> obtenerTodos() {
        return this.segFuncionalidadFacade.find().asList();
    }
    
    public SegFuncionalidad obtenerPorCodigo(String codigo) {
        return this.segFuncionalidadFacade.findOne("codigo",codigo);
    }
    
    public void crear(SegFuncionalidad segFuncionalidad) {
        List<SegFuncionalidad> aux = this.segFuncionalidadFacade.find().asList();
        Integer codigo;
        if (aux.isEmpty()) {
            codigo = 1;
        } else {
            Integer count = aux.size();
            SegFuncionalidad last = aux.get(count - 1);
            codigo = last.getCodigo() + 1;
        }
        segFuncionalidad.setCodigo(codigo);
        this.segFuncionalidadFacade.save(segFuncionalidad);
    }
    
    public void modificar(SegFuncionalidad segFuncionalidad) {
        SegFuncionalidad aux = this.segFuncionalidadFacade.findOne("codigo", segFuncionalidad.getCodigo());
        segFuncionalidad.setId(aux.getId());
        this.segFuncionalidadFacade.save(segFuncionalidad);
    }
    
    public void eliminar(String codigo) {
        SegFuncionalidad segFuncionalidad = this.segFuncionalidadFacade.findOne("codigo", codigo);
        this.segFuncionalidadFacade.delete(segFuncionalidad);
    }
    
}
