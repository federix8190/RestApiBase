package py.gov.csj.poi.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;

import org.apache.shiro.crypto.hash.Md5Hash;

import py.gov.csj.poi.base.BaseServiceImpl;
import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.model.Usuario;

@Stateless
public class UsuarioService extends BaseServiceImpl<Usuario> {
	
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
        	System.err.println("Error al buscar usuario : " + e.getMessage());
            return null;
        }
    }
	
	@Override
	public Usuario insertar(Usuario entity) throws AppException {
		String encryptedToken = new Md5Hash(entity.getPassword(), entity.getAlias()).toString();
		entity.setPassword(encryptedToken);
		return super.insertar(entity);
	}

	@Override
	public Class<Usuario> getEntity() {
		return Usuario.class;
	}

}
