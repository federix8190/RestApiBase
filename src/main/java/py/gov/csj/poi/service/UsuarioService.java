package py.gov.csj.poi.service;

import static py.gov.csj.poi.utils.Constantes.USUARIO_NO_ENCONTRADO;
import static py.gov.csj.poi.utils.Constantes.USUARIO_SIN_ROL;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import org.apache.shiro.crypto.hash.Md5Hash;

import py.gov.csj.poi.base.BaseServiceImpl;
import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.model.Permiso;
import py.gov.csj.poi.model.Usuario;
import py.gov.csj.poi.resource.TestResource;

@Stateless
public class UsuarioService extends BaseServiceImpl<Usuario> {
	
	@Override
	public Class<Usuario> getEntity() {
		return Usuario.class;
	}
	
	@Override
	public Logger getLogger() {
		return Logger.getLogger(this.getClass().getCanonicalName());
	}
	
	public Usuario findByName(String username) throws AppException {

        try {

            String sql = "SELECT u FROM Usuario u WHERE u.alias = :username";
            Query q = em.createQuery(sql);
            q.setParameter("username", username);
            List<Usuario> res = q.getResultList();
            if (res == null || res.size() == 0) {
                return null;
            }
            return res.get(0);

        } catch (Exception e) {
            throw new AppException.InternalError("Error interno del servidor");
        }
    }
	
	public Usuario findByCorreoPassword(Usuario user) {

        try {

            Query q = em.createQuery("SELECT u FROM Usuario u "
                    + "WHERE u.alias = :alias AND u.password = :password");

            q.setParameter("alias", user.getAlias());
            q.setParameter("password", user.getPassword());
            Usuario usuario = (Usuario) q.getSingleResult();
            return usuario;

        } catch (Exception e) {
        	logError("Error al buscar usuario : " + e.getMessage());
            return null;
        }
    }
	
	public List<String> getPermisosRol(Long rol) throws AppException {
    	
    	try {
	    	
    		// 1 - Obtener los ids de los permisos correspondientes al rol
    		String sql = "SELECT rp.rolPermisoPK.idPermiso FROM RolPermiso rp "
    				+ "WHERE rp.rolPermisoPK.idRol = :idRol";
    		Query q = em.createQuery(sql);
	    	q.setParameter("idRol", rol);
	    	List<Long> permisos = q.getResultList();
	    	
	    	// 2 - Obtener el nombre de los permisos a partir de los ids
	    	if (permisos != null && permisos.size() > 0) {
		    	sql = "SELECT p.nombre FROM Permiso p WHERE p.id IN :permisos";
		    	q = em.createQuery(sql);
		    	q.setParameter("permisos", permisos);
		    	List<String> lista = q.getResultList();
		    	return lista;
	    	}
	    	
	    } catch (Exception e) {
	    	logError("Error al listar permisos para el rol : " + rol);
	    }
    	
    	return new ArrayList<String>();
    }
	
	@Override
	public Usuario insertar(Usuario entity) throws AppException {
		String encryptedToken = new Md5Hash(entity.getPassword(), entity.getAlias()).toString();
		entity.setPassword(encryptedToken);
		return super.insertar(entity);
	}
	
	public Set<String> getPermisosUsuario(String userName) throws AppException {
    	
		Set<String> res = new HashSet<>();
		
    	try {
	    	
    		// 1 - Obtener el rol del usuario
    		Usuario user = findByName(userName);
    		Long rol = 0l;
    		if (user != null) {
    			rol = user.getRol();
    			if (rol == null) {
    				throw new AppException.InternalError(USUARIO_SIN_ROL);
    			}
    		} else {
    			throw new AppException.InternalError(USUARIO_NO_ENCONTRADO);
    		}
    		
    		// 2 - Obtener los ids de los permisos correspondientes al rol
    		String sql = "SELECT rp.rolPermisoPK.idPermiso FROM RolPermiso rp ";
    		sql = sql  + "WHERE rp.rolPermisoPK.idRol = :idRol";
    		Query q = em.createQuery(sql);
	    	q.setParameter("idRol", rol);
	    	List<Long> permisos = q.getResultList();
	    	
	    	// 2 - Obtener el nombre de los permisos a partir de los ids
	    	if (permisos != null && permisos.size() > 0) {
		    	sql = "SELECT p.nombre FROM Permiso p WHERE p.id IN :permisos";
		    	q = em.createQuery(sql);
		    	q.setParameter("permisos", permisos);
		    	List<String> lista = q.getResultList();
		    	if (lista != null) {
		    		res = new HashSet<>(lista);
		    	}
	    	}
	    	
	    } catch (Exception e) {
	    	logError("Error al listar permisos del usuario " + userName + " : " + e.getMessage());
	    }
    	
    	return res;
    }

}
