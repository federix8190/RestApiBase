package py.gov.csj.poi.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.gov.csj.poi.Respuesta;
import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.model.Configuracion;

@Stateless
public class ConfiguracionesServices {

	@PersistenceContext(unitName = "TestPU")
    protected EntityManager em;
	
	public List<Configuracion> getAll() {
		String sql = "SELECT p FROM Configuracion p";
		Query q = em.createQuery(sql);
		List<Configuracion> lista = q.getResultList();
		return lista;
	}
	
	public String get(String clave) throws AppException {
		
		Configuracion configuracion = em.find(Configuracion.class, clave);
		
		if (configuracion != null)
			return configuracion.getValor();
		
		throw new AppException.IllegalArgument("No existe un valor para la clave " + clave);
	}
	
	public Configuracion find(String clave) throws AppException {
		Configuracion configuracion = em.find(Configuracion.class, clave);
		return configuracion;
	}
	
	public Respuesta save(Configuracion datos) {
		
		try {
			
			em.persist(datos);
			return new Respuesta(true, "");
			
		} catch (Exception e) {
			return new Respuesta(false, e.getMessage());
		}
	}
	
	public Respuesta update(String clave, Configuracion datos) {
		
		try {
			
			Configuracion configuracion = em.find(Configuracion.class, clave);
			configuracion.setValor(datos.getValor());
			configuracion.setDescripcion(datos.getDescripcion());
			em.merge(configuracion);
			return new Respuesta(true, "");
			
		} catch (Exception e) {
			return new Respuesta(false, e.getMessage());
		}
	}

}
