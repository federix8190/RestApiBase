package py.gov.csj.poi.service;

import java.util.logging.Logger;

import javax.ejb.Stateless;

import py.gov.csj.poi.base.BaseServiceImpl;
import py.gov.csj.poi.model.Ciudad;

@Stateless
public class CiudadService extends BaseServiceImpl<Ciudad> {
	
	@Override
	public Class<Ciudad> getEntity() {
		return Ciudad.class;
	}
	
	@Override
	public Logger getLogger() {
		return Logger.getLogger(this.getClass().getCanonicalName());
	}

}
