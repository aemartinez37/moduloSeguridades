/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.dao;

import ec.edu.espe.arquitectura.seguridades.model.SegPerfilFuncionalidad;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 *
 * @author Andrés
 */
public class SegPerfilFuncionalidadDAO extends BasicDAO<SegPerfilFuncionalidad, ObjectId> {
    
    public SegPerfilFuncionalidadDAO(Class<SegPerfilFuncionalidad> objectEntity, Datastore ds) {
        super(objectEntity, ds);
    }
    
}
