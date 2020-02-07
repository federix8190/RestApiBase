package py.gov.csj.poi.seguridad;

import static py.gov.csj.poi.utils.Constantes.EJB_JNDI_USUARIO_SERVICE;

import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

import py.gov.csj.poi.model.Usuario;
import py.gov.csj.poi.service.UsuarioService;

public class KCredentialsMatcher extends SimpleCredentialsMatcher {
	
	private UsuarioService userService;
	private Logger logger = Logger.getLogger(KCredentialsMatcher.class.getCanonicalName());

	 /**
     * Verifica las credenciales del usuario(username y password)
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken tok, AuthenticationInfo info) {

        try {
        	
	    	if (tok != null && tok.getPrincipal() != null && tok.getCredentials() != null) {
	        	
	        	String username = tok.getPrincipal().toString();
	            String encryptedToken = new Md5Hash(new String((char[]) tok.getCredentials()), username).toString();
	            
	            logInfo("2 - doCredentialsMatch : " + username + " - " + encryptedToken);
	            Context ctx = new InitialContext();
	            userService = (UsuarioService) ctx.lookup(EJB_JNDI_USUARIO_SERVICE);
	            
	            Usuario user = new Usuario();            
	            user.setAlias(username);
	            user.setPassword(encryptedToken);
	            
	            Usuario usuario = userService.findByCorreoPassword(user);
	            if (usuario != null) {
	            	return true;
	            }
	        }
	    	
        } catch (Exception e) {
        	logError("Error en metodo doCredentialsMatch : " + e.getMessage());
        }
        return false;

    }
    
    public void logInfo(String mensaje) {
    	logger.info(mensaje);
	}
    
    public void logError(String mensaje) {
    	logger.severe(mensaje);
	}

}
