package py.gov.csj.poi.seguridad;

import static py.gov.csj.poi.utils.Constantes.EJB_JNDI_USUARIO_SERVICE;

import java.util.Set;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import py.gov.csj.poi.service.UsuarioService;

public class KRealm extends AuthorizingRealm {
	
	private UsuarioService userService;
	private Logger logger = Logger.getLogger(KRealm.class.getCanonicalName());

	/**
     * Obtiene los datos de autenticacion del usuario(username y password)
     * 
     * @param token
     * @return
     * @throws AuthenticationException 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = new String(upToken.getPassword());
        logInfo("1 - doGetAuthenticationInfo : " + username + " - " + password);
        return new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection prins) {

    	if (prins == null || prins.getPrimaryPrincipal() == null) {
    		logError("No se pudo obtener los datos de auntenticacion");
    		return null;
    	}
    	
    	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    	String username = prins.getPrimaryPrincipal().toString();
    	logInfo("3 - doGetAuthorizationInfo : " + username);
    	
    	try {
    		
    		Context ctx = new InitialContext();
            userService = (UsuarioService) ctx.lookup(EJB_JNDI_USUARIO_SERVICE);
		    Set<String> permisos = userService.getPermisosUsuario(username);
		    info = new SimpleAuthorizationInfo(permisos);
        
		} catch (Exception e) {
			logError("Error en metodo doGetAuthorizationInfo " + username + " : " + e.getMessage());
		}
    	
    	return info;
    }

    public void logInfo(String mensaje) {
    	logger.info(mensaje);
	}
    
    public void logError(String mensaje) {
    	logger.severe(mensaje);
	}
    
}
