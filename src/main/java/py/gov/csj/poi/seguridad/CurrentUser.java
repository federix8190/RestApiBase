package py.gov.csj.poi.seguridad;

import java.util.Set;

public class CurrentUser {

	private Long id;
	private String alias;
	private Long rol;
	private Set<String> permisos;
	
	public CurrentUser(Long id, String alias, Long rol, Set<String> permisos) {
		this.id = id;
		this.alias = alias;
		this.rol = rol;
		this.permisos = permisos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Long getRol() {
		return rol;
	}

	public void setRol(Long rol) {
		this.rol = rol;
	}

	public Set<String> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<String> permisos) {
		this.permisos = permisos;
	}

}

