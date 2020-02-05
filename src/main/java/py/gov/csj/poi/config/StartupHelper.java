package py.gov.csj.poi.config;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import py.gov.csj.poi.model.Configuracion;
import py.gov.csj.poi.service.ConfiguracionesServices;

@Startup
@Singleton
public class StartupHelper {
	
	private Logger logger = Logger.getLogger(StartupHelper.class.getCanonicalName());
	
	@EJB
    private ConfiguracionesServices service;
	
	@Inject
	Configuraciones configuraciones;
	
	@PostConstruct
	public void initialize() {
		
		List<Configuracion> configs = service.getAll();
		logger.info("Cargar configuraciones : " + configs.size());
		
		for (Configuracion config : configs) {
			configuraciones.put(config.getClave(), config.getValor());
		}
		
	}

}
