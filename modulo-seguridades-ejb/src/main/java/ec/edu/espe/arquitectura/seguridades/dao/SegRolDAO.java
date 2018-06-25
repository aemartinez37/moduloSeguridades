/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.dao;

import ec.edu.espe.arquitectura.seguridades.model.SegRol;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 *
 * @author Andrés
 */
public class SegRolDAO extends BasicDAO<SegRol, ObjectId> {
    
    public SegRolDAO(Class<SegRol> objectEntity, Datastore ds) {
        super(objectEntity, ds);
    }
    
}
