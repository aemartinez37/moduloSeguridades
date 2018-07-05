/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;

import ec.edu.espe.arquitectura.seguridades.model.SegUsuario;
import ec.edu.espe.arquitectura.seguridades.rest.messages.LoginRS;
import ec.edu.espe.arquitectura.seguridades.rest.messages.SegLoginRQ;
import ec.edu.espe.arquitectura.seguridades.rest.messages.SegUsuarioRQ;
import ec.edu.espe.arquitectura.seguridades.service.SegPerfilService;
import ec.edu.espe.arquitectura.seguridades.service.SegUsuarioService;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
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
            List<SegUsuario> usuarios = this.segUsuarioServ.obtenerTodos();
//            GenericEntity<List<SegPerfil>> ge = new GenericEntity<List<SegPerfil>>(perfiles) {
//            };
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
            //response.setCodigoRetorno("OK");
            //response.setMensajeRetorno("Lista de Usuarios");
             
            try {
                //response.setRespuesta(usuarios);
                json = mapper.writeValueAsString(usuarios);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Response.ok(json).build();
            //return json;
            
            
        } else {
            SegUsuario usuario = this.segUsuarioServ.obtenerPorCodigo(codigo);
            if (usuario != null) {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                //response.setCodigoRetorno("OK");
                //response.setMensajeRetorno("Usuario");

                try {
                    //response.setRespuesta(usuario);
                    json = mapper.writeValueAsString(usuario);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Response.ok(json).build();
            //return json;
                
                
            } else {
                resp.status(Response.Status.NOT_FOUND);
                return resp;
                
//                ObjectMapper mapper = new ObjectMapper();
//                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//
//                response.setCodigoRetorno("ERR");
//                response.setMensajeRetorno("Usuario no encontrado");
//
//                try {
//                    response.setRespuesta(usuario);
//                    json = mapper.writeValueAsString(response);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return Response.ok(json).build();
            //return json;                
                
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
    public Response postJsonNuevo(SegUsuarioRQ request) {

        SegUsuario usuario = new SegUsuario(request.getCod_usuario() ,request.getCod_persona(),this.segPerfilServ.obtenerPorCodigo(request.getPerfil()) ,request.getCorreo() ,request.getNombre(),request.getClave() ,request.getEstado());

        LoginRS response = new LoginRS();
        String json = null;
        Response resp=null;

        //Gson gson=new Gson();        
        try {
            segUsuarioServ.crear(usuario);
             resp.status(Response.Status.CREATED);
            
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//            
//             response.setCodigoRetorno("OK");
//             response.setMensajeRetorno("Usuario Creado");
//             
//            try {
//                response.setRespuesta(segUsuarioServ.obtenerPorCodigo(usuario.getCod_usuario()));
//                json = mapper.writeValueAsString(response);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            
            
//            response.setCodigoRetorno("OK");
//            response.setMensajeRetorno("Perfil Creado");
//            response.setRespuesta(segPerfilServ.obtenerPorCodigo(perfil.getCodigo()));
            
            
        } catch (Exception e) {
            resp.status(Response.Status.BAD_REQUEST);
            
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//
//             response.setCodigoRetorno("ERR");
//             response.setMensajeRetorno("No se pudo crear usuario");
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
    public Response postJsonActualizar(SegUsuarioRQ request) {

        SegUsuario usuario = new SegUsuario(request.getCod_usuario() ,request.getCod_persona(),this.segPerfilServ.obtenerPorCodigo(request.getPerfil()) ,request.getCorreo() ,request.getNombre(),request.getClave() ,request.getEstado());

        LoginRS response = new LoginRS();
        String json = null;
        Response resp=null;
          
        try {
            segUsuarioServ.modificar(usuario);
            resp.status(Response.Status.OK);
            
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//            
//            response.setCodigoRetorno("OK");
//            response.setMensajeRetorno("Usuario Actualizado");
//             
//            try {
//                response.setRespuesta(segUsuarioServ.obtenerPorCodigo(usuario.getCod_usuario()));
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
//             response.setMensajeRetorno("No se pudo actualizar usuario");
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
            segUsuarioServ.eliminar(codigo);
            resp.status(Response.Status.OK);
            
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//            
//            response.setCodigoRetorno("OK");
//            response.setMensajeRetorno("Usuario Eliminado");
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
//             response.setMensajeRetorno("No se pudo eliminar usuario");
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
