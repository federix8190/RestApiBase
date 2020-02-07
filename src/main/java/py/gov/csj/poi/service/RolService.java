package py.gov.csj.poi.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;

import py.gov.csj.poi.base.BaseServiceImpl;
import py.gov.csj.poi.model.Rol;

@Stateless
public class RolService extends BaseServiceImpl<Rol> {
	
	@Override
	public Class<Rol> getEntity() {
		return Rol.class;
	}
	
	@Override
	public Logger getLogger() {
		return Logger.getLogger(this.getClass().getCanonicalName());
	}

}
