package py.gov.csj.poi.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;

import py.gov.csj.poi.base.BaseServiceImpl;
import py.gov.csj.poi.model.Permiso;

@Stateless
public class PermisoService extends BaseServiceImpl<Permiso> {
	
	@Override
	public Class<Permiso> getEntity() {
		return Permiso.class;
	}
	
	@Override
	public Logger getLogger() {
		return Logger.getLogger(this.getClass().getCanonicalName());
	}

}
