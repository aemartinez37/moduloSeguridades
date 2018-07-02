/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.service;

import ec.edu.espe.arquitectura.nosql.mongo.MongoPersistence;
import ec.edu.espe.arquitectura.seguridades.dao.SegModuloDAO;
import ec.edu.espe.arquitectura.seguridades.model.SegModulo;
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
public class SegModuloService {
    
    @EJB
    private MongoPersistence mp;
    private SegModuloDAO segModuloFacade;
    
    @PostConstruct
    public void init() {
        this.segModuloFacade = new SegModuloDAO(SegModulo.class, mp.context());
    }
    
    public List<SegModulo> obtenerTodos() {
        return this.segModuloFacade.find().asList();
    }
    
    public SegModulo obtenerPorCodigo(String codigo) {
        return this.segModuloFacade.findOne("codigo",codigo);
    }
    
    public void crear(SegModulo segModulo) {
        this.segModuloFacade.save(segModulo);
    }
    
    public void modificar(SegModulo segModulo) {
        SegModulo aux = this.segModuloFacade.findOne("codigo", segModulo.getCodigo());
        segModulo.setId(aux.getId());
        this.segModuloFacade.save(segModulo);
    }
    
    public void eliminar(String codigo) {
        SegModulo segModulo = this.segModuloFacade.findOne("codigo", codigo);
        this.segModuloFacade.delete(segModulo);
    }
    
}
