package py.gov.csj.poi.utils;

import org.apache.shiro.SecurityUtils;

import py.gov.csj.poi.dto.UsuarioDTO;

public class SessionUtils {
	
	public static UsuarioDTO getCurrentUser() {
		
		UsuarioDTO user = (UsuarioDTO) SecurityUtils.getSubject().getSession()
        		.getAttribute("currentUserSession");
        return user;
    }

}
