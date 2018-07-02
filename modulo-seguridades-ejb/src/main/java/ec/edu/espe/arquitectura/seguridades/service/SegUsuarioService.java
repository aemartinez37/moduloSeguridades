/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.service;

import ec.edu.espe.arquitectura.nosql.mongo.MongoPersistence;
import ec.edu.espe.arquitectura.seguridades.dao.SegUsuarioDAO;
import ec.edu.espe.arquitectura.seguridades.model.SegUsuario;
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
public class SegUsuarioService {
    
    @EJB
    private MongoPersistence mp;
    private SegUsuarioDAO SegUsuarioFacade;
    
    @PostConstruct
    public void init() {
        this.SegUsuarioFacade = new SegUsuarioDAO(SegUsuario.class, mp.context());
    }
    
    public List<SegUsuario> obtenerTodos() {
        return this.SegUsuarioFacade.find().asList();
    }
    
    public SegUsuario obtenerPorCodigo(String codigo) {
        return this.SegUsuarioFacade.findOne("codigo",codigo);
    }
    
    public void crear(SegUsuario SegUsuario) {
        this.SegUsuarioFacade.save(SegUsuario);
    }
    
    public void modificar(SegUsuario SegUsuario) {
        SegUsuario aux = this.SegUsuarioFacade.findOne("codigo", SegUsuario.getCodigo());
        SegUsuario.setId(aux.getId());
        this.SegUsuarioFacade.save(SegUsuario);
    }
    
    public void eliminar(String codigo) {
        SegUsuario SegUsuario = this.SegUsuarioFacade.findOne("codigo", codigo);
        this.SegUsuarioFacade.delete(SegUsuario);
    }
    
}
