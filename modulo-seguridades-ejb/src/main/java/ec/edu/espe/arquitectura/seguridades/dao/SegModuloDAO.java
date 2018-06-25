/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.dao;

import ec.edu.espe.arquitectura.seguridades.model.SegModulo;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

/**
 *
 * @author Andrés
 */
public class SegModuloDAO extends BasicDAO<SegModulo, ObjectId> {
    
    public SegModuloDAO(Class<SegModulo> objectEntity, Datastore ds) {
        super(objectEntity, ds);
    }
    
}
