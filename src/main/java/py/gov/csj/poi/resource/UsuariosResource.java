package py.gov.csj.poi.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.codehaus.jackson.type.TypeReference;

import py.gov.csj.poi.ListaPaginada;
import py.gov.csj.poi.base.BaseResource;
import py.gov.csj.poi.dto.UsuarioDto;
import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.model.Usuario;
import py.gov.csj.poi.service.UsuarioService;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
public class UsuariosResource extends BaseResource<Usuario, UsuarioService> {
	
	@EJB
    protected UsuarioService service;

	@Override
	public UsuarioService getService() {
		return service;
	}
	
	@GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaPaginada<UsuarioDto> listarUsuarios(
            @QueryParam(PAGINA) @DefaultValue("1") Integer pagina,
            @QueryParam(COUNT) @DefaultValue("10") Integer cantidad,
            @QueryParam(SORT) @DefaultValue("id") String orderBy,
            @QueryParam(ORDER) @DefaultValue("DESC") String orderDir,
            @QueryParam(FILTROS) String json) {
        
        pagina = pagina > 0 ? pagina : 1;
        Integer inicio = (pagina - 1) * cantidad;
        HashMap<String, Object> filtros = getFiltros(json);
        
        ListaPaginada<Usuario> lista = getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
        List<Usuario> items = lista.getLista();
        List<UsuarioDto> usuarios = new ArrayList<UsuarioDto>();
        for (Usuario item : items) {
        	usuarios.add(new UsuarioDto(item));
        }
        ListaPaginada<UsuarioDto> res = new ListaPaginada<UsuarioDto>(usuarios, inicio, lista.getTotal());
        return res;
    }
	
	@GET
    @Path("/roles")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> listar(@QueryParam("rol") Long rol) throws AppException {
		
		return service.getPermisosRol(rol);
	}

}
