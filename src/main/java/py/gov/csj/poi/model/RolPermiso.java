package py.gov.csj.poi.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rol_permiso")
public class RolPermiso extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected RolPermisoPK rolPermisoPK;
    
    public RolPermiso() {
    }

    public RolPermiso(RolPermisoPK rolPermisoPK) {
        this.rolPermisoPK = rolPermisoPK;
    }

    public RolPermiso(long rol, long permiso) {
        this.rolPermisoPK = new RolPermisoPK(rol, permiso);
    }

    public RolPermisoPK getRolPermisoPK() {
        return rolPermisoPK;
    }

    public void setRolPermisoPK(RolPermisoPK rolPermisoPK) {
        this.rolPermisoPK = rolPermisoPK;
    }

}
