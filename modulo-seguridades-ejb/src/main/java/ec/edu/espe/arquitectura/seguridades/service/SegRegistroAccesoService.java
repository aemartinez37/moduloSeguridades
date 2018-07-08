/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.service;

import ec.edu.espe.arquitectura.nosql.mongo.MongoPersistence;
import ec.edu.espe.arquitectura.seguridades.dao.SegRegistroAccesoDAO;
import ec.edu.espe.arquitectura.seguridades.model.SegRegistroAcceso;
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
public class SegRegistroAccesoService {
    
    @EJB
    private MongoPersistence mp;
    private SegRegistroAccesoDAO segRegistroAccesoFacade;
    
    @PostConstruct
    public void init() {
        this.segRegistroAccesoFacade = new SegRegistroAccesoDAO(SegRegistroAcceso.class, mp.context());
    }
    
    public List<SegRegistroAcceso> obtenerTodos() {
        return this.segRegistroAccesoFacade.find().asList();
    }
    
    public SegRegistroAcceso obtenerPorCodigo(Integer codigo) {
        return this.segRegistroAccesoFacade.findOne("codigo",codigo);
    }
    
    public void crear(SegRegistroAcceso segRegistroAcceso) {
        List<SegRegistroAcceso> aux = this.segRegistroAccesoFacade.find().asList();
        Integer codigo;
        if (aux.isEmpty()) {
            codigo = 1;
        } else {
            Integer count = aux.size();
            SegRegistroAcceso last = aux.get(count - 1);
            codigo = last.getCodigo() + 1;
        }
        segRegistroAcceso.setCodigo(codigo);
        this.segRegistroAccesoFacade.save(segRegistroAcceso);
    }
    
    public void modificar(SegRegistroAcceso segRegistroAcceso) {
        SegRegistroAcceso aux = this.segRegistroAccesoFacade.findOne("codigo", segRegistroAcceso.getCodigo());
        segRegistroAcceso.setId(aux.getId());
        this.segRegistroAccesoFacade.save(segRegistroAcceso);
    }
    
    public void eliminar(String codigo) {
        SegRegistroAcceso segRegistroAcceso = this.segRegistroAccesoFacade.findOne("codigo", codigo);
        this.segRegistroAccesoFacade.delete(segRegistroAcceso);
    }
    
}
