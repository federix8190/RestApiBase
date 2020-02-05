package py.gov.csj.poi.resource;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.gov.csj.poi.Respuesta;
import py.gov.csj.poi.utils.EmailSender;

@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TestResource {
    
    //@EJB
    //private TestServices service;
    
    private Logger logger = Logger.getLogger(TestResource.class.getCanonicalName());
    
    @GET
    public Response test() {
        
    	logger.info("Servicio de prueba");
        return Response.ok().entity(new Respuesta(true, "testing")).build();
	}
    
    @POST
    @Path("/email")
    public Response sendEmail(HashMap<String, String> datos) throws AddressException, MessagingException {
        
    	logger.info("Servicio de prueba");
    	EmailSender.send("federix.8190@gmail.com", "", "Prueba", "Testing envio de mensaje");
    	return Response.ok().build();
	}
    
    /*@GET
    @Path("/personas")
    public Response test() {
        
    	List<Persona> lista = service.getPersonas();
    	logger.info("listar personas");
        return Response.ok().entity(lista).build();
	}
    
    @GET
	@Path("/configs")
	public Response getConfig() {
		
		List<Configuracion> configs = service.getConfig();
		return Response.ok(configs).build();
	}
    
    @POST
    @Path("/personas")
    public Response create(Persona p) {
        
    	return ok(new Persona());
	}
    
    @GET
    @Path("/consumos")
    public Response obtenerConsumoLinea() {
        
    	List<Consumo> lista = service.obtenerConsumoLinea("974117928");
        return Response.ok().entity(lista).build();
	}
    
    @GET
    @Path("/ciudades")
    public Response getCiudades() {
        
    	List<Ciudades> lista = service.getCiudades();
        return Response.ok().entity(lista).build();
	}
    
    @GET
    @Path("/cpt")
    public Response getCpt() {
        
    	List<Cpt> lista = service.getCpt();
    	logger.info("listar personas");
        return Response.ok().entity(lista).build();
	}*/
        
}
