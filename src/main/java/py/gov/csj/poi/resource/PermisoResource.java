package py.gov.csj.poi.resource;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import py.gov.csj.poi.base.BaseResource;
import py.gov.csj.poi.model.Permiso;
import py.gov.csj.poi.service.PermisoService;;

@Path("/permisos")
@Consumes(MediaType.APPLICATION_JSON)
public class PermisoResource extends BaseResource<Permiso, PermisoService> {
	
	@EJB
    protected PermisoService service;

	@Override
	public PermisoService getService() {
		return service;
	}

}
