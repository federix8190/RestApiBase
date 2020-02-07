package py.gov.csj.poi.resource;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import py.gov.csj.poi.base.BaseResource;
import py.gov.csj.poi.model.Rol;
import py.gov.csj.poi.service.RolService;

@Path("/roles")
@Consumes(MediaType.APPLICATION_JSON)
public class RolResource extends BaseResource<Rol, RolService> {
	
	@EJB
    protected RolService service;

	@Override
	public RolService getService() {
		return service;
	}

}
