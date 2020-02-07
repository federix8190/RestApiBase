package py.gov.csj.poi.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import py.gov.csj.poi.ListaPaginada;
import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.model.BaseEntity;
import py.gov.csj.poi.seguridad.CurrentUser;
import py.gov.csj.poi.utils.SessionUtils;

public abstract class BaseServiceImpl<G extends BaseEntity> implements BaseService<G> {

	@PersistenceContext(unitName = "TestPU")
    protected EntityManager em;
	
	public abstract Class<G> getEntity();
	public abstract Logger getLogger();
	
	@Override
    public G insertar(G entity) throws AppException {
		entity.setFechaCreacion(new Date());
		entity.setUsuarioCreacion(getUser());
		em.persist(entity);
        return entity;
    }
	
	@Override
    public void modificar(Long id, G datos) throws AppException {
		G entity = (G) em.find(getEntity(), id);
        if (entity == null) {
            throw new AppException.NotFound("No encontado");
        }
        datos.setActivo(entity.isActivo());
        datos.setFechaCreacion(entity.getFechaCreacion());
        datos.setUsuarioCreacion(entity.getUsuarioCreacion());
        datos.setFechaModificacion(new Date());
        datos.setUsuarioModificacion(getUser());
        em.merge(datos);
    }
	
	public void delete(Long id) throws AppException {
        G entity = (G) em.find(getEntity(), id);
        if (entity == null) {
        	throw new AppException.NotFound("No encontado");
        }
        entity.setActivo(false);
        entity.setFechaEliminacion(new Date());
        entity.setUsuarioEliminacion(getUser());
        em.merge(entity);
    }
	
	@Override
    public ListaPaginada<G> listar(Integer inicio, Integer cantidad, String orderBy, 
            String orderDir, HashMap<String, Object> filtros) {
        
		StringBuilder query = new StringBuilder();
        query.append("SELECT c FROM ");
        query.append(getEntity().getCanonicalName());
        query.append(" c");
        buildWhere(query, filtros);
        Query q = em.createQuery(query.toString());
        logInfo("BaseService listar : " + query.toString());
        setParametrers(q, filtros);
        q.setFirstResult(inicio);
        if (cantidad != -1) {            
            q.setMaxResults(cantidad);
        }
        List<G> list = q.getResultList();
        int total = count(filtros);
        
        return new ListaPaginada<G>(list, total, inicio, cantidad);
    }
	
	private int count(HashMap<String, Object> filtros) {
		
        StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT(c) FROM ");
        query.append(getEntity().getCanonicalName());
        query.append(" c");
        buildWhere(query, filtros);
        Query q = em.createQuery(query.toString());
        setParametrers(q, filtros);
        return ((Long) q.getSingleResult()).intValue();
        
    }
	
	public void buildWhere(StringBuilder sb, HashMap<String, Object> filtros) {
        if (filtros == null || filtros.isEmpty()) {
        	sb.append(" WHERE activo = true");
            return;
        }
        int tokens = filtros.keySet().size();
        int token = 1;
        sb.append(" WHERE ");
        for (String key : filtros.keySet()) {
            if (filtros.get(key) instanceof String) {
                sb.append(" LOWER(c.")
                        .append(key)
                        .append(") LIKE LOWER(:")
                        .append(key)
                        .append(")");
            } else {
                sb.append(key).append(" = :").append(key);
            }
            //se añade el 'AND' si hay más caracteres.
            if (token < tokens) {
                sb.append(" AND ");
            }
            token++;
        }
        if (!filtros.containsKey("activo")) {
        	sb.append(" AND activo = true");
        }
    }
	
	public void setParametrers(Query q, HashMap<String, Object> filtros) {
        if (filtros == null) {
            return;
        }
        for (String key : filtros.keySet()) {
            Object value = filtros.get(key);
            if (filtros.get(key) instanceof String) {
                value = "%" + value + "%";
            }
            q.setParameter(key, value);
        }
    }
	
	public void buildOrder(StringBuilder sb, String orderBy, String orderDir) {
        if (orderBy != null && !orderBy.isEmpty()) {
            sb.append(" ORDER BY c.").append(orderBy).append(" ").append(orderDir);
        }
    }
	
	protected void logInfo(String mensaje) {
		getLogger().info(mensaje);
	}
	
	protected void logError(String mensaje) {
		getLogger().severe(mensaje);
	}
	
	protected Long getUser() {
		CurrentUser usuario = SessionUtils.getCurrentUser();
		if (usuario != null) {
			return usuario.getId();
		}
		return 0L;
	}
	
	
}
