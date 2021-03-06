/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;

import ec.edu.espe.arquitectura.seguridades.model.SegPerfil;
import ec.edu.espe.arquitectura.seguridades.rest.messages.SegPerfilRQ;
import ec.edu.espe.arquitectura.seguridades.service.SegPerfilService;
import java.io.IOException;
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
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * REST Web Service
 *
 * @author Andrés
 */
@Path("SegPerfil")
@RequestScoped
public class SegPerfilResource {

    @Context
    private UriInfo context;

    @Inject
    private SegPerfilService segPerfilServ;

    /**
     * Creates a new instance of SegRolResource
     */
    public SegPerfilResource() {
    }
    @GET
    @Path("buscar/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRolesCodigo(@PathParam("codigo") String codigo) {
        
        String json=null;
        Response resp=null;

        if ("todos".equals(codigo)) {
            List<SegPerfil> perfiles = this.segPerfilServ.obtenerTodos();
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);          
             
            try {
                json = mapper.writeValueAsString(perfiles);
                return resp.ok(json).build();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR LISTAR TODOS PERFILES!");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            
            
            
        } else {
            SegPerfil perfil = this.segPerfilServ.obtenerPorCodigo(codigo);
            if (perfil != null) {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                try {
                    json = mapper.writeValueAsString(perfil);
                    return resp.ok(json).build();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ERROR BUSCAR PERFIL POR CODIGO!");
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
                
            } else {               

                //resp.status(Response.Status.NOT_FOUND);
                //return resp;
                System.out.println("ERROR - NO EXISTE PERFIL!");
                return Response.status(Response.Status.NOT_FOUND).build();
                
            }
        }        

    }

    @PUT
    @Path("nuevo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJsonNuevo(SegPerfilRQ request) {

        SegPerfil perfil = new SegPerfil(request.getCodigo(), request.getNombre(), request.getEstado());

        String json = null;
        Response resp=null;
        
        if(segPerfilServ.obtenerPorCodigo(perfil.getCodigo())==null)
        {
            try {
                segPerfilServ.crear(perfil);            
                //resp.status(Response.Status.CREATED);
                return Response.status(Response.Status.OK).build();

            } catch (Exception e) {    
                  //resp.status(Response.Status.BAD_REQUEST);
                  return Response.status(Response.Status.FORBIDDEN).build();
            }
        }
        else
        {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        //return resp;
    } 

    @POST
    @Path("actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJsonActualizar(SegPerfilRQ request) {

        SegPerfil perfil = new SegPerfil(request.getCodigo(), request.getNombre(), request.getEstado());

        String json = null;
        Response resp=null;
          
        try {
            segPerfilServ.modificar(perfil);
            //resp.status(Response.Status.OK);
            return Response.status(Response.Status.OK).build();            
        } catch (Exception e) {
            //resp.status(Response.Status.BAD_REQUEST);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        //return resp;
    }

    @DELETE
    @Path("eliminar/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonEliminar(@PathParam("codigo") String codigo) {
        
        String json = null;
        Response resp=null;

        try {
            segPerfilServ.eliminar(codigo);
            //resp.status(Response.Status.OK);  
            return Response.status(Response.Status.OK).build();
          
        } catch (Exception e) {
            //resp.status(Response.Status.BAD_REQUEST);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        //return resp;
    }

}
