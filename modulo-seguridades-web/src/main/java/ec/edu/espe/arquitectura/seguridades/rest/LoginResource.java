/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;

import ec.edu.espe.arquitectura.seguridades.model.SegUsuario;
import ec.edu.espe.arquitectura.seguridades.rest.messages.LoginRS;
import ec.edu.espe.arquitectura.seguridades.rest.messages.SegLoginRQ;
import ec.edu.espe.arquitectura.seguridades.service.SegUsuarioService;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
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
@Path("Login")
@RequestScoped
public class LoginResource {

    @Context
    private UriInfo context;
    
    @Inject
    private SegUsuarioService segUsuarioServ;   

    /**
     * Creates a new instance of LoginResource
     */
    public LoginResource() {
    }
    
    /* SERVICIO DE LOGIN */    
    
    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJsonLogin(SegLoginRQ request) throws NoSuchAlgorithmException {
        
        LoginRS response = new LoginRS();
        String json=null;
        Response resp=null;

        SegUsuario usuario = this.segUsuarioServ.obtenerPorCodigo(request.getUsuario());
        if (usuario != null) {
            
            
            if(usuario.getClave().compareTo(request.getClave())==0) //Credenciales Correctas
            {
                if(usuario.getIntentosErroneos()<3) //Cuenta Habilitada
                {
                    usuario.setIntentosErroneos(0);
                    segUsuarioServ.modificar(usuario);                   
                    
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                    //response.setCodigoRetorno("OK");
                    //response.setMensajeRetorno("Acceso Concedido");

                    try {
                        response.setCod_usuario(usuario.getCod_usuario());
                        response.setCod_persona(usuario.getCod_persona());
                        response.setNombre(usuario.getNombre());
                        response.setCorreo(usuario.getCorreo());
                        response.setPerfil(usuario.getPerfil().getCodigo());
                        json = mapper.writeValueAsString(response);
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //return Response.ok(json).build();
                    return resp.ok(json).build();
                    //return json;
                }
                else //Cuenta deshabilitada
                {
//                    ObjectMapper mapper = new ObjectMapper();
//                    mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//                    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//
//                    //response.setCodigoRetorno("ERR");
//                    //response.setMensajeRetorno("Cuenta deshabilitada por intentos incorrectos");
//
//                    try {
//                        response.setRespuesta(null);
//                        json = mapper.writeValueAsString(response);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    resp.status(Response.Status.UNAUTHORIZED);
                    return resp;
                    //return json;
                }
            }
            else //Credenciales Incorrectas
            {
                // Aumentar intentos fallidos y devolver error
                usuario.setIntentosErroneos(usuario.getIntentosErroneos()+1);
                segUsuarioServ.modificar(usuario);
                
//                ObjectMapper mapper = new ObjectMapper();
//                    mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//                    mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                    //response.setCodigoRetorno("ERR");
                    //response.setMensajeRetorno("Clave Incorrecta");

//                    try {
//                        response.setRespuesta(null);
//                        json = mapper.writeValueAsString(response);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    resp.status(Response.Status.FORBIDDEN);
                    return resp;
                    //return json;
                
                
            }
        } else {

//            ObjectMapper mapper = new ObjectMapper();
//            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
//            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

//            response.setCodigoRetorno("ERR");
//            response.setMensajeRetorno("Usuario no existe");
//
//            try {
//                response.setRespuesta(usuario);
//                json = mapper.writeValueAsString(response);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            resp.status(Response.Status.NOT_FOUND);
            return resp;
            //return json;                

        }
    }    

}
