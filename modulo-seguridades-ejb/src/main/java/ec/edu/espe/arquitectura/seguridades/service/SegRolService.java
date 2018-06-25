/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.service;

import ec.edu.espe.arquitectura.nosql.mongo.MongoPersistence;
import ec.edu.espe.arquitectura.seguridades.dao.SegRolDAO;
import ec.edu.espe.arquitectura.seguridades.model.SegRol;
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
public class SegRolService {
    
    @EJB
    private MongoPersistence mp;
    private SegRolDAO segRolFacade;
    
    @PostConstruct
    public void init() {
        this.segRolFacade = new SegRolDAO(SegRol.class, mp.context());
    }
    
    public List<SegRol> obtenerTodos() {
        return this.segRolFacade.find().asList();
    }
    
    public SegRol obtenerPorCodigo(String codigo) {
        return this.segRolFacade.findOne("codigo",codigo);
    }
    
    public void crear(SegRol segRol) {
        this.segRolFacade.save(segRol);
    }
    
    public void modificar(SegRol segRol) {
        SegRol aux = this.segRolFacade.findOne("codigo", segRol.getCodigo());
        segRol.setId(aux.getId());
        this.segRolFacade.save(segRol);
    }
    
    public void eliminar(String codigo) {
        SegRol segRol = this.segRolFacade.findOne("codigo", codigo);
        this.segRolFacade.delete(segRol);
    }
    
}
