package py.gov.csj.poi.seguridad;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
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

public class KRealm extends AuthorizingRealm {

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
    		Context ctx = new InitialContext();
			//UsuarioService userService = (UsuarioService) ctx.lookup("java:global/RestApi/UsuarioService!py.com.konecta.services.UsuarioService");
		    System.err.println("3 - doGetAuthorizationInfo : " + username);
		    
		    //info.addStringPermission("user:info:create");
		    Set<String> permisos = new HashSet<>();
		    permisos.add("LISTAR_USUARIO");
		    info = new SimpleAuthorizationInfo(permisos);
		    info.addStringPermission("test:read");
        
		} catch (NamingException e) {
			e.printStackTrace();
		}
    	
    	return info;
    }

}
