package py.gov.csj.poi.base;

import java.util.HashMap;
import java.util.logging.Logger;

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
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import com.google.gson.Gson;

import py.gov.csj.poi.ListaPaginada;
import py.gov.csj.poi.Respuesta;
import py.gov.csj.poi.errores.AppException;
import py.gov.csj.poi.model.Usuario;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public abstract class BaseResource <G extends Object, S extends BaseService<G>> {
	
    public abstract S getService();
    
    /**
     * Mapper utilizado para parsear el json de filtros.
     */
    protected ObjectMapper mapper = new ObjectMapper();
    
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public ListaPaginada<G> listar(
            @QueryParam("page") @DefaultValue("1") Integer pagina,
            @QueryParam("count") @DefaultValue("20") Integer cantidad,
            @QueryParam("sortBy") @DefaultValue("id") String orderBy,
            @QueryParam("sortOrder") @DefaultValue("DESC") String orderDir,
            @QueryParam("filters") String json) {
        
        // se calcula el inicio de la grilla
        pagina = pagina > 0 ? pagina : 1;
        Integer inicio = (pagina - 1) * cantidad;
        
        // se parsea el json para consutrir el filtro
        HashMap<String, Object> filtros = null;
        if (json != null && json.trim().length() > 2) {
            try {
                filtros = mapper.readValue(json, new TypeReference<HashMap<String, Object>>() {
                });
                //filtros = setearFiltros(filtros, httpRequest.getPathInfo());
            } catch (Exception e) {
                throw new WebApplicationException(e.getMessage(),
                        Response.Status.INTERNAL_SERVER_ERROR);
            }
        }
        
        return getService().listar(inicio, cantidad, orderBy, orderDir, filtros);
    }
	
	@POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertar(G dto) throws AppException {
        //try {
            G datos = getService().insertar(dto);
            return Response.ok().entity(datos).build();
        /*} catch (Exception e) {
        	throws
        }*/
    }

    /**
     * Se encarga de actualizar un recurso ya existente.
     *
     * @param id
     * @param dto el DTO que se desea actualizar en la base de datos.
     * @return
     *
     */
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public G modificar(@PathParam("id") Long id, G dto) {
        try {
            getService().modificar(id, dto);
            return dto;
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
    
    /*@GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public G obtener(@PathParam("id") Long id) {
        try {
            return getService().obtener(id);
        } catch (Exception e) {
            throw new WebApplicationException(e.getMessage(),
                    Response.Status.INTERNAL_SERVER_ERROR);
        }
    }*/
    
    public static Usuario obtenerUsuarioAutenticado() {
        Usuario usuario = null;
        Object user = SecurityUtils.getSubject().getSession().getAttribute("usuario");
        Gson gson = new Gson();
        String userJson = gson.toJson(user);
        usuario = gson.fromJson(userJson, Usuario.class);
        return usuario;
    }
	
	protected Response ok(Object resp) {
        return Response.ok().entity(resp).build();
    }

    protected Response unauthorized(Object resp) {
        return Response.status(401).entity(resp).build();
    }
    
    protected Response badRequest(String mensaje) {
        Respuesta resp = new Respuesta(false, mensaje);
        return Response.status(400).entity(resp).build();
    }

}
