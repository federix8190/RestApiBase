package py.gov.csj.poi.seguridad;

import static py.gov.csj.poi.utils.Constantes.EJB_JNDI_USUARIO_SERVICE;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ejb.Singleton;
import javax.enterprise.context.Dependent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.namespace.QName;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import py.gov.csj.poi.dto.UsuarioDTO;
import py.gov.csj.poi.service.UsuarioService;
import py.gov.csj.poi.utils.SessionUtils;

public class KRealm extends AuthorizingRealm {
	
	UsuarioService userService;

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
        System.err.println("1 - doGetAuthenticationInfo : " + username + " - " + password);
        return new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection prins) {

    	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    	
    	try {
    		
    		String username = prins.getPrimaryPrincipal().toString();
    		System.err.println("3 - doGetAuthorizationInfo : " + username);
    		
    		Context ctx = new InitialContext();
            userService = (UsuarioService) ctx.lookup(EJB_JNDI_USUARIO_SERVICE);
            
            //info.addStringPermission("user:info:create");
		    Set<String> permisos = new HashSet<>();
		    permisos.add("LISTAR_CONFIGURACION");
		    info = new SimpleAuthorizationInfo(permisos);
		    //info.addStringPermission("test:read");
        
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return info;
    }

}
