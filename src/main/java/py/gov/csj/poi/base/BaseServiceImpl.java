package py.gov.csj.poi.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.gov.csj.poi.ListaPaginada;
import py.gov.csj.poi.errores.AppException;

public abstract class BaseServiceImpl<G> implements BaseService<G> {

	@PersistenceContext(unitName = "TestPU")
    protected EntityManager em;
	
	public abstract Class<G> getEntity();
	public abstract Logger getLogger();
	
	@Override
    public ListaPaginada<G> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
        
		StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM ");
        query.append(getEntity().getCanonicalName());
        query.append(" c");
        Query q = em.createQuery(query.toString());
        //setParametrers(q, filtros);
        q.setFirstResult(inicio);
        if (cantidad != -1) {            
            q.setMaxResults(cantidad);
        }
        List<G> list = q.getResultList();
        int total = count(filtros);
        
        return new ListaPaginada<G>(list, total, inicio, cantidad);
    }
	
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
	
	protected void logError(String mensaje) {
		getLogger().severe(mensaje);
	}
	
	private int count(HashMap<String, Object> filtros) {
		
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM ");
        query.append(getEntity().getCanonicalName());
        query.append(" c");
        //buildWhere(query, filtros);
        Query q = em.createQuery(query.toString());
        //setParametrers(q, filtros);
        return ((Long) q.getSingleResult()).intValue();
        
    }
	
	
}
