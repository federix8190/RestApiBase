package py.gov.csj.poi.seguridad;

import java.util.List;
import java.util.Set;

import py.gov.csj.poi.Respuesta;

public class LoginResponse extends Respuesta {
	
	private Long userId;
	private String usuario;
	private Set<String> permisos;
	
	public LoginResponse() {
	}
	
	public LoginResponse(Long userId, String usuario) {
		super(true, null);
		this.userId = userId;
		this.usuario = usuario;
	}
	
	public LoginResponse(Long userId, String usuario, Set<String> permisos) {
		super(true, null);
		this.userId = userId;
		this.usuario = usuario;
		this.permisos = permisos;
	}
	
	public LoginResponse(Long userId, String usuario, Set<String> permisos, String mensaje) {
		super(true, mensaje);
		this.userId = userId;
		this.usuario = usuario;
		this.permisos = permisos;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Set<String> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<String> permisos) {
		this.permisos = permisos;
	}

}
