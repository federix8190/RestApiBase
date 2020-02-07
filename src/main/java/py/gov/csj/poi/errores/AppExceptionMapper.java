package py.gov.csj.poi.errores;

import java.util.logging.Logger;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import py.gov.csj.poi.Respuesta;

@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {

	private Logger logger = Logger.getLogger(AppExceptionMapper.class.getCanonicalName());
	
	@Context
	private UriInfo uriInfo;

	@Context
	private Request request;

	@Context
	private HttpHeaders headers;

	public Response toResponse(Exception e) {

		String uri = request.getMethod() + " " + uriInfo.getPath();
		logger.severe("Ocurrio un error al procesar : " + uri);
		logger.severe(e.getMessage());
		Respuesta res = new Respuesta(false, e.getMessage());
		return Response.status(500).entity(res).build();
	}

}
