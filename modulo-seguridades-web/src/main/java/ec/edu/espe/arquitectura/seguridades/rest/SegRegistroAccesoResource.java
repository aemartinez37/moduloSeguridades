/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.seguridades.rest;

import ec.edu.espe.arquitectura.seguridades.model.SegRegistroAcceso;
import ec.edu.espe.arquitectura.seguridades.rest.messages.SegRegistroAccesoRQ;
import ec.edu.espe.arquitectura.seguridades.service.SegRegistroAccesoService;
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
@Path("SegRegistroAcceso")
@RequestScoped
public class SegRegistroAccesoResource {

    @Context
    private UriInfo context;

    @Inject
    private SegRegistroAccesoService segRegistroAccesoServ;

    /**
     * Creates a new instance of SegRolResource
     */
    public SegRegistroAccesoResource() {
    }
    @GET
    @Path("buscar/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarRolesCodigo(@PathParam("codigo") String codigo) {
        
        String json=null;
        Response resp=null;

        if ("todos".equals(codigo)) {
            List<SegRegistroAcceso> registroAccesos = this.segRegistroAccesoServ.obtenerTodos();
            
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);          
             
            try {
                json = mapper.writeValueAsString(registroAccesos);
                return resp.ok(json).build();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("ERROR LISTAR TODOS REGISTROS ACCESO!");
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
            
            
            
        } else {
            SegRegistroAcceso registroAcceso = this.segRegistroAccesoServ.obtenerPorCodigo(Integer.valueOf(codigo));
            if (registroAcceso != null) {
                
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
                mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

                try {
                    json = mapper.writeValueAsString(registroAcceso);
                    return resp.ok(json).build();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("ERROR BUSCAR REGISTRO ACCESO POR CODIGO!");
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
                }
                
            } else {               

                //resp.status(Response.Status.NOT_FOUND);
                //return resp;
                System.out.println("ERROR - NO EXISTE REGISTRO ACCESO!");
                return Response.status(Response.Status.NOT_FOUND).build();
                
            }
        }        

    }

    @PUT
    @Path("nuevo")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postJsonNuevo(SegRegistroAccesoRQ request) {

        SegRegistroAcceso registroAcceso = new SegRegistroAcceso(request.getTipoAcceso(),request.getCodigoUsuario(),request.getPerfil(),request.getIp(),request.getFuncionalidad(),request.getResultado());
        //String tipoAcceso, String codigoUsuario, String ip, String funcionalidad, String resultado

        String json = null;
        Response resp=null;
        
        if(segRegistroAccesoServ.obtenerPorCodigo(registroAcceso.getCodigo())==null)
        {
            try {
                segRegistroAccesoServ.crear(registroAcceso);            
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

}
