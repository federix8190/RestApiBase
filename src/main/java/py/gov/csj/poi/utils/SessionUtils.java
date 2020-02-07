package py.gov.csj.poi.utils;

import org.apache.shiro.SecurityUtils;

import py.gov.csj.poi.dto.UsuarioDto;
import py.gov.csj.poi.seguridad.CurrentUser;

public class SessionUtils {
	
	public static CurrentUser getCurrentUser() {
		
		CurrentUser user = (CurrentUser) SecurityUtils.getSubject().getSession()
        		.getAttribute("currentUserSession");
        return user;
    }

}
