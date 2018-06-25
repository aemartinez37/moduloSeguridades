/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;


import ec.edu.espe.arquitectura.seguridades.model.SegRol;
import ec.edu.espe.arquitectura.seguridades.service.SegRolService;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Andrés
 */
@Path("SegRol")
@RequestScoped
public class SegRolResource {

    @Context
    private UriInfo context;
    
    @Inject
    private SegRolService segRolServ;
    

    /**
     * Creates a new instance of SegRolResource
     */
    public SegRolResource() {
    }

    
    @GET
    //@Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRoles() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        List<SegRol> roles=this.segRolServ.obtenerTodos();
        GenericEntity<List<SegRol>> ge=new GenericEntity<List<SegRol>>(roles){};
        
        return Response.ok(ge).build();
        
    }

   
    @POST
//    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarRol(SegRol rol) {
        
        this.segRolServ.crear(rol);
        
        String result = "Rol guardado : " + rol;
	return Response.status(201).entity(result).build();
        
    }
}
