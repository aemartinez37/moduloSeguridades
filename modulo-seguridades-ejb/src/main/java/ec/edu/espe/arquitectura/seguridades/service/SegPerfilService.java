/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.service;

import ec.edu.espe.arquitectura.nosql.mongo.MongoPersistence;
import ec.edu.espe.arquitectura.seguridades.dao.SegPerfilDAO;
import ec.edu.espe.arquitectura.seguridades.model.SegPerfil;
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
public class SegPerfilService {
    
    @EJB
    private MongoPersistence mp;
    private SegPerfilDAO segPerfilFacade;
    
    @PostConstruct
    public void init() {
        this.segPerfilFacade = new SegPerfilDAO(SegPerfil.class, mp.context());
    }
    
    public List<SegPerfil> obtenerTodos() {
        return this.segPerfilFacade.find().asList();
    }
    
    public SegPerfil obtenerPorCodigo(String codigo) {
        return this.segPerfilFacade.findOne("codigo",codigo);
    }
    
    public void crear(SegPerfil segPerfil) {
        this.segPerfilFacade.save(segPerfil);
    }
    
    public void modificar(SegPerfil segPerfil) {
        SegPerfil aux = this.segPerfilFacade.findOne("codigo", segPerfil.getCodigo());
        segPerfil.setId(aux.getId());
        this.segPerfilFacade.save(segPerfil);
    }
    
    public void eliminar(String codigo) {
        SegPerfil segPerfil = this.segPerfilFacade.findOne("codigo", codigo);
        this.segPerfilFacade.delete(segPerfil);
    }
    
}
