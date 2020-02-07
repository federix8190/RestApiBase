package py.gov.csj.poi.seguridad;

import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import py.gov.csj.poi.Respuesta;
import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.model.Usuario;
import py.gov.csj.poi.service.UsuarioService;
import py.gov.csj.poi.utils.Constantes;
import py.gov.csj.poi.utils.SessionUtils;

@Path("/auth")
public class SessionResource {
	
	@EJB
    UsuarioService usuarioService;
	
	private Logger logger = Logger.getLogger(SessionResource.class.getCanonicalName());
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response iniciarSesion(Credenciales credenciales) throws Exception {

		Subject currentUser = SecurityUtils.getSubject();

        if (!validarParametros(credenciales)) {
            return badRequest(Constantes.PARAMETROS_INVALIDO);
        }

        if (!currentUser.isAuthenticated()) {
            return autenticar(credenciales);
        } else {
        	CurrentUser usuario = SessionUtils.getCurrentUser();
            LoginResponse resp = new LoginResponse(usuario.getId(), usuario.getAlias(), usuario.getPermisos(), "Usuario autenticado");
            return ok(resp);
        }
    }
	
	private Response autenticar(Credenciales credenciales) throws AppException, Exception {

		String username = null;
		String password = null;
		
        try {

            username = credenciales.getUsername();
            password = credenciales.getPassword();

            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.login(token);
            token.setRememberMe(true);

            Usuario usuario = usuarioService.findByName(username);
            SecurityUtils.getSubject().getSession().setAttribute("usuario", usuario);
            
            if (usuario != null) {
                Set<String> permisos = usuarioService.getPermisosUsuario(username);
                CurrentUser dto = new CurrentUser(usuario.getId(), usuario.getAlias(), usuario.getRol(), permisos);
                currentUser.getSession().setAttribute("currentUserSession", dto);
                Respuesta res = new LoginResponse(usuario.getId(), usuario.getNombre(), permisos);
                return ok(res);
            }
            return unauthorized(new Respuesta(false, Constantes.USUARIO_NO_ENCONTRADO));

        } catch (IncorrectCredentialsException e) {
        	logError("Credenciales incorrectas : " + username);
            Respuesta res = new Respuesta(false, Constantes.PASSWORD_INCORRECTO);
            return unauthorized(res);
        } catch (Exception e) {
        	logError("Error al autenticar al usuario : " + username);
            Respuesta res = new Respuesta(false, Constantes.ERROR_SERVIDOR);
            return error(res);
        }
    }
	
	@POST
    @Path("/cerrar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cerrarSesion() {

        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
        	try {
        		
        		currentUser.getSession().removeAttribute("usuario");
        		currentUser.logout();
        		
        	} catch (Exception e) {
        		logError("Error al cerrar session : " + e.getMessage());
        		Respuesta res = new Respuesta(false, "Error al cerrar session"); 
                return error(res);
        	}
        }
        Respuesta res = new Respuesta(true, "Session cerrada"); 
        return ok(res);
    }
	
	private boolean validarParametros(Credenciales credenciales) {

        if (credenciales == null
                || credenciales.getUsername() == null
                || credenciales.getPassword() == null
                || credenciales.getUsername().isEmpty()
                || credenciales.getPassword().isEmpty()) {
            return false;
        }
        return true;
    }
	
	protected Response ok(Object resp) {
        return Response.ok().entity(resp).build();
    }

    protected Response unauthorized(Object resp) {
        return Response.status(401).entity(resp).build();
    }
    
    protected Response badRequest(String mensaje) {
        Respuesta resp = new Respuesta(false, mensaje);
        return Response.status(400).entity(resp).build();
    }
    
    protected Response error(Object resp) {
        return Response.status(500).entity(resp).build();
    }
    
    public void logInfo(String mensaje) {
    	logger.info(mensaje);
	}
    
    public void logError(String mensaje) {
    	logger.severe(mensaje);
	}

}
