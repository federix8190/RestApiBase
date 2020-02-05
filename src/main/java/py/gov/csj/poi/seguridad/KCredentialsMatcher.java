package py.gov.csj.poi.seguridad;

import java.net.URL;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.Path;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

public class KCredentialsMatcher extends SimpleCredentialsMatcher {
	
	//@Inject
	//UsuarioService userService;

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
	            //userService = (UsuarioService) ctx.lookup("java:global/RestApi/UsuarioService!py.com.konecta.services.UsuarioService");
	            //return userService.esValido(username, encryptedToken);
	            return true;
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return false;

    }

}
