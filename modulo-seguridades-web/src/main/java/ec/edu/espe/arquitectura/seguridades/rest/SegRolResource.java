/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;

import ec.edu.espe.arquitectura.seguridades.model.SegRol;
import ec.edu.espe.arquitectura.seguridades.rest.messages.MensajeRS;
import ec.edu.espe.arquitectura.seguridades.rest.messages.SegRolRQ;
import ec.edu.espe.arquitectura.seguridades.service.SegRolService;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
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
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRoles() {
        //TODO return proper representation object
        //throw new UnsupportedOperationException();
        List<SegRol> roles = this.segRolServ.obtenerTodos();
        GenericEntity<List<SegRol>> ge = new GenericEntity<List<SegRol>>(roles) {
        };

        return Response.ok(ge).build();

    }

    @GET
    @Path("buscar/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRolesCodigo(@PathParam("codigo") String codigo) {

        if ("todos".equals(codigo)) {
            List<SegRol> roles = this.segRolServ.obtenerTodos();
            GenericEntity<List<SegRol>> ge = new GenericEntity<List<SegRol>>(roles) {
            };
            return Response.ok(ge).build();
        } else {
            SegRol rol = this.segRolServ.obtenerPorCodigo(codigo);
            if (rol != null) {
                return Response.ok(rol).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }

        //TODO return proper representation object
        //throw new UnsupportedOperationException();
//        List<SegRol> roles = this.segRolServ.obtenerTodos();
//        GenericEntity<List<SegRol>> ge = new GenericEntity<List<SegRol>>(roles) {
//        };
//
//        return Response.ok(ge).build();

    }

    @POST
    @Path("nuevo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonNuevo(SegRolRQ request) {

        SegRol rol = new SegRol(request.getCodigo(), request.getNombre(), request.getEstado());

        MensajeRS response = new MensajeRS();

        //Gson gson=new Gson();        
        try {
            segRolServ.crear(rol);
            response.setOk(1);
        } catch (Exception e) {
            response.setOk(0);
        }

        return Response.ok(response).build();
    }
    
    @POST
    @Path("crear")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonCrear(SegRolRQ request) {

        SegRol rol = new SegRol(request.getCodigo(), request.getNombre(), request.getEstado());

        MensajeRS response = new MensajeRS();

        //Gson gson=new Gson();        
        try {
            segRolServ.crear(rol);
            response.setOk(1);
        } catch (Exception e) {
            response.setOk(0);
        }

        return Response.ok(response).build();
    }

    @POST
    @Path("actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonActualizar(SegRolRQ request) {

        SegRol rol = new SegRol(request.getCodigo(), request.getNombre(), request.getEstado());

        MensajeRS response = new MensajeRS();

        //Gson gson=new Gson();        
        try {
            segRolServ.modificar(rol);
            response.setOk(1);
        } catch (Exception e) {
            response.setOk(0);
        }

        return Response.ok(response).build();
    }

    @DELETE
    @Path("eliminar/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonEliminar(@PathParam("codigo") String codigo) {
        
        MensajeRS response = new MensajeRS();

        //Gson gson=new Gson();        
        try {
            segRolServ.eliminar(codigo);
            response.setOk(1);
        } catch (Exception e) {
            response.setOk(0);
        }

        return Response.ok(response).build();
    }

}
