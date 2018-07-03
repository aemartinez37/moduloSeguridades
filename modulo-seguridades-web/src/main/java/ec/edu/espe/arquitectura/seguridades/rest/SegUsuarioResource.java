/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;

import ec.edu.espe.arquitectura.seguridades.model.SegUsuario;
import ec.edu.espe.arquitectura.seguridades.rest.messages.MensajeRS;
import ec.edu.espe.arquitectura.seguridades.rest.messages.SegLoginRQ;
import ec.edu.espe.arquitectura.seguridades.rest.messages.SegUsuarioRQ;
import ec.edu.espe.arquitectura.seguridades.service.SegPerfilService;
import ec.edu.espe.arquitectura.seguridades.service.SegUsuarioService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.crypto.KeyGenerator;
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
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
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
        
        MensajeRS response = new MensajeRS();
        String json=null;

        if ("todos".equals(codigo)) {
            List<SegUsuario> usuarios = this.segUsuarioServ.obtenerTodos();
//            GenericEntity<List<SegPerfil>> ge = new GenericEntity<List<SegPerfil>>(perfiles) {
//            };
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
            response.setCodigoRetorno("OK");
            response.setMensajeRetorno("Lista de Usuarios");
             
            try {
                response.setRespuesta(usuarios);
                json = mapper.writeValueAsString(response);
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

                response.setCodigoRetorno("OK");
                response.setMensajeRetorno("Usuario");

                try {
                    response.setRespuesta(usuario);
                    json = mapper.writeValueAsString(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Response.ok(json).build();
            //return json;
                
                
            } else {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                response.setCodigoRetorno("ERR");
                response.setMensajeRetorno("Usuario no encontrado");

                try {
                    response.setRespuesta(usuario);
                    json = mapper.writeValueAsString(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return Response.ok(json).build();
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
    public Object postJsonNuevo(SegUsuarioRQ request) {

        SegUsuario usuario = new SegUsuario(request.getCodigo(),this.segPerfilServ.obtenerPorCodigo(request.getPerfil()) ,request.getCorreo() ,request.getNombre(),request.getClave() ,request.getEstado());

        MensajeRS response = new MensajeRS();
        String json = null;

        //Gson gson=new Gson();        
        try {
            segUsuarioServ.crear(usuario);
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
             response.setCodigoRetorno("OK");
             response.setMensajeRetorno("Usuario Creado");
             
            try {
                response.setRespuesta(segUsuarioServ.obtenerPorCodigo(usuario.getCodigo()));
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
             response.setMensajeRetorno("No se pudo crear usuario");

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
    public Object postJsonActualizar(SegUsuarioRQ request) {

        SegUsuario usuario = new SegUsuario(request.getCodigo(),this.segPerfilServ.obtenerPorCodigo(request.getPerfil()) ,request.getCorreo() ,request.getNombre(),request.getClave() ,request.getEstado());

        MensajeRS response = new MensajeRS();
        String json = null;
          
        try {
            segUsuarioServ.modificar(usuario);
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
            response.setCodigoRetorno("OK");
            response.setMensajeRetorno("Usuario Actualizado");
             
            try {
                response.setRespuesta(segUsuarioServ.obtenerPorCodigo(usuario.getCodigo()));
                json = mapper.writeValueAsString(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

             response.setCodigoRetorno("ERR");
             response.setMensajeRetorno("No se pudo actualizar usuario");

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
            segUsuarioServ.eliminar(codigo);
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            
            response.setCodigoRetorno("OK");
            response.setMensajeRetorno("Usuario Eliminado");
             
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
             response.setMensajeRetorno("No se pudo eliminar usuario");

            try {
                response.setRespuesta(null);
                json = mapper.writeValueAsString(response);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return json;
    }
    
    /* SERVICIO DE LOGIN */    
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonLogin(SegLoginRQ request) throws NoSuchAlgorithmException {
        
        MensajeRS response = new MensajeRS();
        String json=null;

        SegUsuario usuario = this.segUsuarioServ.obtenerPorCodigo(request.getUsuario());
        if (usuario != null) {
            
            
            if(usuario.getClave().compareTo(request.getClave())==0) //Credenciales Correctas
            {
                if(usuario.getIntentosErroneos()<3) //Cuenta Habilitada
                {
                    usuario.setIntentosErroneos(0);
                    segUsuarioServ.modificar(usuario);
                    
                    // Issue a token for the user
                    String token = issueToken(usuario.getCodigo());
                    
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                    response.setCodigoRetorno("OK");
                    response.setMensajeRetorno("Acceso Concedido");

                    try {
                        response.setRespuesta(usuario);
                        json = mapper.writeValueAsString(response);
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //return Response.ok(json).build();
                    return Response.ok(json).header(AUTHORIZATION, "Bearer " + token).build();
                    //return json;
                }
                else //Cuenta deshabilitada
                {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                    response.setCodigoRetorno("ERR");
                    response.setMensajeRetorno("Cuenta deshabilitada por intentos incorrectos");

                    try {
                        response.setRespuesta(null);
                        json = mapper.writeValueAsString(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return Response.ok(json).build();
                    //return json;
                }
            }
            else //Credenciales Incorrectas
            {
                // Aumentar intentos fallidos y devolver error
                usuario.setIntentosErroneos(usuario.getIntentosErroneos()+1);
                segUsuarioServ.modificar(usuario);
                
                ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                    response.setCodigoRetorno("ERR");
                    response.setMensajeRetorno("Clave Incorrecta");

                    try {
                        response.setRespuesta(null);
                        json = mapper.writeValueAsString(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return Response.ok(json).build();
                    //return json;
                
                
            }
        } else {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

            response.setCodigoRetorno("ERR");
            response.setMensajeRetorno("Usuario no existe");

            try {
                response.setRespuesta(usuario);
                json = mapper.writeValueAsString(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Response.ok(json).build();
            //return json;                

        }
    }
    
    
    
    private String issueToken(String login) throws NoSuchAlgorithmException {
        Key key;
        SecureRandom rand = new SecureRandom();
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(256, rand);
        key = generator.generateKey();    
        
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDateTime.now().plusMinutes(15L).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
    }
    

}
