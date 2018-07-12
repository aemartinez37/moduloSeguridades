/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;

import ec.edu.espe.arquitectura.seguridades.model.SegUsuario;
import ec.edu.espe.arquitectura.seguridades.rest.messages.SegUsuarioRQ;
import ec.edu.espe.arquitectura.seguridades.service.SegPerfilService;
import ec.edu.espe.arquitectura.seguridades.service.SegUsuarioService;
import java.io.IOException;
import java.util.ArrayList;
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
@Path("SegUsuario")
@RequestScoped
public class SegUsuarioResource {

    @Context
    private UriInfo context;

    @Inject
    private SegUsuarioService segUsuarioServ;
    
    @Inject
    private SegPerfilService segPerfilServ;

    /**
     * Creates a new instance of SegRolResource
     */
    public SegUsuarioResource() {
    }
    
    @GET
    @Path("buscar/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRolesCodigo(@PathParam("codigo") String codigo) {
        
        String json=null;
        Response resp=null;

        if ("todos".equals(codigo)) {
            List<SegUsuario> usuarios = this.segUsuarioServ.obtenerTodos();
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
            try {
                json = mapper.writeValueAsString(usuarios);
                return Response.ok(json).build(); 
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR LISTAR TODOS USUARIOS!");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            //return Response.ok(json).build();          
            
        } else {
            SegUsuario usuario = this.segUsuarioServ.obtenerPorCodigo(codigo);
            if (usuario != null) {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                try {
                    json = mapper.writeValueAsString(usuario);
                    return Response.ok(json).build();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ERROR BUSCAR USUARIO POR CODIGO!");
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
                //return Response.ok(json).build();
                
            } else {
                //resp.status(Response.Status.NOT_FOUND);
                //return resp;   
                System.out.println("ERROR - NO EXISTE USUARIO!");
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }
        
    }
    
    
    //USUARIOS POR ROL
    @GET
    @Path("buscar/perfil/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuariosPorRol(@PathParam("codigo") String codigo) {
        
        String json=null;
        Response resp=null;
        List<SegUsuario> usuariosRS = new ArrayList<SegUsuario>();
        
        List<SegUsuario> usuarios = this.segUsuarioServ.obtenerTodos();
        if (usuarios!=null) {            
            
            for(SegUsuario su: usuarios)
            {
                if(su.getPerfil().getCodigo().equals(codigo))
                    usuariosRS.add(su);
            }
            
            if(usuariosRS!=null){
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                try {
                    json = mapper.writeValueAsString(usuariosRS);
                    return Response.ok(json).build(); 
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ERROR LISTAR USUARIOS POR ROL!");
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
                //return Response.ok(json).build();
                
            }else {
                //resp.status(Response.Status.NOT_FOUND);
                //return resp;   
                System.out.println("ERROR - NO EXISTEN USUARIOS CON ROL!");
                return Response.status(Response.Status.NOT_FOUND).build();
            }       

                      
            
        } else {
                //resp.status(Response.Status.NOT_FOUND);
                //return resp;   
                System.out.println("ERROR - NO EXISTE USUARIO CON ROL!");
                return Response.status(Response.Status.NOT_FOUND).build();
            }
    }
        
    
    
    

    @PUT
    @Path("nuevo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJsonNuevo(SegUsuarioRQ request) {

        SegUsuario usuario = new SegUsuario(request.getCod_usuario() ,request.getCod_persona(),this.segPerfilServ.obtenerPorCodigo(request.getPerfil()) ,request.getCorreo() ,request.getNombre(),request.getClave() ,request.getEstado());

        String json = null;
        Response resp=null;

        if(segUsuarioServ.obtenerPorCodigo(usuario.getCod_usuario())==null)
        {        
            try {
                segUsuarioServ.crear(usuario);
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
    public Response postJsonActualizar(SegUsuarioRQ request) {

        SegUsuario usuario = new SegUsuario(request.getCod_usuario() ,request.getCod_persona(),this.segPerfilServ.obtenerPorCodigo(request.getPerfil()) ,request.getCorreo() ,request.getNombre(),request.getClave() ,request.getEstado());

        String json = null;
        Response resp=null;
          
        try {
            segUsuarioServ.modificar(usuario);
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
            segUsuarioServ.eliminar(codigo);
            //resp.status(Response.Status.OK); 
            return Response.status(Response.Status.OK).build();
           
        } catch (Exception e) {
            //resp.status(Response.Status.BAD_REQUEST);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

       // return resp;
    }      

}
