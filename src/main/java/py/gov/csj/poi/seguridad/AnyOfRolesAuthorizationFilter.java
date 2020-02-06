package py.gov.csj.poi.seguridad;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;

public class AnyOfRolesAuthorizationFilter extends RolesAuthorizationFilter {
	
	@Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response,
            Object mappedValue) throws IOException {

		System.err.println("4 isAccessAllowed");
        final Subject subject = getSubject(request, response);
        final String[] rolesArray = (String[]) mappedValue;
        List<String> roles = Arrays.asList(rolesArray);
        
        if (!(request instanceof HttpServletRequest)) {
            throw new IOException("Can only process HttpServletRequest");
        }
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();
        String metodo = httpRequest.getMethod();
        String query = httpRequest.getQueryString();
        
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }
        
        for (String roleName : rolesArray) {
            String accion = "";
            if (metodo.equals("GET")) {
                accion = "LISTAR_";
            } else if (metodo.equals("POST")) {
                accion = "CREAR_";
            } else if (metodo.equals("PUT")) {
                accion = "EDITAR_";
            } else if (metodo.equals("DELETE")) {
                accion = "ELIMINAR_";
            }
            System.err.println("Rol Name : " + accion + roleName);
            if (subject.hasRole(accion + roleName)) {
                return true;
            }
        }

        return false;

    }

}
