package py.gov.csj.poi.config;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;

import py.gov.csj.poi.errores.AppException;

@Singleton
public class Configuraciones {

	private Map<String, String> configuraciones = new HashMap<String, String>();

	public String get(String key) throws AppException {
		String value = configuraciones.get(key);
		if (value == null) {
			throw new AppException.IllegalArgument("No existe un valor para la clave " + key);
		}
		return value;
	}

	public void put(String key, String value) {
		configuraciones.put(key, value);
	}
	
	public void limpiar() {
		configuraciones.clear();
	}
	
}

