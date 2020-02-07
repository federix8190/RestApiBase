package py.gov.csj.poi.base;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.resource.TestResource;

public abstract class BaseServiceImpl<G> implements BaseService<G> {

	@PersistenceContext(unitName = "TestPU")
    protected EntityManager em;
	
	public abstract Class<G> getEntity();
	public abstract Logger getLogger();
	
	@Override
    public G insertar(G entity) throws AppException {
		em.persist(entity);
        return entity;
    }
	
	@Override
    public void modificar(Long id, G datos) throws AppException {
		G entity = (G) em.find(getEntity(), id);
        if (entity == null) {
            throw new AppException.NotFound("No encontado");
        }
        em.merge(datos);
    }
	
	public void logError(String mensaje) {
		getLogger().severe(mensaje);
	}
	
	
}
