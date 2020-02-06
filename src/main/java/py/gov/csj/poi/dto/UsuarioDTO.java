package py.gov.csj.poi.dto;

import java.util.Set;

public class UsuarioDTO {
	
	private Long id;
	private String nombre;
	private Set<String> permisos;
	
	public UsuarioDTO() {
	}
	
	public UsuarioDTO(Long id, String nombre, Set<String> permisos) {
		this.id = id;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<String> getPermisos() {
		return permisos;
	}

	public void setPermisos(Set<String> permisos) {
		this.permisos = permisos;
	}

}
