package py.gov.csj.poi.resource;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import py.gov.csj.poi.base.BaseResource;
import py.gov.csj.poi.model.Ciudad;
import py.gov.csj.poi.service.CiudadService;

@Path("/ciudades")
@Consumes(MediaType.APPLICATION_JSON)
public class CiudadResource extends BaseResource<Ciudad, CiudadService> {
	
	@EJB
    protected CiudadService service;

	@Override
	public CiudadService getService() {
		return service;
	}

}
