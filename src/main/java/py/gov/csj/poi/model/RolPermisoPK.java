package py.gov.csj.poi.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class RolPermisoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
    @NotNull
    @Column(name = "permiso")
    private Long idPermiso;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "rol")
    private Long idRol;

    public RolPermisoPK() {
    }

    public RolPermisoPK(Long idPermiso, Long idRol) {
        this.idPermiso = idPermiso;
        this.idRol = idRol;
    }

    public Long getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

}
