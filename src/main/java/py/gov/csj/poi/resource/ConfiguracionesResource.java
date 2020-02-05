package py.gov.csj.poi.resource;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import py.gov.csj.poi.Respuesta;
import py.gov.csj.poi.config.Configuraciones;
import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.model.Configuracion;
import py.gov.csj.poi.service.ConfiguracionesServices;

@Path("/config")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConfiguracionesResource {
	
	@EJB
    private ConfiguracionesServices service;
    
	@Inject
	Configuraciones configuraciones;
	
    private Logger logger = Logger.getLogger(TestResource.class.getCanonicalName());
    
    @GET
    public List<Configuracion> getAll() {
    	return service.getAll();
	}
    
    @GET
	@Path("/{clave}")
	public Configuracion get(@PathParam("clave") String clave) throws AppException {
    	return service.find(clave);
	}
    
    @POST
    public Respuesta save(Configuracion datos) {
    	return service.save(datos);
	}
    
    @PUT
	@Path("/{clave}")
	public Respuesta actualizarConfiguracion(@PathParam("clave") String clave, Configuracion datos) {
    	Respuesta r = service.update(clave, datos);
		configuraciones.limpiar();
		return r;
	}

}
