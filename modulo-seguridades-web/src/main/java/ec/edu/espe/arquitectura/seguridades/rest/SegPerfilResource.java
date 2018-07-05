/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;

import ec.edu.espe.arquitectura.seguridades.model.SegPerfil;
import ec.edu.espe.arquitectura.seguridades.rest.messages.LoginRS;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
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

//    @GET
//    @Path("listar")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response listarRoles() {
//        //TODO return proper representation object
//        //throw new UnsupportedOperationException();
//        List<SegRol> roles = this.segPerfilServ.obtenerTodos();
//        GenericEntity<List<SegRol>> ge = new GenericEntity<List<SegRol>>(roles) {
//        };
//
//        return Response.ok(ge).build();
//
//    }

    @GET
    @Path("buscar/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRolesCodigo(@PathParam("codigo") String codigo) {
        
        LoginRS response = new LoginRS();
        String json=null;
        Response resp=null;

        if ("todos".equals(codigo)) {
            List<SegPerfil> perfiles = this.segPerfilServ.obtenerTodos();
//            GenericEntity<List<SegPerfil>> ge = new GenericEntity<List<SegPerfil>>(perfiles) {
//            };
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
            //response.setCodigoRetorno("OK");
            //response.setMensajeRetorno("Lista de Perfiles");
             
            try {
                //response.setRespuesta(perfiles);
                json = mapper.writeValueAsString(perfiles);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resp.ok(json).build();
            
            
        } else {
            SegPerfil perfil = this.segPerfilServ.obtenerPorCodigo(codigo);
            if (perfil != null) {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

               // response.setCodigoRetorno("OK");
                //response.setMensajeRetorno("Perfil");

                try {
                    //response.setRespuesta(perfil);
                    json = mapper.writeValueAsString(perfil);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return resp.ok(json).build();
                
                
            } else {
                
//                ObjectMapper mapper = new ObjectMapper();
//                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//
//                response.setCodigoRetorno("ERR");
//                response.setMensajeRetorno("Perfil no encontrado");
//
//                try {
//                    response.setRespuesta(perfil);
//                    json = mapper.writeValueAsString(response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return json;   
                resp.status(Response.Status.NOT_FOUND);
                return resp;
                
            }
        }

        //TODO return proper representation object
        //throw new UnsupportedOperationException();
//        List<SegRol> roles = this.segPerfilServ.obtenerTodos();
//        GenericEntity<List<SegRol>> ge = new GenericEntity<List<SegRol>>(roles) {
//        };
//
//        return Response.ok(ge).build();

    }

    @POST
    @Path("nuevo")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonNuevo(SegPerfilRQ request) {

        SegPerfil perfil = new SegPerfil(request.getCodigo(), request.getNombre(), request.getEstado());

        LoginRS response = new LoginRS();
        String json = null;
        Response resp=null;
        

        //Gson gson=new Gson();        
        try {
            segPerfilServ.crear(perfil);
            
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
//             response.setCodigoRetorno("OK");
//             response.setMensajeRetorno("Perfil Creado");
//             
//            try {
//                response.setRespuesta(segPerfilServ.obtenerPorCodigo(perfil.getCodigo()));
//                json = mapper.writeValueAsString(response);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
              resp.status(Response.Status.CREATED);
            
            
//            response.setCodigoRetorno("OK");
//            response.setMensajeRetorno("Perfil Creado");
//            response.setRespuesta(segPerfilServ.obtenerPorCodigo(perfil.getCodigo()));
            
            
        } catch (Exception e) {
            
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//
//             response.setCodigoRetorno("ERR");
//             response.setMensajeRetorno("No se pudo crear perfil");
//
//            try {
//                response.setRespuesta(null);
//                json = mapper.writeValueAsString(response);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
            
//            response.setCodigoRetorno("ERR");
//            response.setMensajeRetorno("Error en crear perfil");
//            response.setRespuesta(null);
              resp.status(Response.Status.BAD_REQUEST);
        }

        //return Response.ok(response).build();
        return resp;
    }
    
//    @POST
//    @Path("crear")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response postJsonCrear(SegRolRQ request) {
//
//        SegRol rol = new SegRol(request.getCodigo(), request.getNombre(), request.getEstado());
//
//        LoginRS response = new LoginRS();
//
//        //Gson gson=new Gson();        
//        try {
//            segPerfilServ.crear(rol);
//            response.setOk(1);
//        } catch (Exception e) {
//            response.setOk(0);
//        }
//
//        return Response.ok(response).build();
//    }

    @POST
    @Path("actualizar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonActualizar(SegPerfilRQ request) {

        SegPerfil perfil = new SegPerfil(request.getCodigo(), request.getNombre(), request.getEstado());

        LoginRS response = new LoginRS();
        String json = null;
        Response resp=null;
          
        try {
            segPerfilServ.modificar(perfil);
            resp.status(Response.Status.OK);
            
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//            
//            response.setCodigoRetorno("OK");
//            response.setMensajeRetorno("Perfil Actualizado");
//             
//            try {
//                response.setRespuesta(segPerfilServ.obtenerPorCodigo(perfil.getCodigo()));
//                json = mapper.writeValueAsString(response);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            
        } catch (Exception e) {
            resp.status(Response.Status.BAD_REQUEST);
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//
//             response.setCodigoRetorno("ERR");
//             response.setMensajeRetorno("No se pudo actualizar perfil");
//
//            try {
//                response.setRespuesta(null);
//                json = mapper.writeValueAsString(response);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
        }

        return resp;
    }

    @DELETE
    @Path("eliminar/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonEliminar(@PathParam("codigo") String codigo) {
        
        LoginRS response = new LoginRS();
        String json = null;
        Response resp=null;

        //Gson gson=new Gson();        
        try {
            segPerfilServ.eliminar(codigo);
            resp.status(Response.Status.OK);
            
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//            
//            response.setCodigoRetorno("OK");
//            response.setMensajeRetorno("Perfil Eliminado");
//             
//            try {
//                response.setRespuesta(null);
//                json = mapper.writeValueAsString(response);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
           
        } catch (Exception e) {
            resp.status(Response.Status.BAD_REQUEST);
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//
//             response.setCodigoRetorno("ERR");
//             response.setMensajeRetorno("No se pudo eliminar perfil");
//
//            try {
//                response.setRespuesta(null);
//                json = mapper.writeValueAsString(response);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
        }

        return resp;
    }

}