package py.gov.csj.poi.seguridad;

import static py.gov.csj.poi.utils.Constantes.EJB_JNDI_USUARIO_SERVICE;

import java.net.URL;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.Path;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

import py.gov.csj.poi.model.Usuario;
import py.gov.csj.poi.service.UsuarioService;

public class KCredentialsMatcher extends SimpleCredentialsMatcher {
	
	UsuarioService userService;

	 /**
     * Verifica las credenciales del usuario(username y password)
     *
     * @param tok tok.getPrincipal().toString() contiene el nombreusuario,
     * tok.getCredentials() el pass que ingreso
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken tok, AuthenticationInfo info) {

        try {
	    	if (tok != null && tok.getPrincipal() != null && tok.getCredentials() != null) {
	        	
	        	String username = tok.getPrincipal().toString();
	            String encryptedToken = new Md5Hash(new String((char[]) tok.getCredentials()), username).toString();
	            
	            System.err.println("2 - doCredentialsMatch : " + username + " - " + encryptedToken);
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
        	e.printStackTrace();
        }
        return false;

    }

}
