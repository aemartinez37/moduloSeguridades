/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;

import ec.edu.espe.arquitectura.seguridades.model.SegPerfil;
import ec.edu.espe.arquitectura.seguridades.rest.messages.MensajeRS;
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
    public Object listarRolesCodigo(@PathParam("codigo") String codigo) {
        
        MensajeRS response = new MensajeRS();
        String json=null;

        if ("todos".equals(codigo)) {
            List<SegPerfil> perfiles = this.segPerfilServ.obtenerTodos();
//            GenericEntity<List<SegPerfil>> ge = new GenericEntity<List<SegPerfil>>(perfiles) {
//            };
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
            response.setCodigoRetorno("OK");
            response.setMensajeRetorno("Lista de Perfiles");
             
            try {
                response.setRespuesta(perfiles);
                json = mapper.writeValueAsString(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;
            
            
        } else {
            SegPerfil perfil = this.segPerfilServ.obtenerPorCodigo(codigo);
            if (perfil != null) {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                response.setCodigoRetorno("OK");
                response.setMensajeRetorno("Perfil");

                try {
                    response.setRespuesta(perfil);
                    json = mapper.writeValueAsString(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;
                
                
            } else {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                response.setCodigoRetorno("ERR");
                response.setMensajeRetorno("Perfil no encontrado");

                try {
                    response.setRespuesta(perfil);
                    json = mapper.writeValueAsString(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return json;                
                
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
    public Object postJsonNuevo(SegPerfilRQ request) {

        SegPerfil perfil = new SegPerfil(request.getCodigo(), request.getNombre(), request.getEstado());

        MensajeRS response = new MensajeRS();
        String json = null;

        //Gson gson=new Gson();        
        try {
            segPerfilServ.crear(perfil);
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
             response.setCodigoRetorno("OK");
             response.setMensajeRetorno("Perfil Creado");
             
            try {
                response.setRespuesta(segPerfilServ.obtenerPorCodigo(perfil.getCodigo()));
                json = mapper.writeValueAsString(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
//            response.setCodigoRetorno("OK");
//            response.setMensajeRetorno("Perfil Creado");
//            response.setRespuesta(segPerfilServ.obtenerPorCodigo(perfil.getCodigo()));
            
            
        } catch (Exception e) {
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

             response.setCodigoRetorno("ERR");
             response.setMensajeRetorno("No se pudo crear perfil");

            try {
                response.setRespuesta(null);
                json = mapper.writeValueAsString(response);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
//            response.setCodigoRetorno("ERR");
//            response.setMensajeRetorno("Error en crear perfil");
//            response.setRespuesta(null);
        }

        //return Response.ok(response).build();
        return json;
    }
    
//    @POST
//    @Path("crear")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response postJsonCrear(SegRolRQ request) {
//
//        SegRol rol = new SegRol(request.getCodigo(), request.getNombre(), request.getEstado());
//
//        MensajeRS response = new MensajeRS();
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
    public Object postJsonActualizar(SegPerfilRQ request) {

        SegPerfil perfil = new SegPerfil(request.getCodigo(), request.getNombre(), request.getEstado());

        MensajeRS response = new MensajeRS();
        String json = null;
          
        try {
            segPerfilServ.modificar(perfil);
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
            response.setCodigoRetorno("OK");
            response.setMensajeRetorno("Perfil Actualizado");
             
            try {
                response.setRespuesta(segPerfilServ.obtenerPorCodigo(perfil.getCodigo()));
                json = mapper.writeValueAsString(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

             response.setCodigoRetorno("ERR");
             response.setMensajeRetorno("No se pudo actualizar perfil");

            try {
                response.setRespuesta(null);
                json = mapper.writeValueAsString(response);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return json;
    }

    @DELETE
    @Path("eliminar/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object postJsonEliminar(@PathParam("codigo") String codigo) {
        
        MensajeRS response = new MensajeRS();
        String json = null;

        //Gson gson=new Gson();        
        try {
            segPerfilServ.eliminar(codigo);
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
            response.setCodigoRetorno("OK");
            response.setMensajeRetorno("Perfil Eliminado");
             
            try {
                response.setRespuesta(null);
                json = mapper.writeValueAsString(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
           
        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

             response.setCodigoRetorno("ERR");
             response.setMensajeRetorno("No se pudo eliminar perfil");

            try {
                response.setRespuesta(null);
                json = mapper.writeValueAsString(response);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return json;
    }

}
