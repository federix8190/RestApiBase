package py.gov.csj.poi.dto;

import java.util.Set;

import py.gov.csj.poi.model.Usuario;

public class UsuarioDto {
	
	private Long id;
	private String nombre;
	private String apellido;
	private String correo;
	private Long rol;
	private Set<String> permisos;
	
	public UsuarioDto() {
	}
	
	public UsuarioDto(Usuario usuario) {
		this.id = usuario.getId();
		this.nombre = usuario.getNombre();
		this.apellido = usuario.getApellido();
		this.correo = usuario.getCorreo();
	}
	
	public UsuarioDto(Long id, String nombre, Long rol, Set<String> permisos) {
		this.id = id;
		this.nombre = nombre;
		this.rol = rol;
		this.permisos = permisos;
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
}
